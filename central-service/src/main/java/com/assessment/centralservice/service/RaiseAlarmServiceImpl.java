package com.assessment.centralservice.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.centralservice.domain.Alarm;
import com.assessment.centralservice.repository.AlarmRepository;
import jakarta.transaction.Transactional;

public class RaiseAlarmServiceImpl implements RaiseAlarmService {

	private final AlarmRepository alarmRepository;

	private static final Logger logger = LoggerFactory.getLogger(RaiseAlarmServiceImpl.class);

	public RaiseAlarmServiceImpl(AlarmRepository alarmRepository) {
		this.alarmRepository = alarmRepository;
	}

	@Override
	@Transactional
	public RaiseAlarmResponse raise(RaiseAlarmRequest request) {
		LocalDateTime timestamp = LocalDateTime.now();
		Alarm alarm = new Alarm();
		alarm.setMeasurement(request.getMeasurement());
		alarm.setThresholdType(request.getThreshold().getThresholdType());
		alarm.setThresholdValue(request.getThreshold().getValue());
		alarm.setTimestamp(timestamp);
		alarm = alarmRepository.save(alarm);
		raise(alarm);
		return new RaiseAlarmResponse(alarm);
	}

	private void raise(Alarm alarm) {
		logger.info(
				"Triggering alarm warehouse : {}, sensor : {}, measurement : {}, value : {} threshold : {} , type: {} ",
				alarm.getMeasurement().getSensor().getWarehouse().getName(),
				alarm.getMeasurement().getSensor().getName(), alarm.getMeasurement().getSensor().getMeasurementType(),
				alarm.getMeasurement().getValue(), alarm.getThresholdValue(), alarm.getThresholdType());
	}

}
