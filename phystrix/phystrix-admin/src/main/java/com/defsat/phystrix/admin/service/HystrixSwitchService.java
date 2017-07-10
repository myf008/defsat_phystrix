package com.defsat.phystrix.admin.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.defsat.phystrix.admin.dao.daointerface.HystrixSwitchDAO;
import com.defsat.phystrix.admin.dao.daoobject.HystrixSwitchDO;

@Slf4j
@Service("hystrixSwitchService")
public class HystrixSwitchService {

	@Autowired
	private HystrixSwitchDAO hystrixDao;
	
	
	public HystrixSwitchDO getSwitch(String appId){
		
		log.debug("get hystrix switch, appId : {}", appId);
		
		return hystrixDao.get(appId);
	}
	
	public List<HystrixSwitchDO> getAllSwitch(){
		
		log.debug("get all hystrix switch");
		
		return hystrixDao.getAll();
	}
	
	
	@Transactional
	public void createSwitch(HystrixSwitchDO hystrixDO){
		
		log.debug("create hystrix switch, hystrixDO : {}", hystrixDO.toString());
		
		hystrixDao.insert(hystrixDO);
	}
	
	@Transactional
	public void deleteSwitch(String appId){
		
		log.debug("delete hystrix switch, appId : {}", appId);
		
		hystrixDao.delete(appId);
	}
	
	@Transactional
	public void updateSwitch(HystrixSwitchDO hystrixDO){
		
		log.debug("update hystrix switch, appId : {}", hystrixDO.getAppId());
		
		hystrixDao.update(hystrixDO);
	}
}
