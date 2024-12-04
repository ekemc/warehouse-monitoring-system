package com.assessment.commons.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MeasurementDTO {

	private Long id;

	private SensorDTO sensor;

	private LocalDateTime timestamp;

	private BigDecimal value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SensorDTO getSensor() {
		return sensor;
	}

	public void setSensor(SensorDTO sensor) {
		this.sensor = sensor;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MeasurementDTO [id=" + id + ", sensor=" + sensor + ", timestamp=" + timestamp + ", value=" + value
				+ "]";
	}

	
}
