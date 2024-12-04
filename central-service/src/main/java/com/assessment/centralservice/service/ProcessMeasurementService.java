package com.assessment.centralservice.service;

@FunctionalInterface
public interface ProcessMeasurementService {

	ProcessMeasurementResponse process(ProcessMeasurementRequest request);
	
}
