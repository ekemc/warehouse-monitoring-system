package com.assessment.centralservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.centralservice.domain.MeasurementThreshold;
import com.assessment.commons.enums.MeasurementType;

@Repository
public interface MeasurementThresholdRepository extends JpaRepository<MeasurementThreshold, Long>{

	List<MeasurementThreshold> findByMeasurementTypeAndActive(MeasurementType measurementType, Boolean active);

}
