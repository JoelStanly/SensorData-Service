package com.aqms.sensordataservice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.aqms.sensordataservice.model.SensorData;

@Repository
public class SensorDataRepository {
	
	@Autowired
	DynamoDBMapper mapper;

	public SensorData save(SensorData sensorData) {
		// TODO Auto-generated method stub
		mapper.save(sensorData);
		return sensorData;
	}

	public List<SensorData> findAll() {
		return mapper.scan(SensorData.class, new DynamoDBScanExpression());
	}
	
	
}
