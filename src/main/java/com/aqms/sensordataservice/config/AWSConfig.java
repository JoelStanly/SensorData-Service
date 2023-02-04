package com.aqms.sensordataservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class AWSConfig {

	@Autowired
	private AppConfig appConfig;

	@Bean
	public DynamoDBMapper dynamoDBMapper() {
		return new DynamoDBMapper(amazonDynamoDBConfig());
	}
	
	private AmazonDynamoDB amazonDynamoDBConfig() {
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(appConfig.getDynamoDBEndpoint(),
						appConfig.getRegion()))
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(appConfig.getAccessKeyId(), appConfig.getSecretKeyId())))
				.build();
	}

	@Bean
	public AmazonSNSClient getSNSClient() {
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(appConfig.getAccessKeyId(), appConfig.getSecretKeyId())))
				.withRegion(Regions.AP_SOUTH_1).build();
	}

}
