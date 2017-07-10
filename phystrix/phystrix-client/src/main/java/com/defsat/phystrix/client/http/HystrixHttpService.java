package com.defsat.phystrix.client.http;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface HystrixHttpService {

	@FormUrlEncoded
	@POST("/phystrix/config/add")
	public void addConfig(@Field("phyConfig") String hystrixConfigStr, Callback<ResponseResult> cb);
	
}
