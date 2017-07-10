package com.defsat.phystrix.client.util;

public class ConstantUtil {

	//hystrix switch status
	public static final String SWITCH_STATUS = "phystrix.switch.status";
	public static final String ENABLE = "1";
	
	//archaius
	public static final String ARCHAIUS_ADDITIONALURL = "archaius.configurationSource.additionalUrls";
	public static final String ARCHAIUS_DELAY_PARA = "archaius.fixedDelayPollingScheduler.delayMills";
	public static final String USER_ARCHAIUS_DELAY = "phystrix.update.delay";
	public static final String ARCHAIUS_DEFAULT_DELAY = "60000";
	
	//hystrix dynamic commandCache
	public static final String PHYSTRIX_ADMIN_URL = "phystrix.admin.url";
	public static final String DYNAMIC_CONFIG_URL = "phystrix.dynamic.config.url";
	
	public static final String PHYSTRIX_PROP = "phystrix.properties";
	public static final String USER_PHYSTRIX_PROP = "phystrix_config.properties";
	public static final String PHYSTRIX_METRIC_DELAY = "phystrix.metric.delay";
	public static final String PHYSTRIX_APPID = "phystrix.appId";
	public static final String PHYSTRIX_ENV = "phystrix.env";
	public static final String ARCHAIUS_ENV = "archaius.deployment.environment";
	
	//influxdb
	public static final String Phy_ServerAddr = "phy_monitor.serverAddr";
	public static final String Phy_DBName = "phy_monitor.dbname";
	public static final String Phy_UserName = "phy_monitor.username";
	public static final String Phy_PassWord = "phy_monitor.password";
	public static final String Phy_Retention = "phy_monitor.retention";
	public static final String Phy_Consistency = "phy_monitor.consistency";
	public static final String Phy_BufferSize = "phy_monitor.bufferSize";
	public static final String Phy_StoragePolicy = "phy_monitor.storagePolicy";
	public static final String Phy_WorkerSize = "phy_monitor.workerSize";
	public static final String Phy_WorkerBufSize = "phy_monitor.workerBufSize";
	public static final String Phy_AppId = "phy_monitor.appId";
	
	//kafka producer
	public static final String Phy_KafkaServers = "phy_monitor.kafka.bootstrap.servers";
	public static final String Phy_KafkaKeySerializer = "phy_monitor.kafka.key.serializer";
	public static final String Phy_KafkaValueSerializer = "phy_monitor.kafka.value.serializer";
	public static final String Phy_KafkaAcks = "phy_monitor.kafka.acks";
	public static final String Phy_KafkaRetries = "phy_monitor.kafka.retries";
	public static final String Phy_KafkaTopic = "phy_monitor.kafka.topic";
	
}

