package com.assessment.centralservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.centralservice.domain.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
