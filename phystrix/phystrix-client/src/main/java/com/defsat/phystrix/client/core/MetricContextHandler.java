package com.defsat.phystrix.client.core;



public class MetricContextHandler {

	private static ThreadLocal<Context> hystrixContext = new ThreadLocal<Context>();
		
	
	private MetricContextHandler(){
		
	}
	
	public static Context getContext(){
		return hystrixContext.get();
	}
	
	public static void setContext(Context context){
		hystrixContext.set(context);
	}
	
	public static void remove(){
		hystrixContext.remove();
	}
	
	
}
