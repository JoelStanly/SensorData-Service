package com.aqms.sensordataservice.model;

import java.math.BigDecimal;

import com.aqms.sensordataservice.FileUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//POJO of the Sensor Stream/ Data
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="Sensordata")
public class SensorData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dataId;
	
	@Column(name = "sensorid",nullable = false)
	@Positive
	private Long sensorid;
	
	@Column(name = "o2",nullable = false)
	@DecimalMin(value= FileUtil.minO2)
	@DecimalMax(value=FileUtil.maxO2)
	private BigDecimal o2;
	
	@Column(name = "co2",nullable = false)
	@DecimalMin(value= FileUtil.minCO2)
	@DecimalMax(value=FileUtil.maxCO2)
	private BigDecimal co2;
	
	@Column(name = "so2",nullable = false)
	@DecimalMin(value= FileUtil.minSO2)
	@DecimalMax(value=FileUtil.maxSO2)
	private BigDecimal so2;
	
	@Column(name = "co",nullable = false)
	@DecimalMin(value= FileUtil.minCO)
	@DecimalMax(value=FileUtil.maxCO)
	private BigDecimal co;
	
	@Column(name = "c",nullable = false)
	@DecimalMin(value= FileUtil.minC)
	@DecimalMax(value=FileUtil.maxC)
	private BigDecimal c;
	
	@Column(name = "safeValue",nullable = false)
	private BigDecimal safeValue;
	
	@Column(name = "safety", nullable=false)
	private Boolean safety;


}
