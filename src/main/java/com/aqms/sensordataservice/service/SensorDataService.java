package com.aqms.sensordataservice.service;

import java.util.List;

import com.aqms.sensordataservice.model.SensorData;
// Service Interface
public interface SensorDataService {
	
	SensorData saveSensorData(SensorData sensor);
	List<SensorData> getSensorDataAll();
	SensorData addSafetyInfo(SensorData sensor);
	void notifySafety(SensorData sensorData);
	String addSubscribe(String email);
	void validSensor(String sensorid);
	
}
