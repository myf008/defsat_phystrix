package com.defsat.phystrix.admin.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.defsat.phystrix.admin.dao.daointerface.HystrixConfigDAO;
import com.defsat.phystrix.admin.dao.daoobject.HystrixConfigDO;

@Slf4j
@Service("hystrixConfigService")
public class HystrixConfigService {

	@Autowired
	private HystrixConfigDAO hystrixDao;
	
	
	public List<HystrixConfigDO> getAllConfigs(){
		
		log.debug("get all hystrix configs");
		
		return hystrixDao.getAll();
	}
	
	public List<HystrixConfigDO> getConfig(String appId){
		
		log.debug("get hystrix config, appId : {}", appId);
		
		return hystrixDao.get(appId);
	}
	
	public HystrixConfigDO getByCommandKey(String appId, String commandKey){
		
		log.debug("get hystrix config, appId : {}, commandKey : {}", appId, commandKey);
		
		return hystrixDao.getByCommandKey(appId,commandKey);
	}
	
	
	@Transactional
	public void createConfig(HystrixConfigDO hystrixDO){
		
		log.debug("create hystrix config, hystrixDO : {}", hystrixDO.toString());
		try{
			hystrixDao.insert(hystrixDO);
		} catch (DuplicateKeyException de) {
			log.info("insert failed,duplicate key, appId:{}, commndKey: {}", hystrixDO.getAppId(),
					hystrixDO.getCommandKey());
		}
		
	}
	
	@Transactional
	public void deleteConfig(String appId){
		
		log.debug("delete hystrix config, appId : {}", appId);
		
		hystrixDao.delete(appId);
	}
	
	@Transactional
	public void delOne(String appId, String commandKey){
		
		log.debug("delete hystrix config, appId : {},commandKey : {} ", appId, commandKey);
		
		hystrixDao.delOne(appId, commandKey);
	}
	
	@Transactional
	public void updateConfig(HystrixConfigDO hystrixDO){
		
		log.debug("update hystrix config, appId : {}", hystrixDO.getAppId());
		
		hystrixDao.update(hystrixDO);
	}
}
