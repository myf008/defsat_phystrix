package com.defsat.phystrix.client.core;




public class Context {
	
	private String conmmandKey;
	private boolean isCircuitBreakerOpen;
	private boolean isShortCircuited;
	private boolean isSemphoreRejected;
	private boolean isThreadPoolRejected;
	private boolean isFailedExecution;
	private boolean isTimedOut;
	
	
	public Context(PHystrixCommand command){
		this.conmmandKey = command.getCommandKey().name();
		this.isCircuitBreakerOpen = command.isCircuitBreakerOpen();
		this.isSemphoreRejected = command.isResponseSemaphoreRejected();
		this.isThreadPoolRejected = command.isResponseThreadPoolRejected();
		this.isTimedOut = command.isResponseTimedOut();
		this.isShortCircuited = command.isResponseShortCircuited();
		this.isFailedExecution = command.isFailedExecution();
		
	}


	public String getConmmandKey() {
		return conmmandKey;
	}


	public void setConmmandKey(String conmmandKey) {
		this.conmmandKey = conmmandKey;
	}
	
	
	public boolean isCircuitBreakerOpen() {
		return isCircuitBreakerOpen;
	}


	public void setCircuitBreakerOpen(boolean isCircuitBreakerOpen) {
		this.isCircuitBreakerOpen = isCircuitBreakerOpen;
	}


	public boolean isSemphoreRejected() {
		return isSemphoreRejected;
	}


	public void setSemphoreRejected(boolean isSemphoreRejected) {
		this.isSemphoreRejected = isSemphoreRejected;
	}


	public boolean isThreadPoolRejected() {
		return isThreadPoolRejected;
	}


	public void setThreadPoolRejected(boolean isThreadPoolRejected) {
		this.isThreadPoolRejected = isThreadPoolRejected;
	}


	public boolean isTimedOut() {
		return isTimedOut;
	}


	public void setTimedOut(boolean isTimedOut) {
		this.isTimedOut = isTimedOut;
	}


	public boolean isShortCircuited() {
		return isShortCircuited;
	}


	public void setShortCircuited(boolean isShortCircuited) {
		this.isShortCircuited = isShortCircuited;
	}


	public boolean isFailedExecution() {
		return isFailedExecution;
	}


	public void setFailedExecution(boolean isFailedExecution) {
		this.isFailedExecution = isFailedExecution;
	}


	@Override
	public String toString() {
		return "Context [conmmandKey=" + conmmandKey + ", isCircuitBreakerOpen=" + isCircuitBreakerOpen
				+ ", isShortCircuited=" + isShortCircuited + ", isSemphoreRejected=" + isSemphoreRejected
				+ ", isThreadPoolRejected=" + isThreadPoolRejected + ", isFailedExecution=" + isFailedExecution
				+ ", isTimedOut=" + isTimedOut + "]";
	}
	
	
	
}
