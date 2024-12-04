package com.assessment.warehouse.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.commons.enums.MeasurementType;
import com.assessment.warehouse.domain.Measurement;
import com.assessment.warehouse.domain.Sensor;
import com.assessment.warehouse.repository.MeasurementRepository;
import com.assessment.warehouse.repository.SensorRepository;

public class RecordMeasurementServiceImpl implements RecordMeasurementService{

	private final SensorRepository sensorRepository;
	
	private final MeasurementRepository measurementRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(RecordMeasurementServiceImpl.class);
	
	public RecordMeasurementServiceImpl(SensorRepository sensorRepository,
			MeasurementRepository measurementRepository) {
		this.sensorRepository = sensorRepository;
		this.measurementRepository = measurementRepository;
	}


	@Override
	@Transactional
	public RecordMeasurementResponse record(RecordMeasurementRequest request) {
		LocalDateTime timestamp = LocalDateTime.now();
		validateRequest(request);
		Sensor sensor = sensorRepository.findByNameAndMeasurementType(request.getSensorName(), request.getMeasurementType())
				.orElse(createNewSensor(request.getSensorName(), request.getMeasurementType()));
		Measurement measurement = new Measurement();
		measurement.setSensor(sensor);
		measurement.setTimestamp(timestamp);
		measurement.setValue(request.getValue());
		Measurement savedMeasurement = measurementRepository.save(measurement);
		logger.info("Recorded measurement {}", savedMeasurement);
		return new RecordMeasurementResponse(savedMeasurement);
	}


	private void validateRequest(RecordMeasurementRequest request) {
		// TODO Auto-generated method stub
		
	}


	private Sensor createNewSensor(String sensorName, MeasurementType measurementType) {
		Sensor sensor = new Sensor();
		sensor.setMeasurementType(measurementType);
		sensor.setName(sensorName);
		return sensorRepository.save(sensor);
	}

}
