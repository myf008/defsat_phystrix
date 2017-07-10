package com.defsat.phystrix.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration.AbstractConfiguration;
import org.junit.Test;

import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.URLConfigurationSource;

@Slf4j
public class DynamicPropTest {

	@Test
	public void testDynamicPropFromLocal(){
		
//		System.setProperty("archaius.configurationSource.additionalUrls", "");
		System.setProperty("archaius.configurationSource.defaultFileName", "phystrix.properties");
		DynamicIntProperty coreSize = 
			      DynamicPropertyFactory.getInstance().getIntProperty("hystrix.threadpool.default.coreSize", 5);
		
		while(true){
			System.out.println("coreSize : "+coreSize.get());
			try {
				TimeUnit.SECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	public void testDynamicPropFromRemote(){
		
		System.setProperty("archaius.configurationSource.additionalUrls", "http://localhost:8140/metric-admin/hystrix/config/dynamic/phystrix.demo");
		DynamicPropertyFactory instance = DynamicPropertyFactory.getInstance();	
		DynamicIntProperty key = instance.getIntProperty("getAddress.maxRequest", 10);
		
		int value = 0;
		while(true){
			try{
				value = key.get();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			log.info(Thread.currentThread().getName() + "---getAddress.maxRequest : "+ value);
			try {
				TimeUnit.SECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	
	@Test
	public void testDynamicProp(){
		URL url = null;
		
		 try {
			url = new URL("http://localhost:8140/metric-admin/phystrix/1");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		 
		 PolledConfigurationSource source = new URLConfigurationSource(url);
		 AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler();
		 ConfigurationManager.install(new DynamicConfiguration(source, scheduler));
		 
		 DynamicPropertyFactory instance = DynamicPropertyFactory.getInstance();	
		 DynamicStringProperty key = instance.getStringProperty("key", "KEY");
		 DynamicStringProperty servers = instance.getStringProperty("servers", "0.0.0.0");
			
			while(true){
				System.out.println("key : "+ key.get());
				System.out.println("servers : "+ servers.get());
				try {
					TimeUnit.SECONDS.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		
	}
		
	class MyConfigSource implements PolledConfigurationSource{

		@Override
		public PollResult poll(boolean initial, Object checkPoint) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
		
	
	
	/*-Darchaius.configurationSource.additionalUrls=http://10.8.89.76/h5gateway/config/prod
	 private void initCacheTables(){
	        final AbstractConfiguration configuration = ConfigurationManager.getConfigInstance();
	        *//** Behind store type*//*
	        final String behindStoreType = configuration.getString(BEHIND_STORE_TYPE, "none");
	        final DynamicStringProperty behindSyncType = DynamicPropertyFactory.getInstance().getStringProperty(BEHIND_STORE_TYPE, behindStoreType);

	        this.cacheTables = getCacheTables( behindSyncType.get(), configuration);
	        behindSyncType.addCallback(new Runnable() {
				
				@Override
				public void run() {
					String behindType = behindSyncType.get();
					List<CacheTable> oldCacheTables = cacheTables;
					cacheTables = getCacheTables(behindType,configuration);
			        for(CacheTable cacheTable:oldCacheTables){
			        	cacheTable.close();
			        }
				}
			});
	    }*/

}
