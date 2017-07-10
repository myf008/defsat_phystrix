package com.defsat.phystrix.admin.dao.daointerface;

import java.util.List;

import com.defsat.phystrix.admin.dao.daoobject.HystrixConfigDO;

public interface HystrixConfigDAO {

	List<HystrixConfigDO> getAll();
	
	List<HystrixConfigDO> get(String appId);
	
	HystrixConfigDO getByCommandKey(String appId,String commandKey);
	
	void insert(HystrixConfigDO configDOList);

	void delete(String appId);
	
	void delOne(String appId, String commandKey);

	void update(HystrixConfigDO configDO);
}
