package com.aqms.sensordataservice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aqms.sensordataservice.FileUtil;
import com.aqms.sensordataservice.model.SensorData;
import com.aqms.sensordataservice.service.SensorDataService;

import jakarta.validation.Valid;

// Rest Controller for the Sensor Data

@RestController
@RequestMapping("/sensorData")
public class SensorDataController {

	// Controller Level Logging
	Logger logger = FileUtil.getLogger(SensorDataController.class);

	private SensorDataService sensorDataservice;

	public SensorDataController(SensorDataService sensorDataservice) {
		super();
		this.sensorDataservice = sensorDataservice;
	}

	// Posting The data stream
	@PostMapping("post")
	@ResponseBody
	public SensorData saveSensorData(@RequestBody @Valid SensorData sensorData) {
		logger.info("Verifying the Sensor ID is valid or not");
		sensorDataservice.validSensor(sensorData.getSensorid());
		logger.info("Verified the Sensor ID is valid");
		logger.info("Sending data that is posted for safety calculation");
		sensorData = sensorDataservice.addSafetyInfo(sensorData);
		logger.info("Calculation Completed");
		logger.info("SensorData is being saved with data:\n" + sensorData);
		if(!sensorData.getSafety()) {
			logger.info("Sending SMS");
			sensorDataservice.notifySafety(sensorData);
		}
		return sensorDataservice.saveSensorData(sensorData);
	}

	@GetMapping("/sub/{email}")
	@ResponseBody
	public String addSub(@PathVariable String email) {
		logger.info("Requesting Subscription for "+ email);
		return sensorDataservice.addSubscribe(email);

	}

	// Getting all the data from the stream
	@GetMapping("/all")
	@ResponseBody
	public List<SensorData> getSensorDataAllControl() {
		logger.info("Getting all sensorData data");
		return sensorDataservice.getSensorDataAll();
	}

}
