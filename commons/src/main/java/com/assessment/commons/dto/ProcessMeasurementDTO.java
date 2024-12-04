package com.assessment.commons.dto;

import java.math.BigDecimal;

import com.assessment.commons.enums.MeasurementType;

public class ProcessMeasurementDTO {

	private String warehouseName;

	private String sensorName;

	private MeasurementType measurementType;

	private BigDecimal value;

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
		return "ProcessMeasurementDTO [warehouseName=" + warehouseName + ", sensorName=" + sensorName + ", measurementType="
				+ measurementType + ", value=" + value + "]";
	}

}
