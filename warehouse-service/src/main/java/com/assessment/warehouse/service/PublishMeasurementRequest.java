package com.assessment.warehouse.service;

public class PublishMeasurementRequest {

	private final long measurementId;

	public PublishMeasurementRequest(long measurementId) {
		this.measurementId = measurementId;
	}

	public long getMeasurementId() {
		return measurementId;
	}

	@Override
	public String toString() {
		return "PublishMeasurementRequest [measurementId=" + measurementId + "]";
	}

}
