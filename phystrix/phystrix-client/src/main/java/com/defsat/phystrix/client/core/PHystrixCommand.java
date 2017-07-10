package com.defsat.phystrix.client.core;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

@Slf4j
public class PHystrixCommand extends HystrixCommand<Object> {

	private ProceedingJoinPoint pjp;
	private AnnotationContext commandContext;


	public PHystrixCommand(Setter setter) {
		super(setter);
	}

	public PHystrixCommand(AnnotationContext commandContext, ProceedingJoinPoint pjp) {
		this(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandContext.getCommandGroup()))
				.andCommandKey(HystrixCommandKey.Factory.asKey(commandContext.getCommandKey()))
			    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
			        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.valueOf(commandContext.getIsolationStgy()))
				    .withExecutionIsolationSemaphoreMaxConcurrentRequests(commandContext.getMaxRequest())
				    .withExecutionTimeoutInMilliseconds(commandContext.getTimeout())
				    .withCircuitBreakerRequestVolumeThreshold(commandContext.getRequestThreshold())
				    .withCircuitBreakerErrorThresholdPercentage(commandContext.getErrorThreshold())
			    	.withCircuitBreakerSleepWindowInMilliseconds(commandContext.getCircuitBreakTime()))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
				    .withCoreSize(commandContext.getThreadPoolSize()))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(commandContext.getCommandKey()+"Pool")));
		this.pjp = pjp;
		this.commandContext = commandContext;
	}


		
	@Override
	protected Object run() throws Exception {
		try {
			return pjp.proceed();
		} catch (Throwable e) {
			log.error("pjp proceed error : {}", e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Override
	protected Object getFallback() {
		if (StringUtils.isNotEmpty(commandContext.getFallBack())) {
			try{
				Context context = new Context(this);
				MetricContextHandler.setContext(context);
				
				Method method = ReflectionUtils.findMethod(pjp.getTarget().getClass(), commandContext.getFallBack(),
						((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes());
				ReflectionUtils.makeAccessible(method);
				return ReflectionUtils.invokeMethod(method, pjp.getTarget(), pjp.getArgs());
			
			}finally{
				MetricContextHandler.remove();
			}
		}
		throw new UnsupportedOperationException("No fallback available.");
	}

	
}
