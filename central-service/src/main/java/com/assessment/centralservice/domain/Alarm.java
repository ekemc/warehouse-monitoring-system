package com.assessment.centralservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Alarm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Measurement measurement;

	@Column(nullable = false)
	private BigDecimal thresholdValue;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100)
	private ThresholdType thresholdType;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	public BigDecimal getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(BigDecimal thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	public ThresholdType getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(ThresholdType thresholdType) {
		this.thresholdType = thresholdType;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Alarm [id=" + id + ", measurement=" + measurement + ", thresholdValue=" + thresholdValue
				+ ", thresholdType=" + thresholdType + ", timestamp=" + timestamp + "]";
	}

	
}
