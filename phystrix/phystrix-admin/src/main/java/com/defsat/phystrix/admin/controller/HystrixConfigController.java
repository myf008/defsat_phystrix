package com.defsat.phystrix.admin.controller;


import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.defsat.metric.util.StringUtils;
import com.defsat.phystrix.admin.common.Result;
import com.defsat.phystrix.admin.config.ConfigConstant;
import com.defsat.phystrix.admin.dao.daoobject.HystrixConfigDO;
import com.defsat.phystrix.admin.dao.daoobject.HystrixSwitchDO;
import com.defsat.phystrix.admin.service.HystrixConfigService;
import com.defsat.phystrix.admin.service.HystrixSwitchService;


@Slf4j
@RestController
@RequestMapping(value = "/phystrix/config")
public class HystrixConfigController {

	@Autowired
	private HystrixConfigService configService;
	
	@Autowired
	private HystrixSwitchService switchService;
	
	@RequestMapping(value = "/dynamic/{appId:.*}", method = RequestMethod.GET)
	public String getDynamicConfig(@PathVariable String appId) {
		String result = null;
		Properties prop = new Properties();
		StringBuilder builder = new StringBuilder();
		
		//switch status
		boolean status = true;
		HystrixSwitchDO swtichDO = switchService.getSwitch(appId);
		if(null != swtichDO){
			status = swtichDO.getSwitchStatus().equals("1") ? true : false;
		}
		prop.put(ConfigConstant.PHYSTRIX_SWITCH_STATUS, status);
		
		
		//commandContext
		List<HystrixConfigDO> hystrixDOList = configService.getConfig(appId);
		for(HystrixConfigDO hystrixDO : hystrixDOList){
			if(null != hystrixDO){
				String commandKey = hystrixDO.getCommandKey();
				prop.put(commandKey + ".commandGroup", hystrixDO.getCommandGroup());
				prop.put(commandKey + ".fallback", hystrixDO.getFallback());
				prop.put(commandKey + ".isolationStgy", hystrixDO.getIsolationStgy());
				prop.put(commandKey + ".maxRequest", hystrixDO.getMaxRequest());
				prop.put(commandKey + ".timeout", hystrixDO.getTimeout());
				prop.put(commandKey + ".threadPoolSize", hystrixDO.getThreadPoolSize());
				prop.put(commandKey + ".requestThreshold", hystrixDO.getRequestThreshold());
				prop.put(commandKey + ".errorThreshold", hystrixDO.getErrorThreshold());
				prop.put(commandKey + ".circuitBreakTime", hystrixDO.getCircuitBreakTime());
			}
		}
		Set<Entry<Object,Object>> entrySet = prop.entrySet();
		for(Entry<Object,Object> entry : entrySet){
			builder.append(entry.toString()).append(StringUtils.PROP_SEPRATOR);
		}
		result = builder.toString().trim();
		log.info("hystrix dynamic config : {}", result);
		return result;
	}
	
