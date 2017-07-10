package com.defsat.phystrix.client.spring;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.Ordered;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.defsat.metric.config.MetricConfig;
import com.defsat.metric.util.ConfigUtil;
import com.defsat.phystrix.client.Phystrix;
import com.defsat.phystrix.client.core.AnnotationContext;
import com.defsat.phystrix.client.core.PHystrixCommand;
import com.defsat.phystrix.client.core.PhyConfigLoader;
import com.defsat.phystrix.client.http.HystrixConfig;
import com.defsat.phystrix.client.http.HystrixHttpService;
import com.defsat.phystrix.client.http.ResponseResult;
import com.defsat.phystrix.client.infoboard.PhystrixMetricsPoller;
import com.defsat.phystrix.client.infoboard.PhystrixReset;
import com.defsat.phystrix.client.util.ConstantUtil;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

@Component
@Aspect
@Slf4j
public class PHystrixAspect implements Ordered, ResourceLoaderAware, ApplicationContextAware {

	private ResourceLoader resourceLoader;

	private ApplicationContext applicationContext;

	private DynamicBooleanProperty hystrixStatus;

	private ConcurrentMap<String, AtomicLong> suffixMap = Maps.newConcurrentMap();
	
	private ConcurrentMap<String, String> commandKeyMap = Maps.newConcurrentMap();
	
	private RestAdapter restAdapter;
	
	private HystrixHttpService hystrixHttpService;
	
	private String appId;
	
	private MetricConfig metricConfig;
	
	private PhystrixMetricsPoller poller;
	

	@PostConstruct
    public void init(){
		//init hystrix config
    	initHystrixConfig();
    	//init phystrix metric poller
    	initPhystrixMetricsPoller();
    }

	private void initPhystrixMetricsPoller() {
    	int delay = ConfigurationManager.getConfigInstance().getInt(ConstantUtil.PHYSTRIX_METRIC_DELAY);
        this.poller = new PhystrixMetricsPoller(delay, this.metricConfig);
        startPhystrixMetricsPoller();
	}
	
	private void startPhystrixMetricsPoller(){
		if(!this.poller.isRunning()){
			this.poller.start();
		}
	}
	
	private void closePhystrixMetricsPoller(){
		if(this.poller.isRunning()){
			this.poller.pause();
		}
	}

	private void initHystrixConfig() {
		try {
    		Properties userProps = ConfigUtil.getProperties(ConstantUtil.USER_PHYSTRIX_PROP);
    		if(null == userProps){
    			String msg = ConstantUtil.USER_PHYSTRIX_PROP + " is null!";
    			log.warn(msg);
    			throw new RuntimeException(msg);
    		}
    		String env = userProps.getProperty(ConstantUtil.PHYSTRIX_ENV).trim();
    		this.appId = userProps.getProperty(ConstantUtil.PHYSTRIX_APPID).trim();
    		log.info("phystrix env: {}, appId: {}", env, this.appId);
    		
    		String archaiusDelay = userProps.getProperty(ConstantUtil.USER_ARCHAIUS_DELAY,ConstantUtil.ARCHAIUS_DEFAULT_DELAY);
    		log.info("archaiusDelay: {}", archaiusDelay);
    		
    		String phyMonitorApp = userProps.getProperty(ConstantUtil.Phy_AppId);
    		if(null==phyMonitorApp) {
    			phyMonitorApp = this.appId;
    			userProps.setProperty(ConstantUtil.Phy_AppId, phyMonitorApp);
    		}
    		log.info("phystrix monitor appId: {}", phyMonitorApp);
    		Properties envProp = ConfigUtil.getProperties("phystrix-"+env + ".properties");
    		String baseUrl = envProp.getProperty(ConstantUtil.PHYSTRIX_ADMIN_URL);
    		String dynamiConfigUrl = baseUrl + envProp.getProperty(ConstantUtil.DYNAMIC_CONFIG_URL)	+ "/" + this.appId;
			
    		System.setProperty(ConstantUtil.ARCHAIUS_ENV, env);
    		System.setProperty(ConstantUtil.ARCHAIUS_DELAY_PARA, archaiusDelay);
    		System.setProperty(ConstantUtil.ARCHAIUS_ADDITIONALURL, dynamiConfigUrl);
    		log.info("archaius.configurationSource.additionalUrls : {}",dynamiConfigUrl);
			
			ConfigurationManager.loadAppOverrideProperties("phystrix");
			ConfigurationManager.loadProperties(userProps);
			
			this.metricConfig = PhyConfigLoader.ConfigFromArchaius();
			log.info("Phystrix MetricConfig : {}", this.metricConfig.toString());
			
			this.hystrixStatus = DynamicPropertyFactory.getInstance().getBooleanProperty(ConstantUtil.SWITCH_STATUS, false);
			log.info("init switch status : {}, appId: {}",hystrixStatus.get(), this.appId);
			
			//init hystrixHttpService
    		initHystrixHttpService(baseUrl);
    	} catch (Exception e) {
			log.error("init phystrix config failed!",e.getMessage());
		}
	}

