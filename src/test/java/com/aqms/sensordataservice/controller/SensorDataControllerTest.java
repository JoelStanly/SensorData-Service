package com.aqms.sensordataservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.aqms.sensordataservice.VO.SensorPlot;
import com.aqms.sensordataservice.model.SensorData;
import com.aqms.sensordataservice.service.SensorDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SensorDataController.class)
public class SensorDataControllerTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SensorDataService sensorDataService;

	// Post Sensor Data Successfully
	@DisplayName("Post SensorData")
	@Test
	public void test_Post_SensorData() throws Exception {
		
		//Mocking
		SensorData sensorData = getMockSensorData();

		when(sensorDataService.addSafetyInfo(any())).thenReturn(sensorData);
		when(sensorDataService.saveSensorData(any())).thenReturn(sensorData);
		
		//Actual
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/sensorData/post")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(sensorData)))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		SensorData sensorDataResult = objectMapper.readValue(resultContent, SensorData.class);
		
		//Assert
		assertNotNull(sensorDataResult);
		assertEquals(sensorDataResult.getDataId(), sensorData.getDataId());
		assertEquals(sensorDataResult.getSensorid(), sensorData.getSensorid());
		assertEquals(sensorDataResult.getCo2(), sensorData.getCo2());
		assertEquals(sensorDataResult.getO2(), sensorData.getO2());
		assertEquals(sensorDataResult.getCo(), sensorData.getCo());
		assertEquals(sensorDataResult.getSo2(), sensorData.getSo2());
		assertEquals(sensorDataResult.getC(), sensorData.getC());
		
		//Verify
		verify(sensorDataService,times(1)).saveSensorData(any());
		

	}


	// Find All Sensor Data Successfully
	@DisplayName("Find All SensorData")
	@Test
	public void test_FindAll_SensorData() throws Exception {

		//Mocking
		SensorData sensorData = getMockSensorData();
		List<SensorData> sensorDatas = new ArrayList<>();
		sensorDatas.add(sensorData);
		when(sensorDataService.getSensorDataAll()).thenReturn(sensorDatas);
		//Actual
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sensorData/all")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		SensorData[] sensorDataResult = objectMapper.readValue(resultContent, SensorData[].class);

		//Assert
		assertNotNull(sensorDataResult);
		assertEquals(sensorDataResult[0].getDataId(), sensorData.getDataId());
		assertEquals(sensorDataResult[0].getSensorid(), sensorData.getSensorid());
		assertEquals(sensorDataResult[0].getCo2(), sensorData.getCo2());
		assertEquals(sensorDataResult[0].getO2(), sensorData.getO2());
		assertEquals(sensorDataResult[0].getCo(), sensorData.getCo());
		assertEquals(sensorDataResult[0].getSo2(), sensorData.getSo2());
		assertEquals(sensorDataResult[0].getC(), sensorData.getC());
		assertEquals(sensorDataResult[0].getSafety(), sensorData.getSafety());
		assertEquals(sensorDataResult[0].getSafeValue(), sensorData.getSafeValue());
		//Verify
		verify(sensorDataService,times(1)).getSensorDataAll();
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
