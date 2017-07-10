package com.defsat.phystrixDemo.dao;

import org.springframework.stereotype.Component;

import com.defsat.phystrix.client.Phystrix;
import com.defsat.phystrix.client.core.MetricContextHandler;
import com.defsat.phystrixDemo.model.Address;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AddressDao{
    
	@Phystrix(commandKey="getAddress",commandGroup="address",fallBack="fail",maxRequest=10,timeout=2000)
    public Address getAddress(String customerId) {
        log.info("Get address for customer {}", customerId);
        //测试用
        if(customerId.equals("-1")){
			throw new RuntimeException("throw get address exception!");
        }else if(customerId.equals("-2")){
        	try {
				TimeUnit.MILLISECONDS.sleep(3000);
			} catch (InterruptedException e) {}
        }else if(customerId.equals("-3")){
    	try {
    		TimeUnit.MILLISECONDS.sleep(1000);
    	} catch (InterruptedException e) {}
    }
        
        log.info("======aaaaaaaaaaaaaaaaa");
        return new Address("China", "Beijing", "Beijing", "100# jianguo rd, Haidian district");
    }
    
    public Address fail(String customerId){
		log.warn("-----fallback: get address failed!");
		log.warn("hystrix context_tostring() : {}",MetricContextHandler.getContext().toString());

		return null;
	}
    
    public Address fail22(String customerId){
    	log.warn("!!!!!!fallback22: get address failed!");
    	log.warn("hystrix context_tostring() : {}",MetricContextHandler.getContext().toString());
    	
    	return null;
    }
	
}
