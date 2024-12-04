package com.assessment.commons.dto;

public class MeasurementResponseDTO {

	private String responseMessage;

	private MeasurementDTO measurement;
	
	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public MeasurementDTO getMeasurement() {
		return measurement;
	}

	public void setMeasurement(MeasurementDTO measurement) {
		this.measurement = measurement;
	}

	@Override
	public String toString() {
		return "MeasurementResponseDTO [responseMessage=" + responseMessage + ", measurement=" + measurement + "]";
	}
	
	
	
	
}
