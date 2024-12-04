package com.assessment.warehouse.actor;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.assessment.commons.enums.MeasurementType;
import com.assessment.warehouse.actor.MeasurementPublishingActor.MeasurementPublishingRequest;
import com.assessment.warehouse.service.RecordMeasurementRequest;
import com.assessment.warehouse.service.RecordMeasurementResponse;
import com.assessment.warehouse.service.RecordMeasurementService;
import com.assessment.warehouse.spring.SpringExtension;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

@Component
@Scope("prototype")
public class MeasurementRecordingActor extends AbstractActor {

	private final RecordMeasurementService recordMeasurementService;

	private final SpringExtension springExtension;

	private final ActorSystem actorSystem;
	
	private static final Logger logger = LoggerFactory.getLogger(MeasurementRecordingActor.class);
	

	public MeasurementRecordingActor(RecordMeasurementService recordMeasurementService, SpringExtension springExtension,
			ActorSystem actorSystem) {
		this.recordMeasurementService = recordMeasurementService;
		this.springExtension = springExtension;
		this.actorSystem = actorSystem;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(MeasurementRecordingRequest.class, measurementRecordingRequest -> {
			logger.info("Measurement recording request {}", measurementRecordingRequest);
			RecordMeasurementResponse recordMeasurementResponse = recordMeasurementService.record(new RecordMeasurementRequest(measurementRecordingRequest.getSensorName(), measurementRecordingRequest.getType(), measurementRecordingRequest.getValue()));
			ActorRef measurementPublishingActor = actorSystem.actorOf(springExtension.props("measurementPublishingActor"),
					"measurement-publishing-actor");
			measurementPublishingActor.tell(new MeasurementPublishingRequest(recordMeasurementResponse.getMeasurement().getId()), self());
		}).build();
	}

	public static class MeasurementRecordingRequest {

		private final String sensorName;

		private final MeasurementType type;

		private final BigDecimal value;

		public MeasurementRecordingRequest(String sensorName, MeasurementType type, BigDecimal value) {
			this.sensorName = sensorName;
			this.type = type;
			this.value = value;
		}

		public String getSensorName() {
			return sensorName;
		}

		public MeasurementType getType() {
			return type;
		}

		public BigDecimal getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "MeasurementRecordingRequest [sensorName=" + sensorName + ", type=" + type + ", value=" + value
					+ "]";
		}

	}
}
