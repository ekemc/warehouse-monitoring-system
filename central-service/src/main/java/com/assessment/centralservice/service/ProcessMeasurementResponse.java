package com.assessment.centralservice.service;

import com.assessment.centralservice.domain.Measurement;

public final class ProcessMeasurementResponse {

	private final Measurement measurement;

	public ProcessMeasurementResponse(Measurement measurement) {
		this.measurement = measurement;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	@Override
	public String toString() {
		return "ProcessMeasurementResponse [measurement=" + measurement + "]";
	}
	
	
}
