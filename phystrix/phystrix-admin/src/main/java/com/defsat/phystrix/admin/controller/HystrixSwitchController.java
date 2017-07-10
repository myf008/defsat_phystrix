package com.defsat.phystrix.admin.controller;


import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.defsat.phystrix.admin.common.Result;
import com.defsat.phystrix.admin.dao.daoobject.HystrixSwitchDO;
import com.defsat.phystrix.admin.service.HystrixSwitchService;
import com.google.common.base.Strings;


@Slf4j
@RestController
@RequestMapping(value = "/phystrix/switch")
public class HystrixSwitchController {

	@Autowired
	private HystrixSwitchService hystrixService;
	
	@RequestMapping(value = "/{appId:.*}", method = RequestMethod.GET)
	public Result<HystrixSwitchDO> getHystrixSwitch(@PathVariable String appId) {
		HystrixSwitchDO hystrixDO = hystrixService.getSwitch(appId);
		Result<HystrixSwitchDO> result = new Result<HystrixSwitchDO>();
		boolean success = hystrixDO==null ? false : true;
		String message = hystrixDO==null ? "warning: get hystrix switch failed!" : "OK";
		result.setSuccess(success);
		result.setResult(hystrixDO);
		result.setMessages(message);
		return result;
	}
	
	@RequestMapping(value = "/phySwitchList", method = RequestMethod.GET)
	public Result<List<HystrixSwitchDO>> getAllSwitch() {
		Result<List<HystrixSwitchDO>> result = new Result<List<HystrixSwitchDO>>();
		try{
			List<HystrixSwitchDO> hystrixDOList = hystrixService.getAllSwitch();
			result.setSuccess(true);
			result.setResult(hystrixDOList);
			result.setMessages("OK");
		}catch(Throwable t) {
			log.error("get all hystrix switch failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Result<String> deleteHystrixSwitch(@RequestParam(value = "appId", defaultValue = "") String appId) {
		Result<String> result = new Result<String>();
		
		try{
			hystrixService.deleteSwitch(appId);
			result.setSuccess(true);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("delete hystrix switch failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result<String> createHystrixSwitch(@RequestParam(value = "phySwitch", defaultValue = "") String  phySwitchStr) {
		Result<String> result = new Result<String>();
		
		try{
			JSONObject object = JSON.parseObject(phySwitchStr);
			String appId = object.getString("appId");
			String switchStatus = object.getString("switchStatus");
			if(Strings.isNullOrEmpty(appId) || Strings.isNullOrEmpty(switchStatus)){
				result.setSuccess(false);
				result.setMessages("appId, switch status cannot be null!");
				return result;
			}
			HystrixSwitchDO hystrixDO = new HystrixSwitchDO(appId,switchStatus);
			hystrixService.createSwitch(hystrixDO);
			result.setSuccess(true);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("add hystrix switch failed!", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result<String> updateHystrixSwitch(@RequestParam(value = "phySwitch", defaultValue = "") String  phySwitchStr) {
		Result<String> result = new Result<String>();
		
		try{
			JSONObject object = JSON.parseObject(phySwitchStr);
			String appId = object.getString("appId");
			String switchStatus = object.getString("switchStatus");
			if(Strings.isNullOrEmpty(appId) || Strings.isNullOrEmpty(switchStatus)){
				result.setSuccess(false);
				result.setMessages("appId, switch status cannot be null!");
				return result;
			}
			HystrixSwitchDO hystrixDO = new HystrixSwitchDO(appId,switchStatus);
			
			hystrixService.updateSwitch(hystrixDO);
			result.setSuccess(true);
			result.setMessages("OK");
		}catch (Throwable t) {
			log.error("update hystrix switch failed.", t.getMessage());
			result.setSuccess(false);
			result.setMessages(t.getMessage());
		}
		
		return result;
	}
	
	
}
