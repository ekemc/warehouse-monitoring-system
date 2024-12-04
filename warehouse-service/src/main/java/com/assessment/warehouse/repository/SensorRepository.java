package com.assessment.warehouse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.commons.enums.MeasurementType;
import com.assessment.warehouse.domain.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{

	Optional<Sensor> findByNameAndMeasurementType(String sensorName, MeasurementType measurementType);

}
