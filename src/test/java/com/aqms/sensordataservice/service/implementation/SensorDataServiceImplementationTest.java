package com.aqms.sensordataservice.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.aqms.sensordataservice.VO.SensorPlot;
import com.aqms.sensordataservice.config.AppConfig;
import com.aqms.sensordataservice.exception.ResourceNotFoundException;
import com.aqms.sensordataservice.model.SensorData;
import com.aqms.sensordataservice.repository.SensorDataRepository;
import com.aqms.sensordataservice.service.SensorDataService;

@SpringBootTest
public class SensorDataServiceImplementationTest {

	@Mock
	private SensorDataRepository sensorDataRepository;

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private AppConfig appConfig;
	@Mock
	private AmazonSNSClient amazonSNSClient;

	@InjectMocks
	SensorDataService sensorDataService = new SensorDataServiceImplementation(sensorDataRepository, restTemplate,appConfig,amazonSNSClient);

	// When Save Data Works Successfully
	@DisplayName("Save Data - Success")
	@Test
	void test_When_SaveData_Success() {
		// Mocking
		SensorData sensorData = getMockSensorData();
		when(sensorDataRepository.save(any(SensorData.class))).thenReturn(sensorData);

		// Actual
		SensorData sensorDataActual = sensorDataService.saveSensorData(sensorData);

		// Verify
		verify(sensorDataRepository, times(1)).save(any());

		// Assert
		assertNotNull(sensorDataActual);
		assertEquals(sensorDataActual.getDataId(), sensorData.getDataId());
		assertEquals(sensorDataActual.getSensorid(), sensorData.getSensorid());
		assertEquals(sensorDataActual.getCo2(), sensorData.getCo2());
		assertEquals(sensorDataActual.getO2(), sensorData.getO2());
		assertEquals(sensorDataActual.getCo(), sensorData.getCo());
		assertEquals(sensorDataActual.getSo2(), sensorData.getSo2());
		assertEquals(sensorDataActual.getC(), sensorData.getC());
		assertEquals(sensorDataActual.getSafety(), sensorData.getSafety());
		assertEquals(sensorDataActual.getSafeValue(), sensorData.getSafeValue());

	}

	// When Get All Sensor Data Successfully
	@DisplayName("Get All Data - Success")
	@Test
	void test_When_GetAllData_Success() {
		// Mocking
		SensorData sensorData = getMockSensorData();
		List<SensorData> sensorDatas = new ArrayList<>();
		sensorDatas.add(sensorData);
		when(sensorDataRepository.findAll()).thenReturn(sensorDatas);
		// Actual
		List<SensorData> sensorDatasActual = sensorDataService.getSensorDataAll();
		// Verify
		verify(sensorDataRepository, times(1)).findAll();
		// Assert

		assertNotNull(sensorDatasActual);
		assertEquals(sensorDatasActual, sensorDatas);
	}
	// When Safety Info Adds Success
	@DisplayName("Safety Info - Success")
	@Test
	void test_When_SafetyInfo_Success() {
		// Mocking
		SensorData sensorData = getMockSensorData();
		// Actual
		SensorData sensorDataActual = sensorDataService.addSafetyInfo(sensorData);
		//Assert
		assertNotNull(sensorDataActual);
		assertEquals(sensorDataActual.getDataId(), sensorData.getDataId());
		assertEquals(sensorDataActual.getSensorid(), sensorData.getSensorid());
		assertEquals(sensorDataActual.getCo2(), sensorData.getCo2());
		assertEquals(sensorDataActual.getO2(), sensorData.getO2());
		assertEquals(sensorDataActual.getCo(), sensorData.getCo());
		assertEquals(sensorDataActual.getSo2(), sensorData.getSo2());
		assertEquals(sensorDataActual.getC(), sensorData.getC());
		assertEquals(sensorDataActual.getSafety(), sensorData.getSafety());
		assertEquals(sensorDataActual.getSafeValue(), sensorData.getSafeValue());
	}

	// When SensorPlot Get Success
	@DisplayName("SensorPlot Get - Success")
	@Test
	void test_When_SensorPlot_Success() throws Exception{
		//Mocking
		when(restTemplate.getForObject(anyString(), any())).thenReturn(getMockSensorPlot());
		//Actual
		sensorDataService.validSensor("1");
		//verify
		verify(restTemplate,times(1)).getForObject(anyString(), any());
	}
	// When SensorPlot Get Fails
	@DisplayName("SensorPlot Get - Fails")
	@Test
	void test_When_SensorPlot_Failure() {
		//Mocking
		when(restTemplate.getForObject(anyString(), any())).thenThrow(InternalServerError.class);
		//Actual/Assert
		assertThrows(InternalServerError.class, ()->sensorDataService.validSensor("1"));
		//verify
		verify(restTemplate,times(1)).getForObject(anyString(), any());
	}

	// Mock Sensor Data
	private SensorData getMockSensorData() {
		BigDecimal value = new BigDecimal(45.2112);
		return SensorData.builder().dataId("1l").sensorid("1").o2(value).co2(value).so2(value).co(value).c(value).safeValue(value).safety(true)
				.build();
	}
	

	// Mock Sensor Plot
	private SensorPlot getMockSensorPlot() {
		return SensorPlot.builder().floor(1).room(1).sensorid(1L).build();
	}

}
