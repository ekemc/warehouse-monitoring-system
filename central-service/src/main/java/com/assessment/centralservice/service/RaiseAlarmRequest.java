package com.assessment.centralservice.service;

import com.assessment.centralservice.domain.Measurement;
import com.assessment.centralservice.domain.MeasurementThreshold;

public class RaiseAlarmRequest {

	private final Measurement measurement;
	
	private final MeasurementThreshold threshold;

	public RaiseAlarmRequest(Measurement measurement, MeasurementThreshold threshold) {
		this.measurement = measurement;
		this.threshold = threshold;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public MeasurementThreshold getThreshold() {
		return threshold;
	}

	@Override
	public String toString() {
		return "RaiseAlarmRequest [measurement=" + measurement + ", threshold=" + threshold + "]";
	}

	
	
}
