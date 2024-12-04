package com.assessment.warehouse.actor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.assessment.warehouse.service.PublishMeasurementRequest;
import com.assessment.warehouse.service.PublishMeasurementService;

import akka.actor.AbstractActor;


@Component
@Scope("prototype")
public class MeasurementPublishingActor extends AbstractActor {

	private PublishMeasurementService publishMeasurementService;

	public MeasurementPublishingActor(PublishMeasurementService publishMeasurementService) {
		this.publishMeasurementService = publishMeasurementService;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(MeasurementPublishingRequest.class, measurementPublishingRequest -> {
			publishMeasurementService.publish(new PublishMeasurementRequest(measurementPublishingRequest.getMeasurementId()));
		}).build();
	}

	public static class MeasurementPublishingRequest {

		private long measurementId;

		public MeasurementPublishingRequest(long measurementId) {
			this.measurementId = measurementId;
		}

		public long getMeasurementId() {
			return measurementId;
		}

		public void setMeasurementId(long measurementId) {
			this.measurementId = measurementId;
		}

	}
}
