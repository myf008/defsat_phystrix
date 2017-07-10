package com.defsat.phystrix.client.core;


public class AnnotationContext {
	private String commandKey;
	private String commandGroup;
	private String fallBack;
	private String isolationStgy;
	private int maxRequest;
	private int timeout;
	private int threadPoolSize;
	private int requestThreshold;
	private int errorThreshold;
	private int circuitBreakTime;

	public AnnotationContext(String commandGroup, String commandKey, String fallBack, String isolationStrategy,
			int maxRequest, int timeout, int threadPoolSize, int requestThreshold, int errorThreshold, int circuitBreakTime) {
		this.commandKey = commandKey;
		this.commandGroup = commandGroup;
		this.fallBack = fallBack;
		this.isolationStgy = isolationStrategy.toUpperCase();
		this.maxRequest = maxRequest;
		this.timeout = timeout;
		this.threadPoolSize = threadPoolSize;
		this.requestThreshold = requestThreshold;
		this.errorThreshold = errorThreshold;
		this.circuitBreakTime = circuitBreakTime;
	}

	public AnnotationContext(String commandGroup, String commandKey, String fallBack, String isolationStrategy) {
		this.commandKey = commandKey;
		this.commandGroup = commandGroup;
		this.fallBack = fallBack;
		this.isolationStgy = isolationStrategy.toUpperCase();
		this.maxRequest = 500;
		this.timeout = 1000;
		this.threadPoolSize = 10;
		this.requestThreshold = 20;
		this.errorThreshold = 30;
		this.circuitBreakTime = 10000;
	}
	
	public String getCommandKey() {
		return commandKey;
	}

	public void setCommandKey(String commandKey) {
		this.commandKey = commandKey;
	}

	public String getCommandGroup() {
		return commandGroup;
	}

	public void setCommandGroup(String commandGroup) {
		this.commandGroup = commandGroup;
	}

	public String getFallBack() {
		return fallBack;
	}

	public void setFallBack(String fallBack) {
		this.fallBack = fallBack;
	}

	public int getMaxRequest() {
		return maxRequest;
	}

	public void setMaxRequest(int maxRequest) {
		this.maxRequest = maxRequest;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getIsolationStgy() {
		return isolationStgy;
	}

	public void setIsolationStgy(String isolationStgy) {
		this.isolationStgy = isolationStgy.toUpperCase();
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

	@Override
	public String toString() {
		return "AnnotationContext [commandKey=" + commandKey + ", commandGroup=" + commandGroup + ", fallBack="
				+ fallBack + ", isolationStgy=" + isolationStgy + ", maxRequest=" + maxRequest + ", timeout=" + timeout
				+ ", threadPoolSize=" + threadPoolSize + ", requestThreshold=" + requestThreshold + ", errorThreshold="
				+ errorThreshold + ", circuitBreakTime=" + circuitBreakTime + "]";
	}
	
	
}
