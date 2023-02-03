package com.aqms.sensordataservice.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// POJO for the sensorPlot for getting and saving the data that we are getting from the sensor Plot service

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SensorPlot {
	private Long sensorid;
	private int floor;
	private int room;
}
