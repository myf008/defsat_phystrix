package com.defsat.phystrix.admin.service;


import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.defsat.phystrix.admin.dao.daoobject.HystrixConfigDO;
import com.defsat.phystrix.admin.dao.daoobject.HystrixSwitchDO;
import com.defsat.phystrix.admin.service.HystrixConfigService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class HystrixConfigServiceTest {
	
	@Autowired
    private HystrixConfigService service;

    
    @Test
    public void testAddSwitch() throws Exception {
    	HystrixConfigDO hystrixDo = new HystrixConfigDO("aaa.bbb","getContact","contact","contactFallBack","thread",100,1500,20,30,25,6000);
    	service.createConfig(hystrixDo);
    	HystrixConfigDO hystrixDo2 = new HystrixConfigDO("aaa.bbb","getCustomer","customer","customerFallBack","thread",200,2500,25,35,25,8000);
    	service.createConfig(hystrixDo2);
    	List<HystrixConfigDO> configDoList = service.getConfig(hystrixDo.getAppId());
    	for(HystrixConfigDO configDo : configDoList){
    		log.debug(configDo.toString());
        	assertThat(configDo.getAppId(), equalTo("aaa.bbb"));
    	}
    	
    }
    
    
    @Test
    public void testGetConfig() throws Exception {
    	String appId = "aaa.bbb";
    	List<HystrixConfigDO> configDoList = service.getConfig(appId);
    	for(HystrixConfigDO configDo : configDoList){
    		log.debug(configDo.toString());
        	assertThat(configDo.getAppId(), equalTo("aaa.bbb"));
    	}
    }
    
    
    @Test
    public void testUpdateConfig() throws Exception {
    	HystrixConfigDO hystrixDo2 = new HystrixConfigDO("aaa.bbb","getCustomer","customer","customerFallBack","semaphore",300,1500,35,35,35,10000);
    	service.updateConfig(hystrixDo2);
    	
    }
    
    @Test
    public void testDelOne() throws Exception {
    	service.delOne("aaa.bbb","getCustomer");
    	List<HystrixConfigDO> configDoList = service.getConfig("aaa.bbb");
    	for(HystrixConfigDO configDo : configDoList){
    		log.debug(configDo.toString());
        	assertThat(configDo.getAppId(), equalTo("aaa.bbb"));
    	}
    }
    
    @Test
    public void testDelConfig() throws Exception {
    	service.deleteConfig("aaa.bbb");
    	List<HystrixConfigDO> hystrixDoList = service.getConfig("aaa.bbb");
    	log.debug(hystrixDoList==null?"null":hystrixDoList.toString());
    	assertThat(hystrixDoList.isEmpty(), equalTo(true));
    }
    
}
