package com.defsat.phystrix.admin.dao.daointerface;

import java.util.List;

import com.defsat.phystrix.admin.dao.daoobject.HystrixSwitchDO;

public interface HystrixSwitchDAO {

	HystrixSwitchDO get(String appId);
	
	List<HystrixSwitchDO> getAll();
	
	void insert(HystrixSwitchDO configDO);

	void delete(String appId);

	void update(HystrixSwitchDO configDO);
}
