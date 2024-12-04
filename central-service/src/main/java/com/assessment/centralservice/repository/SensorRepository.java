package com.assessment.centralservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.centralservice.domain.Sensor;
import com.assessment.centralservice.domain.Warehouse;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{

	Optional<Sensor> findByWarehouseAndName(Warehouse warehouse, String sensorName);

}
