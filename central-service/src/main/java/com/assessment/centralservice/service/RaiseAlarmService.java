package com.assessment.centralservice.service;

@FunctionalInterface
public interface RaiseAlarmService {

	RaiseAlarmResponse raise(RaiseAlarmRequest request);
	
}
