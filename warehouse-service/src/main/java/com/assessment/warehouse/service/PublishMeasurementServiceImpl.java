package com.assessment.warehouse.service;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.assessment.commons.dto.MeasurementResponseDTO;
import com.assessment.commons.dto.ProcessMeasurementDTO;
import com.assessment.commons.exception.ValidationException;
import com.assessment.warehouse.domain.Measurement;
import com.assessment.warehouse.repository.MeasurementRepository;
import com.assessment.warehouse.spring.SystemParameters;

public class PublishMeasurementServiceImpl implements PublishMeasurementService{

	private final MeasurementRepository measurementRepository;
	
	private final RestTemplate restTemplate;
	
	private final SystemParameters systemParameters;
	
	private static final Logger logger = LoggerFactory.getLogger(PublishMeasurementServiceImpl.class);
	
	
	public PublishMeasurementServiceImpl(MeasurementRepository measurementRepository, RestTemplate restTemplate,
			SystemParameters systemParameters) {
		this.measurementRepository = measurementRepository;
		this.restTemplate = restTemplate;
		this.systemParameters = systemParameters;
	}


	@Override
	public void publish(PublishMeasurementRequest request) {
		ProcessMeasurementDTO processMeasurementDTO = new ProcessMeasurementDTO();
		Measurement measurement = measurementRepository.findById(request.getMeasurementId())
				.orElseThrow(() -> new ValidationException("Measurement with id " +  request.getMeasurementId() + " not found"));
		processMeasurementDTO.setMeasurementType(measurement.getSensor().getMeasurementType());
		processMeasurementDTO.setSensorName(measurement.getSensor().getName());
		processMeasurementDTO.setValue(measurement.getValue());
		processMeasurementDTO.setWarehouseName(systemParameters.getWarehouseName());
		logger.info("Publishing measurement {} to central server {}", processMeasurementDTO, systemParameters.getCentralServerEndpoint());
		
		try {
			RequestEntity<ProcessMeasurementDTO> requestEntity = RequestEntity.post(URI.create(systemParameters.getCentralServerEndpoint()))
					.body(processMeasurementDTO);
			ResponseEntity<MeasurementResponseDTO> responseEntity = restTemplate.exchange(requestEntity, MeasurementResponseDTO.class);
			logger.info("Central server response {}", responseEntity);
			
		}
		catch(Exception ex) {
			logger.error("Failed to publish {} to central server {} due to error", processMeasurementDTO, systemParameters.getCentralServerEndpoint(), ex);
		}
	}

}
