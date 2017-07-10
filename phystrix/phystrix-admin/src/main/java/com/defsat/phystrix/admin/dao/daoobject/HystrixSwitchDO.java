package com.defsat.phystrix.admin.dao.daoobject;

import java.io.Serializable;
import java.util.Date;


public class HystrixSwitchDO implements Serializable{

	/**  */
	private static final long serialVersionUID = 3446216845621117261L;

	/** 自增主键 */
	private Integer id;

	/** app id */
	private String appId;
	
	/** 描述 */
	private String switchStatus;
	
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



	public String getSwitchStatus() {
		return switchStatus;
	}



	public void setSwitchStatus(String switchStatus) {
		this.switchStatus = switchStatus;
	}



	public HystrixSwitchDO(){
		
	}

	public HystrixSwitchDO(String appId, String switchStatus) {
		this.appId = appId;
		this.switchStatus = switchStatus;
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
		return "HystrixSwitchDO [id=" + id + ", appId=" + appId + ", switchStatus=" + switchStatus + ", creatTime="
				+ creatTime + ", modifyTime=" + modifyTime + "]";
	}


	
}