	private void initHystrixHttpService(String baseUrl) {
		this.restAdapter = new RestAdapter.Builder()
					.setEndpoint(baseUrl)
					.build();
		
		this.hystrixHttpService = this.restAdapter.create(HystrixHttpService.class);
	}
	
	
	public AnnotationContext getContext(Method commandMethod) throws Exception {  
     	
     	Phystrix phystrix = commandMethod.getAnnotation(Phystrix.class);
     	final String commandKey = phystrix.commandKey();
     	suffixMap.putIfAbsent(commandKey, new AtomicLong());
    	
    	DynamicStringProperty commandGroup = DynamicPropertyFactory.getInstance().getStringProperty(commandKey+".commandGroup", phystrix.commandGroup());
    	DynamicStringProperty fallBack = DynamicPropertyFactory.getInstance().getStringProperty(commandKey+".fallback", phystrix.fallBack());
    	DynamicStringProperty isolationStgy = DynamicPropertyFactory.getInstance().getStringProperty(commandKey+".isolationStgy", phystrix.isolationStgy());
    	DynamicIntProperty maxRequest = DynamicPropertyFactory.getInstance().getIntProperty(commandKey+".maxRequest", phystrix.maxRequest());
    	DynamicIntProperty timeout = DynamicPropertyFactory.getInstance().getIntProperty(commandKey+".timeout", phystrix.timeout());
    	DynamicIntProperty threadPoolSize = DynamicPropertyFactory.getInstance().getIntProperty(commandKey+".threadPoolSize", phystrix.threadPoolSize());
    	DynamicIntProperty requestThreshold = DynamicPropertyFactory.getInstance().getIntProperty(commandKey+".requestThreshold", phystrix.requestThreshold());
    	DynamicIntProperty errorThreshold = DynamicPropertyFactory.getInstance().getIntProperty(commandKey+".errorThreshold", phystrix.errorThreshold());
    	DynamicIntProperty circuitBreakTime = DynamicPropertyFactory.getInstance().getIntProperty(commandKey+".circuitBreakTime", phystrix.circuitBreakTime());
        	
    	if(!commandKeyMap.containsKey(commandKey)){
    		commandKeyMap.putIfAbsent(commandKey, commandKey);
    		HystrixReset hystrixReset = new HystrixReset(commandKey);
    		
        	fallBack.addCallback(hystrixReset);
        	isolationStgy.addCallback(hystrixReset);
        	maxRequest.addCallback(hystrixReset);
        	timeout.addCallback(hystrixReset);
        	threadPoolSize.addCallback(hystrixReset);
        	requestThreshold.addCallback(hystrixReset);
        	errorThreshold.addCallback(hystrixReset);
        	circuitBreakTime.addCallback(hystrixReset);	
        	//write init hystrix config to db
	    	writeInitHystrixConfig(phystrix, commandKey);
     	}
     	
     	return new AnnotationContext(commandGroup.get(), commandKeyMap.get(commandKey),fallBack.get(), isolationStgy.get(), maxRequest.get(), timeout.get(),
     			threadPoolSize.get(), requestThreshold.get(), errorThreshold.get(),circuitBreakTime.get());
     }

	private void writeInitHystrixConfig(Phystrix phystrix, final String commandKey) {
		HystrixConfig config = new HystrixConfig(this.appId, commandKey, phystrix.commandGroup(),
				phystrix.fallBack(), phystrix.isolationStgy(), phystrix.maxRequest(), phystrix.timeout(), phystrix.threadPoolSize(),
				phystrix.requestThreshold(), phystrix.errorThreshold(), phystrix.circuitBreakTime());
		
		this.hystrixHttpService.addConfig(JSON.toJSONString(config), new Callback<ResponseResult>(){
			@Override
			public void success(ResponseResult result, Response response) {
				log.info("add init hystrix config to db success! commandKey : {}",commandKey);
			}
			@Override
			public void failure(RetrofitError error) {
				log.warn("error : add init hystrix config to db failed! {}", error.getMessage());
			}
		});
		
	}
	
	
	
	@PreDestroy
	public void destroy() {

	}

	@Pointcut("@annotation(com.defsat.phystrix.client.Phystrix)")
	public void pointcutPHystrix() {
	}

	@Around("pointcutPHystrix()")
	public Object doPHystrix(ProceedingJoinPoint pjp) throws Throwable {
		if (hystrixStatus.get()) {
			// 获取方法上的注解内容
			final Method commandMethod = ((MethodSignature) pjp.getSignature()).getMethod();
			AnnotationContext commandContext = getContext(commandMethod);
			log.debug("commandContext:{}", commandContext.toString());
			startPhystrixMetricsPoller();
			return new PHystrixCommand(commandContext, pjp).execute();
		}
		closePhystrixMetricsPoller();
		return pjp.proceed();

	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
	

	
	class HystrixReset extends Thread{
		private String commandKey;
		
		public HystrixReset(String commandKey) {
			this.commandKey = commandKey;
		}


		@Override
		public void run(){
			String preCommandKey = commandKeyMap.get(this.commandKey);
			try {
				PhystrixReset.updateCache(preCommandKey);
			} catch (Exception e) {
				log.warn("update phystrix metric cache failed!, commondKey : {}", preCommandKey,e);
			}
			String newCommandKey = this.commandKey + "-" + suffixMap.get(this.commandKey).getAndIncrement();
			commandKeyMap.put(this.commandKey, newCommandKey);
		}
	}
}
