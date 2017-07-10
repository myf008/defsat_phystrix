package com.defsat.phystrix.client.http;


public class HystrixConfig{

	
	/** app id */
	private String appId;
	
	/** commandKey */
	private String commandKey;
	
	/** commandGroup */
	private String commandGroup;
	
	/** fallback */
	private String fallback;
	
	/** isolationStgy */
	private String isolationStgy;
	
	/** maxRequest */
	private int maxRequest;
	
	/** timeout */
	private int timeout;
	
	/** threadPoolSize */
	private int threadPoolSize;
	
	/** requestThreshold */
	private int requestThreshold;
	
	/** errorThreshold */
	private int errorThreshold;
	
	/** circuitBreakTime */
	private int circuitBreakTime;
	
	


	public String getAppId() {
		return appId;
	}



	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getCommandKey() {
		return commandKey;
	}

	public void setCommandKey(String commandKey) {
		this.commandKey = commandKey;
	}

	public HystrixConfig(){
		
	}

	public String getCommandGroup() {
		return commandGroup;
	}

	public void setCommandGroup(String commandGroup) {
		this.commandGroup = commandGroup;
	}

	public String getFallback() {
		return fallback;
	}

	public void setFallback(String fallback) {
		this.fallback = fallback;
	}

	public String getIsolationStgy() {
		return isolationStgy;
	}

	public void setIsolationStgy(String isolationStgy) {
		this.isolationStgy = isolationStgy;
	}


	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	

	public int getMaxRequest() {
		return maxRequest;
	}

	public void setMaxRequest(int maxRequest) {
		this.maxRequest = maxRequest;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public int getRequestThreshold() {
		return requestThreshold;
	}

	public void setRequestThreshold(int requestThreshold) {
		this.requestThreshold = requestThreshold;
	}

	public int getErrorThreshold() {
		return errorThreshold;
	}

	public void setErrorThreshold(int errorThreshold) {
		this.errorThreshold = errorThreshold;
	}

	public int getCircuitBreakTime() {
		return circuitBreakTime;
	}

	public void setCircuitBreakTime(int circuitBreakTime) {
		this.circuitBreakTime = circuitBreakTime;
	}

	public HystrixConfig(String appId, String commandKey) {
		this.appId = appId;
		this.commandKey = commandKey;
	}

	public HystrixConfig(String appId, String commandKey, String commandGroup,
			String fallback, String isolationStgy, int maxRequest, int timeout, int threadPoolSize,
			int requestThreshold, int errorThreshold, int circuitBreakTime) {
		this.appId = appId;
		this.commandKey = commandKey;
		this.commandGroup = commandGroup;
		this.fallback = fallback;
		this.isolationStgy = isolationStgy;
		this.maxRequest = maxRequest;
		this.timeout = timeout;
		this.threadPoolSize = threadPoolSize;
		this.requestThreshold = requestThreshold;
		this.errorThreshold = errorThreshold;
		this.circuitBreakTime = circuitBreakTime;
	}



	@Override
	public String toString() {
		return "HystrixConfig [appId=" + appId + ", commandKey=" + commandKey + ", commandGroup=" + commandGroup
				+ ", fallback=" + fallback + ", isolationStgy=" + isolationStgy + ", maxRequest=" + maxRequest
				+ ", timeout=" + timeout + ", threadPoolSize=" + threadPoolSize + ", requestThreshold="
				+ requestThreshold + ", errorThreshold=" + errorThreshold + ", circuitBreakTime=" + circuitBreakTime
				+ "]";
	}


	
}
