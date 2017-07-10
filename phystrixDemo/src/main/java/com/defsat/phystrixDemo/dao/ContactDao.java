package com.defsat.phystrixDemo.dao;

import com.defsat.phystrix.client.Phystrix;
import com.defsat.phystrixDemo.model.Contact;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContactDao{
	
	@Phystrix(commandKey = "getContact", commandGroup = "contact", fallBack = "contactFallBack",
			isolationStgy = "thread", threadPoolSize = 30, timeout = 1000)
	public Contact getContact(String customerId) {
        log.debug("Get contact for customer {}", customerId);
        
        //测试用
        if(customerId.equals("-3")){
	        Random random = new Random();
			int num = random.nextInt(1500);
			if(num < 500){
				throw new RuntimeException("throw get contact exception!");
			}
			try {
				TimeUnit.MILLISECONDS.sleep(num);
			} catch (InterruptedException e) {}
        }
        
        log.info("=========cccccccccccccccc");
        return new Contact("010-58888888","13800000000","xxx@163.com");
    }
    
    public Contact contactFallBack(String customerId){
		log.warn("-----fallback: get contact failed!");
		return null;
	}
   
}
