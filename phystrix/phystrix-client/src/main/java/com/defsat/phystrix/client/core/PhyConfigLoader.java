package com.defsat.phystrix.client.core;

import static com.defsat.metric.config.ConfigConstant.WORKER_BUFFER_SIZE;
import static com.defsat.metric.config.ConfigConstant.WORKER_SIZE;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_AppId;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_BufferSize;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_Consistency;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_DBName;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_KafkaAcks;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_KafkaKeySerializer;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_KafkaRetries;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_KafkaServers;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_KafkaTopic;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_KafkaValueSerializer;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_PassWord;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_Retention;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_ServerAddr;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_StoragePolicy;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_UserName;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_WorkerBufSize;
import static com.defsat.phystrix.client.util.ConstantUtil.Phy_WorkerSize;
import static com.defsat.metric.config.ConfigConstant.BUFFER_SIZE;

import org.apache.commons.configuration.AbstractConfiguration;

import com.google.common.base.Joiner;
import com.defsat.metric.config.KafkaProducerConfig;
import com.defsat.metric.config.MetricConfig;
import com.defsat.metric.storage.StorageType;
import com.netflix.config.ConfigurationManager;

public class PhyConfigLoader {

	public static MetricConfig ConfigFromArchaius() {
		MetricConfig config = new MetricConfig();
		
		AbstractConfiguration  cm = ConfigurationManager.getConfigInstance();
		config.setBufferSize(cm.getInt(Phy_BufferSize,BUFFER_SIZE));
		config.setConsistent(cm.getString(Phy_Consistency,"one").toUpperCase().trim());
		config.setDbName(cm.getString(Phy_DBName, "default").trim());
		config.setUsername(cm.getString(Phy_UserName, "root").trim());
		config.setPassword(cm.getString(Phy_PassWord, "root").trim());
		config.setWorkerSize(cm.getInt(Phy_WorkerSize,WORKER_SIZE));
		config.setWorkerBufSize(cm.getInt(Phy_WorkerBufSize,WORKER_BUFFER_SIZE));
		config.setRetention(cm.getString(Phy_Retention, "default").trim());
		config.setServerAddr(cm.getString(Phy_ServerAddr, "default").trim());
		config.setStorageType(StorageType.getStorageType(cm.getString(Phy_StoragePolicy, "kafka").trim()));
		config.setAppId(cm.getString(Phy_AppId, "").trim());
		config.setKafkaConfig(kafkaConfigFromArchaius());
		
		return config;
	}

	public static KafkaProducerConfig kafkaConfigFromArchaius(){
		KafkaProducerConfig config = new KafkaProducerConfig();
		
		AbstractConfiguration  cm = ConfigurationManager.getConfigInstance();
		String[] servers = cm.getStringArray(Phy_KafkaServers);
		config.setServers(Joiner.on(",").join(servers));
		config.setKeySerializer(cm.getString(Phy_KafkaKeySerializer,"").trim());
		config.setValueSerializer(cm.getString(Phy_KafkaValueSerializer,"").trim());
		config.setAcks(cm.getString(Phy_KafkaAcks,"0").trim());
		config.setRetries(cm.getInt(Phy_KafkaRetries,1));
		config.setTopic(cm.getString(Phy_KafkaTopic,"").trim());
		
		return config;
	}
	
		
	
}
