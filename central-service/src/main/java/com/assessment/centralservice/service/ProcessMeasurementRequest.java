package com.assessment.centralservice.service;

import java.math.BigDecimal;

import com.assessment.commons.enums.MeasurementType;

public class ProcessMeasurementRequest {

	private String warehouseName;
	
	private String sensorName;
	
	private MeasurementType measurementType;
	
	private BigDecimal value;

	public ProcessMeasurementRequest(String warehouseName, String sensorName, MeasurementType measurementType,
			BigDecimal value) {
		this.warehouseName = warehouseName;
		this.sensorName = sensorName;
		this.measurementType = measurementType;
		this.value = value;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public MeasurementType getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ProcessMeasurementRequest [warehouseName=" + warehouseName + ", sensorName=" + sensorName
				+ ", measurementType=" + measurementType + ", value=" + value + "]";
	}
	
	
}
