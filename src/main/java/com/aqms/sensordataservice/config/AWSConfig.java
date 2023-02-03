package com.aqms.sensordataservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class AWSConfig {

	@Autowired
	private AppConfig appConfig;
	

	@Bean
	public AmazonSNSClient getSNSClient() {
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(appConfig.getAccessKeyId(), appConfig.getSecretKeyId())))
				.withRegion(Regions.AP_SOUTH_1)
				.build();
	}

}
