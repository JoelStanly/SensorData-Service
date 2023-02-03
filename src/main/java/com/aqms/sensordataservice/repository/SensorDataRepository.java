package com.aqms.sensordataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aqms.sensordataservice.model.SensorData;


// Sensor Plot Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long>{

}
