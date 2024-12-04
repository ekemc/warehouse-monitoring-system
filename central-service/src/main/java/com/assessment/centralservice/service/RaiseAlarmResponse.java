package com.assessment.centralservice.service;

import com.assessment.centralservice.domain.Alarm;

public final class RaiseAlarmResponse {

	private final Alarm alarm;

	public RaiseAlarmResponse(Alarm alarm) {
		this.alarm = alarm;
	}

	public Alarm getAlarm() {
		return alarm;
	}
	
	
}
