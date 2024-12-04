package com.assessment.warehouse.service;

import com.assessment.warehouse.domain.Measurement;

public final class RecordMeasurementResponse {

	private final Measurement measurement;

	public RecordMeasurementResponse(Measurement measurement) {
		this.measurement = measurement;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

}
