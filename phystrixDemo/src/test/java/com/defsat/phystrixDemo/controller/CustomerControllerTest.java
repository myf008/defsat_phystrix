package com.defsat.phystrixDemo.controller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.junit.Test;


@Slf4j
public class CustomerControllerTest {
	
	@Test
    public void testMultiThread() throws Exception{
    	ExecutorService service = Executors.newCachedThreadPool();
    	for(int i=0; i<2; i++){
    		service.execute(new Runnable(){

				@Override
				public void run() {
					try {
						log.info("test hystrix: ");
						testNormalRequest();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
    			
    		});
    	}
    	while(true){
    		Thread.sleep(1);
    	}
    	
    }
	
	
	
	/**
	 * 测试正常请求，结果看dashboard和控制台日志
	 * @throws Exception
	 */
	@Test
	public void testNormalRequest() throws Exception {
		String response;
		int i=0;
//		while(true){
			while(i++ < 500){
				  response = Request.Get("http://localhost:8090/phystrixDemo/customers/1")
				      .execute()
				      .returnContent()
				      .asString();
				      
			      System.out.println(response);	
			      Thread.sleep(5);
			}
//		}
	}
	
	
	
	/**
	 * 测试超时情况下走fallback流程，查看dashboard和控制台日志
	 * @throws Exception
	 */
	@Test
	public void testTimeout() throws Exception {
		int num = 50;
		ExecutorService service = Executors.newFixedThreadPool(num);
		for(int i=0; i<num; i++){
			service.execute(new Runnable(){

				@Override
				public void run() {
					try {
						System.out.println(Request.Get("http://localhost:8090/phystrixDemo/customers/-2")
							      .execute()
							      .returnContent()
							      .asString());
						
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				
			});
			
		}
		while(true){
			Thread.sleep(3000);
		}
	}
	
	/**
	 * 测试限流情况下走fallback流程，查看dashboard和控制台日志
	 * @throws Exception 
	 */
	@Test
	public void testRequestLimit() throws Exception {
		
		int num =25;
		ExecutorService service = Executors.newFixedThreadPool(num);
		for(int i=0; i<num; i++){
			service.execute(new Runnable(){

				@Override
				public void run() {
					try {
						System.out.println(Request.Get("http://localhost:8090/phystrixDemo/customers/-3")
								.execute()
								.returnContent()
								.asString());
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		while(true){
			Thread.sleep(3000);
		}
	}
	
	
	/**
	 * 测试熔断开关打开触发条件，1.满足10s内请求数量阀值 2.请求错误率达到阀值，结果看dashboard
	 * 测试熔断时也走fallback流程，熔断时看控制台日志
	 * @throws Exception
	 */
	@Test
	public void testCircuitOpen() throws Exception {
		String response;
		int i=0;
		while(i++ < 150){
			response = Request.Get("http://localhost:8090/phystrixDemo/customers/-1")
					.execute()
					.returnContent()
					.asString();
			
			System.out.println(response);	
		}
		
	}
	
	/**
	 * 测试熔断开关打开，熔断10s后，如果再有正常请求，则熔断开关关闭，结果看dashboard
	 * @throws Exception
	 */
	@Test
	public void testCircuitClose() throws Exception {
		String response;
		int i=0;
		while(i++ < 50){
			response = Request.Get("http://localhost:8090/phystrixDemo/customers/-1")
					.execute()
					.returnContent()
					.asString();
			
	      System.out.println(response);	
		}
		 TimeUnit.MILLISECONDS.sleep(12000);
		 i=0;
		while(i++ < 50){
			response = Request.Get("http://localhost:8090/phystrixDemo/customers/2")
					.execute()
					.returnContent()
					.asString();
			
			System.out.println(response);	
			TimeUnit.MILLISECONDS.sleep(200);
		}
		
	}
	

}
