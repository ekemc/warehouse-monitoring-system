package com.assessment.centralservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.assessment.centralservice.domain.Measurement;
import com.assessment.centralservice.domain.MeasurementThreshold;
import com.assessment.centralservice.domain.Sensor;
import com.assessment.centralservice.domain.ThresholdType;
import com.assessment.centralservice.domain.Warehouse;
import com.assessment.centralservice.repository.MeasurementRepository;
import com.assessment.centralservice.repository.MeasurementThresholdRepository;
import com.assessment.centralservice.repository.SensorRepository;
import com.assessment.centralservice.repository.WarehouseRepository;
import com.assessment.commons.enums.MeasurementType;
import com.assessment.commons.exception.ValidationException;

import jakarta.transaction.Transactional;

public class ProcessMeasurementServiceImpl implements ProcessMeasurementService {

	private final WarehouseRepository warehouseRepository;

	private final SensorRepository sensorRepository;

	private final MeasurementRepository measurementRepository;

	private final MeasurementThresholdRepository measurementThresholdRepository;

	private final RaiseAlarmService raiseAlarmService;

	private static final Logger logger = LoggerFactory.getLogger(ProcessMeasurementServiceImpl.class);

	public ProcessMeasurementServiceImpl(WarehouseRepository warehouseRepository, SensorRepository sensorRepository,
			MeasurementRepository measurementRepository, MeasurementThresholdRepository measurementThresholdRepository,
			RaiseAlarmService raiseAlarmService) {
		this.warehouseRepository = warehouseRepository;
		this.sensorRepository = sensorRepository;
		this.measurementRepository = measurementRepository;
		this.measurementThresholdRepository = measurementThresholdRepository;
		this.raiseAlarmService = raiseAlarmService;
	}

	@Override
	@Transactional
	public ProcessMeasurementResponse process(ProcessMeasurementRequest request) {
		LocalDateTime timestamp = LocalDateTime.now();
		logger.info("Processing request {}", request);
		validateRequest(request);
		Warehouse warehouse = warehouseRepository.findByName(request.getWarehouseName())
				.orElse(createNewWarehouse(request.getWarehouseName(), timestamp));
		Sensor sensor = sensorRepository.findByWarehouseAndName(warehouse, request.getSensorName())
				.orElse(createNewSensor(warehouse, request.getSensorName(), request.getMeasurementType(), timestamp));
		Measurement measurement = new Measurement();
		measurement.setSensor(sensor);
		measurement.setTimestamp(timestamp);
		measurement.setValue(request.getValue());
		measurement = measurementRepository.save(measurement);
		evaluateThresholds(measurement);
		return new ProcessMeasurementResponse(measurement);
	}

	private void evaluateThresholds(Measurement measurement) {
		List<MeasurementThreshold> configuredThresholds = measurementThresholdRepository
				.findByMeasurementTypeAndActive(measurement.getSensor().getMeasurementType(), true);
		configuredThresholds.forEach(threshold -> {
			RaiseAlarmRequest raiseAlarmRequest = null;
			if(threshold.getThresholdType() == ThresholdType.EXCEEDS) {
				if(measurement.getValue().compareTo(threshold.getValue()) > 0) {
					raiseAlarmRequest = new RaiseAlarmRequest(measurement, threshold);
				}
			}
			else {
				if(measurement.getValue().compareTo(threshold.getValue()) < 0) {
					raiseAlarmRequest = new RaiseAlarmRequest(measurement, threshold);
				}
			}
			if(raiseAlarmRequest != null) {
				raiseAlarmService.raise(raiseAlarmRequest);
			}
		});
	}

	private Sensor createNewSensor(Warehouse warehouse, String sensorName, MeasurementType measurementType,
			LocalDateTime timestamp) {
		Sensor sensor = new Sensor();
		sensor.setMeasurementType(measurementType);
		sensor.setWarehouse(warehouse);
		sensor.setName(sensorName);
		sensor.setDateCreated(timestamp);
		sensor.setDateModified(timestamp);
		return sensorRepository.save(sensor);
	}

	private Warehouse createNewWarehouse(String warehouseName, LocalDateTime timestamp) {
		Warehouse warehouse = new Warehouse();
		warehouse.setDateCreated(timestamp);
		warehouse.setDateModified(timestamp);
		warehouse.setName(warehouseName);
		return warehouseRepository.save(warehouse);
	}

	private void validateRequest(ProcessMeasurementRequest request) {
		if (request.getMeasurementType() == null) {
			throw new ValidationException("Measurement type is required");
		}
		if (!StringUtils.hasText(request.getSensorName())) {
			throw new ValidationException("Sensor name is required");
		}
		if (!StringUtils.hasText(request.getWarehouseName())) {
			throw new ValidationException("Warehouse name is required");
		}
		if (request.getValue() == null) {
			throw new ValidationException("Measurement value is required");
		}
	}

}
