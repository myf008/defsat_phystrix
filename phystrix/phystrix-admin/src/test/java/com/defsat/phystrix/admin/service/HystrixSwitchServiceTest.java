package com.defsat.phystrix.admin.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.defsat.phystrix.admin.dao.daoobject.HystrixSwitchDO;
import com.defsat.phystrix.admin.service.HystrixSwitchService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class HystrixSwitchServiceTest {
	
	@Autowired
    private HystrixSwitchService service;

    
    @Test
    public void testAddSwitch() throws Exception {
    	HystrixSwitchDO hystrixDo = new HystrixSwitchDO("aaa.bbb","0");
    	service.createSwitch(hystrixDo);
    	HystrixSwitchDO switchDo = service.getSwitch(hystrixDo.getAppId());
    	log.debug(switchDo.toString());
    	assertThat(switchDo.getSwitchStatus(), equalTo("0"));
    }
    
    
    @Test
    public void testGetSwitch() throws Exception {
    	String appId = "aaa.bbb";
        HystrixSwitchDO hystrixDo = service.getSwitch(appId);
        log.debug(hystrixDo.toString());
        assertThat(hystrixDo.getAppId(), equalTo(appId));
    }
    
    
    @Test
    public void testUpdateSwitch() throws Exception {
    	String appId = "aaa.bbb";
    	String switchStatus = "1";
    	HystrixSwitchDO hystrixDo = service.getSwitch(appId);
    	hystrixDo.setSwitchStatus(switchStatus);
    	service.updateSwitch(hystrixDo);
    	hystrixDo = service.getSwitch(appId);
    	log.debug(hystrixDo.toString());
    	assertThat(hystrixDo.getSwitchStatus(), equalTo(switchStatus));
    }
    
    
    @Test
    public void testDelSwitch() throws Exception {
    	service.deleteSwitch("aaa.bbb");
    	HystrixSwitchDO hystrixDo = service.getSwitch("aaa.bbb");
    	log.debug(hystrixDo==null?"null":hystrixDo.toString());
    	assertThat(hystrixDo, equalTo(null));
    }
    
}
