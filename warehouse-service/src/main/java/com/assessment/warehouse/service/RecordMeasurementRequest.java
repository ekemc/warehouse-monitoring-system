package com.assessment.warehouse.service;

import java.math.BigDecimal;

import com.assessment.commons.enums.MeasurementType;

public class RecordMeasurementRequest {

	private final String sensorName;
	
	private final MeasurementType measurementType;
	
	private final BigDecimal value;

	public RecordMeasurementRequest(String sensorName, MeasurementType measurementType, BigDecimal value) {
		this.sensorName = sensorName;
		this.measurementType = measurementType;
		this.value = value;
	}

	public String getSensorName() {
		return sensorName;
	}

	public MeasurementType getMeasurementType() {
		return measurementType;
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "RecordMeasurementRequest [sensorName=" + sensorName + ", measurementType=" + measurementType
				+ ", value=" + value + "]";
	}
	
	
	
}
