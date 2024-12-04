package com.assessment.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.warehouse.domain.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long>{

}
