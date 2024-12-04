package com.assessment.warehouse.actor;

import java.text.MessageFormat;
import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.assessment.commons.enums.MeasurementType;
import com.assessment.warehouse.actor.MeasurementRecordingActor.MeasurementRecordingRequest;
import com.assessment.warehouse.spring.SpringExtension;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.math.BigDecimal;

@Component
@Scope("prototype")
public class MeasurementParserActor extends AbstractActor {

	private final MessageFormat messageFormat = new MessageFormat("sensor_id={0};value={1}");

	private final SpringExtension springExtension;

	private final ActorSystem actorSystem;
	
	private static final Logger logger = LoggerFactory.getLogger(MeasurementParserActor.class);
	
	
	public MeasurementParserActor(SpringExtension springExtension, ActorSystem actorSystem) {
		this.springExtension = springExtension;
		this.actorSystem = actorSystem;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(MeasurementParseRequest.class, measurementParseRequest -> {
			// sensor_id=t1;value=30
			try {
				logger.debug("Measurement parse request {}", measurementParseRequest);
				Object[] tokens = messageFormat.parse(measurementParseRequest.getPayload());
				ActorRef measurementRecordingActor = actorSystem.actorOf(springExtension.props("measurementRecordingActor"),
						"measurement-recording-actor");
				measurementRecordingActor.tell(new MeasurementRecordingRequest((String)tokens[0], measurementParseRequest.getMeasurementType(), new BigDecimal((String)tokens[1])), self());
				//
			} catch (ParseException parseException) {
				logger.error("Failed to parse {} due to error", measurementParseRequest.getPayload(), parseException);
				unhandled(measurementParseRequest);
			}
		}).build();
	}
	
	public static class MeasurementParseRequest{
		
		private final String payload;
		
		private final MeasurementType measurementType;

		public MeasurementParseRequest(String payload, MeasurementType measurementType) {
			this.payload = payload;
			this.measurementType = measurementType;
		}

		public String getPayload() {
			return payload;
		}

		public MeasurementType getMeasurementType() {
			return measurementType;
		}

		@Override
		public String toString() {
			return "MeasurementParseRequest [payload=" + payload + ", measurementType=" + measurementType + "]";
		}
		
	}

}
