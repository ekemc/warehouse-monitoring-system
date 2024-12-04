package com.assessment.centralservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.centralservice.service.ProcessMeasurementRequest;
import com.assessment.centralservice.service.ProcessMeasurementResponse;
import com.assessment.centralservice.service.ProcessMeasurementService;
import com.assessment.commons.dto.ProcessMeasurementDTO;
import com.assessment.commons.dto.SensorDTO;
import com.assessment.commons.dto.WarehouseDTO;
import com.assessment.commons.dto.MeasurementDTO;
import com.assessment.commons.dto.MeasurementResponseDTO;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/measurement")
public class MeasurementController {

	@Autowired
	private ProcessMeasurementService processMeasurementService;

	private static final Logger logger = LoggerFactory.getLogger(MeasurementController.class);

	@PostMapping
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "401", description = "Unauthorized. authentication failed"),
			@ApiResponse(responseCode = "400", description = "Bad request, missing required fields"),
			@ApiResponse(responseCode = "500", description = "Processing error") })
	public ResponseEntity<MeasurementResponseDTO> process(@RequestBody ProcessMeasurementDTO measurement) {
		logger.info("Received request {}", measurement);
		ProcessMeasurementRequest processMeasurementRequest = new ProcessMeasurementRequest(
				measurement.getWarehouseName(), measurement.getSensorName(), measurement.getMeasurementType(),
				measurement.getValue());
		ProcessMeasurementResponse processMeasurementResponse = processMeasurementService
				.process(processMeasurementRequest);
		MeasurementDTO measurementDTO = new MeasurementDTO();
		WarehouseDTO warehouseDTO = new WarehouseDTO();
		warehouseDTO.setId(processMeasurementResponse.getMeasurement().getSensor().getWarehouse().getId());
		warehouseDTO.setName(processMeasurementResponse.getMeasurement().getSensor().getWarehouse().getName());
		SensorDTO sensorDTO = new SensorDTO();
		sensorDTO.setWarehouse(warehouseDTO);
		sensorDTO.setName(processMeasurementResponse.getMeasurement().getSensor().getName());
		sensorDTO.setId(processMeasurementResponse.getMeasurement().getSensor().getId());
		measurementDTO.setId(processMeasurementResponse.getMeasurement().getId());
		measurementDTO.setSensor(sensorDTO);
		measurementDTO.setTimestamp(processMeasurementResponse.getMeasurement().getTimestamp());
		measurementDTO.setValue(processMeasurementResponse.getMeasurement().getValue());
		MeasurementResponseDTO measurementResponseDTO = new MeasurementResponseDTO();
		measurementResponseDTO.setMeasurement(measurementDTO);
		measurementResponseDTO.setResponseMessage("OK");
		return ResponseEntity.ok(measurementResponseDTO);
	}

}
