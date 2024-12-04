package com.assessment.centralservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.centralservice.domain.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long>{

}
