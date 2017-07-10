package com.defsat.phystrix.client.http;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.defsat.metric.util.SleepUtils;
import com.defsat.phystrix.client.http.HystrixConfig;
import com.defsat.phystrix.client.http.HystrixHttpService;
import com.defsat.phystrix.client.http.ResponseResult;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HystrixHttpTest {

	
	@Test
	public void testAddConfig(){
		RestAdapter adapter = new RestAdapter.Builder()
		.setEndpoint("http://localhost:8100/phystrix-admin/")
		.build();
		HystrixHttpService configService = adapter.create(HystrixHttpService.class);
		HystrixConfig config = new HystrixConfig("toa.235","getContact","contact","contactFallBack","thread",100,1500,20,30,25,6000);
		configService.addConfig(JSON.toJSONString(config), new Callback<ResponseResult>(){

			@Override
			public void success(ResponseResult result, Response response) {
				log.info("POST success! result : {}", JSON.toJSONString(result));
			}

			@Override
			public void failure(RetrofitError error) {
				log.warn("POST error!{}",error.getStackTrace());
				
			}
			
		});
		
		SleepUtils.sleep(10000);
	}
	
	
}
