package com.assessment.centralservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.assessment.commons.enums.MeasurementType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class MeasurementThreshold implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private MeasurementType measurementType;

	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private ThresholdType thresholdType;

	@Column(nullable = false, name = "val")
	private BigDecimal value;

	@Column(nullable = false)
	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MeasurementType getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(MeasurementType measurementType) {
		this.measurementType = measurementType;
	}

	public ThresholdType getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(ThresholdType thresholdType) {
		this.thresholdType = thresholdType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "MeasurementThreshold [id=" + id + ", measurementType=" + measurementType + ", thresholdType="
				+ thresholdType + ", value=" + value + ", active=" + active + "]";
	}

}
