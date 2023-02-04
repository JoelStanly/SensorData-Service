package com.aqms.sensordataservice.service.implementation;

import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.aqms.sensordataservice.FileUtil;
import com.aqms.sensordataservice.VO.SensorPlot;
import com.aqms.sensordataservice.config.AppConfig;
import com.aqms.sensordataservice.model.SensorData;
import com.aqms.sensordataservice.repository.SensorDataRepository;
import com.aqms.sensordataservice.service.SensorDataService;

// Service Implementation
@Service
public class SensorDataServiceImplementation implements SensorDataService {

	private SensorDataRepository sensorDataRepository;

	private RestTemplate restTemplate;
	
	private AppConfig appConfig;
	
	private AmazonSNSClient amazonSNSClient;

	public SensorDataServiceImplementation(SensorDataRepository sensorDataRepository, RestTemplate restTemplate,AppConfig appConfig,AmazonSNSClient amazonSNSClient) {
		super();
		this.sensorDataRepository = sensorDataRepository;
		this.restTemplate = restTemplate;
		this.appConfig = appConfig;
		this.amazonSNSClient = amazonSNSClient;
	}

	// Saving data in DB
	@Override
	public SensorData saveSensorData(SensorData sensorData) {
		return sensorDataRepository.save(sensorData);
	}

	// Getting data from the DB
	@Override
	public List<SensorData> getSensorDataAll() {
		return (List<SensorData>) sensorDataRepository.findAll();
	}

	// Adding Safety Informations
	public SensorData addSafetyInfo(SensorData sensorData) {
		// TODO Auto-generated method stub
		sensorData.setSafeValue((sensorData.getC().add(sensorData.getCo()).add(sensorData.getCo2())
				.add(sensorData.getSo2()).subtract(sensorData.getO2())).divide(FileUtil.divider, RoundingMode.CEILING));
		sensorData.setSafety(sensorData.getSafeValue().compareTo(FileUtil.threshold) == 1 ? false : true);
		return sensorData;
	}

	// Checking whether the sensor is valid based on sensorPlot
	@Override
	public void validSensor(String sensorid) throws InternalServerError {
		// TODO Auto-generated method stub
		restTemplate.getForObject("http://SENSOR-PLOT/SensorPlot/find/" + sensorid,
				SensorPlot.class);
	}
	
	@Override
	public void notifySafety(SensorData sensorData){
		PublishRequest publishRequest = new PublishRequest(appConfig.getSnsEndpoint(), safetyMessage(sensorData.getSensorid()),
				"Notification: Air Quality Alert");
		amazonSNSClient.publish(publishRequest);

	}

	private String safetyMessage(String string) {
		SensorPlot sensor = restTemplate.getForObject("http://SENSOR-PLOT/SensorPlot/find/" + string, SensorPlot.class);
		return "The Room number:" + sensor.getRoom()+"\nThe Floor:" +sensor.getFloor()+"\nHas Detected the air quality values being above than the threshold\nKindly follow the safety precautions";
	}

	@Override
	public String addSubscribe(String email) {
		SubscribeRequest request = new SubscribeRequest(appConfig.getSnsEndpoint(),"email",email);
		amazonSNSClient.subscribe(request);
		return email + "  requested for subscription";
	}


}
