package com.defsat.phystrix.client;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.netflix.config.ConfigurationManager;

@Slf4j
public class DeployContextTest {

	@Test
	public void testEnvironment() throws Exception{
		String key = "hystrix.switch.status.url";
		ConfigurationManager.getConfigInstance().setProperty("@environment", "prd");
		//archaius.deployment.environment
		String env = ConfigurationManager.getConfigInstance().getString("@environment");
		log.info("env: {}", env);
		ConfigurationManager.loadAppOverrideProperties("phystrix");
		String url = ConfigurationManager.getConfigInstance().getString(key);
		log.info("url: {}", url);
	}
}