	@RequestMapping(value = "/{appId:.*}", method = RequestMethod.GET)
	public Result<List<HystrixConfigDO>> getHystrixConfig(@PathVariable String appId) {
		Result<List<HystrixConfigDO>> result = new Result<List<HystrixConfigDO>>();
		try{
			List<HystrixConfigDO> hystrixDOList = configService.getConfig(appId);
			result.setSuccess(true);
			result.setResult(hystrixDOList);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("warning: get hystrix configs failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/phyConfigList", method = RequestMethod.GET)
	public Result<List<HystrixConfigDO>> getAllConfigs() {
		Result<List<HystrixConfigDO>> result = new Result<List<HystrixConfigDO>>();
		try{
			List<HystrixConfigDO> hystrixDOList = configService.getAllConfigs();
			result.setSuccess(true);
			result.setResult(hystrixDOList);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("warning: get all hystrix configs failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Result<String> deleteHystrixConfig(@RequestParam(value = "appId", defaultValue = "") String appid) {
		Result<String> result = new Result<String>();
		
		try{
			configService.deleteConfig(appid);
			result.setSuccess(true);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("delete hystrix config failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/delOne", method = RequestMethod.POST)
	public Result<String> deleteOneConfig(@RequestParam(value = "appId", defaultValue = "") String appId,
			@RequestParam(value = "commandKey", defaultValue = "") String commandKey) {
		Result<String> result = new Result<String>();

		try{
			configService.delOne(appId, commandKey);
			result.setSuccess(true);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("delete hystrix config failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result<String> createHystrixConfig(@RequestParam(value="phyConfig",defaultValue="") String phyConfigStr) {
		Result<String> result = new Result<String>();
		
		try{
			JSONObject object = JSON.parseObject(phyConfigStr);
			String appId = object.getString("appId");
			String commandKey = object.getString("commandKey");
			String commandGroup = object.getString("commandGroup");
			String fallback = object.getString("fallback");
			String isolationStgy = object.getString("isolationStgy");
			int maxRequest = object.getString("maxRequest").length()>0 ? object.getIntValue("maxRequest") : 0;
			int timeout = object.getString("timeout").length()>0 ? object.getIntValue("timeout") : 0;
			int threadPoolSize = object.getString("threadPoolSize").length()>0 ? object.getIntValue("threadPoolSize") : 0;
			int requestThreshold = object.getString("requestThreshold").length()>0 ? object.getIntValue("requestThreshold") : 0;
			int errorThreshold = object.getString("errorThreshold").length()>0 ? object.getIntValue("errorThreshold") : 0;
			int circuitBreakTime = object.getString("circuitBreakTime").length()>0 ? object.getIntValue("circuitBreakTime") : 0;
			
			if (Strings.isNullOrEmpty(appId) || Strings.isNullOrEmpty(commandKey)
					|| Strings.isNullOrEmpty(commandGroup) || Strings.isNullOrEmpty(fallback)
					|| Strings.isNullOrEmpty(isolationStgy)) {
				result.setSuccess(false);
				result.setMessages("warning: appId, commandKey, commandGroup, fallback cannot be null!");
				return result;
			}
			
			if (maxRequest <= 0 || timeout <= 0 || threadPoolSize <= 0 || requestThreshold <= 0 || errorThreshold <= 0
					|| circuitBreakTime <= 0) {
				result.setSuccess(false);
				result.setMessages("warning: maxRequest, timeout, threadPoolSize, requestThreshold, errorThreshold, circuitBreakTime must > 0");
				return result;
			}
			
			if(errorThreshold > 100){
				result.setSuccess(false);
				result.setMessages("warning: errorThreshold must <= 100");
				return result;
			}
			
			HystrixConfigDO hystrixDO = new HystrixConfigDO(appId, commandKey, commandGroup, fallback, isolationStgy,
					maxRequest, timeout, threadPoolSize, requestThreshold, errorThreshold, circuitBreakTime);
			
			configService.createConfig(hystrixDO);
			result.setSuccess(true);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("add hystrix config failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/getByCommandKey", method = RequestMethod.POST)
	public Result<HystrixConfigDO> getByCommandKey(@RequestParam(value = "appId", defaultValue = "") String appId,
			@RequestParam(value = "commandKey", defaultValue = "") String commandKey) {
		Result<HystrixConfigDO> result = new Result<HystrixConfigDO>();
		
		try{
			HystrixConfigDO configDo = configService.getByCommandKey(appId, commandKey);
			result.setSuccess(true);
			result.setMessages("OK");
			result.setResult(configDo);
		}catch (Throwable t) {
			log.error("get hystrix config failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result<String> updateHystrixConfig(@RequestParam(value="phyConfig",defaultValue="") String phyConfigStr) {
		Result<String> result = new Result<String>();
		
		try{
			JSONObject object = JSON.parseObject(phyConfigStr);
			String appId = object.getString("appId");
			String commandKey = object.getString("commandKey");
			String commandGroup = object.getString("commandGroup");
			String fallback = object.getString("fallback");
			String isolationStgy = object.getString("isolationStgy");
			int maxRequest = object.getIntValue("maxRequest");
			int timeout = object.getIntValue("timeout");
			int threadPoolSize = object.getIntValue("threadPoolSize");
			int requestThreshold = object.getIntValue("requestThreshold");
			int errorThreshold = object.getIntValue("errorThreshold");
			int circuitBreakTime = object.getIntValue("circuitBreakTime");
			
			if (Strings.isNullOrEmpty(appId) || Strings.isNullOrEmpty(commandKey)
					|| Strings.isNullOrEmpty(commandGroup) || Strings.isNullOrEmpty(fallback)
					|| Strings.isNullOrEmpty(isolationStgy)) {
				result.setSuccess(false);
				result.setMessages("warning: appId, commandKey, commandGroup, fallback cannot be null!");
				return result;
			}
			
			if (maxRequest <= 0 || timeout <= 0 || threadPoolSize <= 0 || requestThreshold <= 0 || errorThreshold <= 0
					|| circuitBreakTime <= 0) {
				result.setSuccess(false);
				result.setMessages("warning: maxRequest, timeout, threadPoolSize, requestThreshold, errorThreshold, circuitBreakTime must > 0");
				return result;
			}
			
			if(errorThreshold > 100){
				result.setSuccess(false);
				result.setMessages("warning: errorThreshold must <= 100");
				return result;
			}
			
			HystrixConfigDO hystrixDO = new HystrixConfigDO(appId, commandKey, commandGroup, fallback, isolationStgy,
					maxRequest, timeout, threadPoolSize, requestThreshold, errorThreshold, circuitBreakTime);
			
			
			configService.updateConfig(hystrixDO);
			result.setSuccess(true);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("update hystrix config by appId failed.", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	
}
