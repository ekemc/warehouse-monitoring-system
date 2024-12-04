package com.assessment.centralservice.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assessment.centralservice.repository.AlarmRepository;
import com.assessment.centralservice.repository.MeasurementRepository;
import com.assessment.centralservice.repository.MeasurementThresholdRepository;
import com.assessment.centralservice.repository.SensorRepository;
import com.assessment.centralservice.repository.WarehouseRepository;
import com.assessment.centralservice.service.ProcessMeasurementService;
import com.assessment.centralservice.service.ProcessMeasurementServiceImpl;
import com.assessment.centralservice.service.RaiseAlarmService;
import com.assessment.centralservice.service.RaiseAlarmServiceImpl;

@Configuration
public class ApplicationConfiguration {

	@Autowired
	private AlarmRepository alarmRepository;
	
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
	private SensorRepository sensorRepository;
	
	@Autowired
	private MeasurementRepository measurementRepository;
	
	@Autowired
	private MeasurementThresholdRepository measurementThresholdRepository;

	@Bean
	RaiseAlarmService raiseAlarmService() {
		return new RaiseAlarmServiceImpl(alarmRepository);
	}
	
	@Bean
	ProcessMeasurementService processMeasurementService() {
		return new ProcessMeasurementServiceImpl(warehouseRepository, sensorRepository, measurementRepository, measurementThresholdRepository, raiseAlarmService());
	}
	
}
