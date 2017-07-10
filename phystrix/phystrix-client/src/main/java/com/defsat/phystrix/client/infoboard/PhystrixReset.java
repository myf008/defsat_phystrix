package com.defsat.phystrix.client.infoboard;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPool;
import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;

public class PhystrixReset{


	@SuppressWarnings("unchecked")
	public static void updateCache(String commandKey) throws Exception {
		String threadPoolKey = commandKey+"Pool";
		
		Class<HystrixPropertiesFactory> clazz = HystrixPropertiesFactory.class;
		Field field = clazz.getDeclaredField("commandProperties");
		field.setAccessible(true);
		ConcurrentHashMap<String, HystrixCommandProperties> commandProperties = (ConcurrentHashMap<String, HystrixCommandProperties>) field.get(null);
		commandProperties.remove(commandKey);
		
		Field field2 = clazz.getDeclaredField("threadPoolProperties");
		field2.setAccessible(true);
		ConcurrentHashMap<String, HystrixThreadPoolProperties> threadPoolProperties = (ConcurrentHashMap<String, HystrixThreadPoolProperties>) field2.get(null);
		threadPoolProperties.remove(threadPoolKey);
		
		Class<HystrixCommandMetrics> clazz3 = HystrixCommandMetrics.class;
		Field field3 = clazz3.getDeclaredField("metrics");
		field3.setAccessible(true);
		ConcurrentHashMap<String, HystrixCommandMetrics> commandMetrics = (ConcurrentHashMap<String, HystrixCommandMetrics>) field3.get(null);
		commandMetrics.remove(commandKey);
		
		Class<HystrixThreadPoolMetrics> clazz4 = HystrixThreadPoolMetrics.class;
		Field field4 = clazz4.getDeclaredField("metrics");
		field4.setAccessible(true);
		ConcurrentHashMap<String, HystrixThreadPoolMetrics> threadPoolMetrics = (ConcurrentHashMap<String, HystrixThreadPoolMetrics>) field4.get(null);
		threadPoolMetrics.remove(threadPoolKey);
		
		Class<HystrixThreadPool.Factory> clazz5 = HystrixThreadPool.Factory.class;
		Field field5 = clazz5.getDeclaredField("threadPools");
		field5.setAccessible(true);
		ConcurrentHashMap<String, HystrixThreadPool> threadPools = (ConcurrentHashMap<String, HystrixThreadPool>) field5.get(null);
		HystrixThreadPool threadPool = threadPools.get(threadPoolKey);
		if(null!=threadPool){
			threadPool.getExecutor().shutdown();
			threadPools.remove(threadPoolKey);
		}
	}
		
	    
}
