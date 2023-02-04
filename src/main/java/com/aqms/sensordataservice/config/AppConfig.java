package com.aqms.sensordataservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix="aws")
@Getter
@Setter
@Component
public class AppConfig {
	
	private String accessKeyId;
	private String secretKeyId;
	private String snsEndpoint;
	private String dynamoDBEndpoint;
	private String region;

}