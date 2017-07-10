package com.defsat.phystrix.admin.influxdb;
/*package com.defsat.metric.admin.influxdb;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.influxdb.InfluxDB.ConsistencyLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.defsat.metric.admin.config.MetricAdminConfig;
import com.defsat.metric.admin.storage.StorageHandler;
import com.defsat.metric.metric.MetricData;

@Slf4j
@RunWith(Parameterized.class)
public class StorageHandlerTest {

	private MetricAdminConfig config;
	private StorageHandler storageHandler;
	
	@Parameters
	public static Collection data(){
		return Arrays.asList(new Object[][]{
			{ "192.168.242.135:8086",// addr
			  "root",// name
			  "root", // password
			  "stg",//dbName
			  "default",//retention
			  "one" //consistent
			}
		});
	}
	
	
	
	public StorageHandlerTest(String addr,String userName,String password, String dbName, String retention, String consistency) {
		this.config = new MetricAdminConfig(addr,userName,password,dbName,retention,consistency);
		this.storageHandler = new StorageHandler(config);
	}


	@Test
	public void testStorage(){
		List<MetricData> dataList = Lists.newArrayList();
		Map<String,String> tags = Maps.newHashMap();
		tags.put("name", "s1");
		MetricData data1 = new MetricData("storage","value",tags,System.currentTimeMillis(),1);
		dataList.add(data1);
		storageHandler.store(dataList);
		
	}
}
*/