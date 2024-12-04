package com.assessment.warehouse.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assessment.warehouse.repository.MeasurementRepository;
import com.assessment.warehouse.repository.SensorRepository;
import com.assessment.warehouse.service.PublishMeasurementService;
import com.assessment.warehouse.service.PublishMeasurementServiceImpl;
import com.assessment.warehouse.service.RecordMeasurementService;
import com.assessment.warehouse.service.RecordMeasurementServiceImpl;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

@Configuration
@EnableConfigurationProperties({SystemParameters.class})
public class ApplicationConfiguration {

	@Autowired
	private SpringExtension springExtension;

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	ActorSystem actorSystem() {
		ActorSystem actorSystem = ActorSystem.create("warehouse-actor-system", akkaConfiguration());
		springExtension.initialize(applicationContext);
		return actorSystem;
	}

	@Bean
	Config akkaConfiguration() {
		return ConfigFactory.load();
	}
	
	@Bean
	RecordMeasurementService recordMeasurementService(SensorRepository sensorRepository, MeasurementRepository measurementRepository) {
		return new RecordMeasurementServiceImpl(sensorRepository, measurementRepository);
	}
	
	@Bean
	PublishMeasurementService publishMeasurementService(MeasurementRepository measurementRepository, RestTemplateBuilder restTemplateBuilder, SystemParameters systemParameters) {
		return new PublishMeasurementServiceImpl(measurementRepository, restTemplateBuilder.build(), systemParameters);
	}
}
