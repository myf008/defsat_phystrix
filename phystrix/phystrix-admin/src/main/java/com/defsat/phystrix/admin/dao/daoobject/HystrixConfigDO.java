package com.defsat.phystrix.admin.dao.daoobject;

import java.io.Serializable;
import java.util.Date;


public class HystrixConfigDO implements Serializable{

	/**  */
	private static final long serialVersionUID = -7562240518585139464L;

	/** 自增主键 */
	private Integer id;

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
	
	/** 创建时间 */
	private Date creatTime;

	/** 修改时间 */
	private Date modifyTime;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



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

	public HystrixConfigDO(){
		
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
		this.isolationStgy = isolationStgy.toUpperCase();
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

	public HystrixConfigDO(String appId, String commandKey) {
		this.appId = appId;
		this.commandKey = commandKey;
	}

	public HystrixConfigDO(String appId, String commandKey, String commandGroup,
			String fallback, String isolationStgy, int maxRequest, int timeout, int threadPoolSize,
			int requestThreshold, int errorThreshold, int circuitBreakTime) {
		this.appId = appId;
		this.commandKey = commandKey;
		this.commandGroup = commandGroup;
		this.fallback = fallback;
		this.isolationStgy = isolationStgy.toUpperCase();
		this.maxRequest = maxRequest;
		this.timeout = timeout;
		this.threadPoolSize = threadPoolSize;
		this.requestThreshold = requestThreshold;
		this.errorThreshold = errorThreshold;
		this.circuitBreakTime = circuitBreakTime;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "HystrixConfigDO [id=" + id + ", appId=" + appId + ", commandKey=" + commandKey + ", commandGroup="
				+ commandGroup + ", fallback=" + fallback + ", isolationStgy=" + isolationStgy + ", maxRequest="
				+ maxRequest + ", timeout=" + timeout + ", threadPoolSize=" + threadPoolSize + ", requestThreshold="
				+ requestThreshold + ", errorThreshold=" + errorThreshold + ", circuitBreakTime=" + circuitBreakTime
				+ ", creatTime=" + creatTime + ", modifyTime=" + modifyTime + "]";
	}

	
}
