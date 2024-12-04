package com.assessment.centralservice.service;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

public class ProcessMeasurementServiceImplTest {

	private WarehouseRepository warehouseRepository;

	private SensorRepository sensorRepository;

	private MeasurementRepository measurementRepository;

	private MeasurementThresholdRepository measurementThresholdRepository;

	private RaiseAlarmService raiseAlarmService;
	
	private ProcessMeasurementService processMeasurementService;
	
	@BeforeEach
	public void init() {
		warehouseRepository = mock(WarehouseRepository.class);
		sensorRepository = mock(SensorRepository.class);
		measurementRepository = mock(MeasurementRepository.class);
		measurementThresholdRepository = mock(MeasurementThresholdRepository.class);
		raiseAlarmService = mock(RaiseAlarmService.class);
		processMeasurementService = new ProcessMeasurementServiceImpl(warehouseRepository, sensorRepository, measurementRepository, measurementThresholdRepository, raiseAlarmService);
	}
	
	@Test
	public void shouldProcessMeasurement() {
		ProcessMeasurementRequest processMeasurementRequest = new ProcessMeasurementRequest("w1", "t1", MeasurementType.TEMPERATURE, new BigDecimal("40"));
		Warehouse warehouse = new Warehouse();
		warehouse.setId(1L);
		warehouse.setName(processMeasurementRequest.getWarehouseName());
		when(warehouseRepository.findByName(processMeasurementRequest.getWarehouseName())).thenReturn(Optional.of(warehouse));
		
		Sensor sensor = new Sensor();
		sensor.setMeasurementType(processMeasurementRequest.getMeasurementType());
		sensor.setId(1L);
		sensor.setName(processMeasurementRequest.getSensorName());
		sensor.setWarehouse(warehouse);
		
		when(sensorRepository.findByWarehouseAndName(warehouse, processMeasurementRequest.getSensorName())).thenReturn(Optional.of(sensor));
		doAnswer(returnsFirstArg()).when(measurementRepository).save(any(Measurement.class));
		when(measurementThresholdRepository.findByMeasurementTypeAndActive(processMeasurementRequest.getMeasurementType(), true)).thenReturn(List.of());
		ProcessMeasurementResponse response = processMeasurementService.process(processMeasurementRequest);
		assertThat(response.getMeasurement().getSensor()).isSameAs(sensor);
		assertThat(response.getMeasurement().getValue()).isEqualByComparingTo(processMeasurementRequest.getValue());
	}
	
	@Test
	public void shouldRaiseAlarmWhenThresholdIsExceeded() {
		ProcessMeasurementRequest processMeasurementRequest = new ProcessMeasurementRequest("w1", "t1", MeasurementType.TEMPERATURE, new BigDecimal("40"));
		Warehouse warehouse = new Warehouse();
		warehouse.setId(1L);
		warehouse.setName(processMeasurementRequest.getWarehouseName());
		when(warehouseRepository.findByName(processMeasurementRequest.getWarehouseName())).thenReturn(Optional.of(warehouse));
		
		Sensor sensor = new Sensor();
		sensor.setMeasurementType(processMeasurementRequest.getMeasurementType());
		sensor.setId(1L);
		sensor.setName(processMeasurementRequest.getSensorName());
		sensor.setWarehouse(warehouse);
		
		when(sensorRepository.findByWarehouseAndName(warehouse, processMeasurementRequest.getSensorName())).thenReturn(Optional.of(sensor));
		doAnswer(returnsFirstArg()).when(measurementRepository).save(any(Measurement.class));
		MeasurementThreshold measurementThreshold = new MeasurementThreshold();
		measurementThreshold.setActive(true);
		measurementThreshold.setId(1L);
		measurementThreshold.setValue(new BigDecimal("35"));
		measurementThreshold.setThresholdType(ThresholdType.EXCEEDS);
		measurementThreshold.setMeasurementType(processMeasurementRequest.getMeasurementType());
		when(measurementThresholdRepository.findByMeasurementTypeAndActive(processMeasurementRequest.getMeasurementType(), true)).thenReturn(List.of(measurementThreshold));
		processMeasurementService.process(processMeasurementRequest);
		
		verify(raiseAlarmService).raise(any(RaiseAlarmRequest.class));
	}
	
	@Test
	public void shouldThrowExceptionWhenMeasurementTypeIsNotSupplied() {
		ProcessMeasurementRequest processMeasurementRequest = new ProcessMeasurementRequest("w1", "t1", null, new BigDecimal("30"));
		assertThrows(ValidationException.class, () -> processMeasurementService.process(processMeasurementRequest));
	}
	

	@Test
	public void shouldThrowExceptionWhenWarehouseNameIsNotSupplied() {
		ProcessMeasurementRequest processMeasurementRequest = new ProcessMeasurementRequest("", "t1", MeasurementType.TEMPERATURE, new BigDecimal("30"));
		assertThrows(ValidationException.class, () -> processMeasurementService.process(processMeasurementRequest));
	}
	
	

	@Test
	public void shouldThrowExceptionWhenSensorNameIsNotSupplied() {
		ProcessMeasurementRequest processMeasurementRequest = new ProcessMeasurementRequest("w1", "", MeasurementType.TEMPERATURE, new BigDecimal("30"));
		assertThrows(ValidationException.class, () -> processMeasurementService.process(processMeasurementRequest));
	}
	

	@Test
	public void shouldThrowExceptionWhenMeasurementValueIsNotSupplied() {
		ProcessMeasurementRequest processMeasurementRequest = new ProcessMeasurementRequest("w1", "t1", MeasurementType.TEMPERATURE, null);
		assertThrows(ValidationException.class, () -> processMeasurementService.process(processMeasurementRequest));
	}
}
