package com.assessment.centralservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.centralservice.domain.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

	Optional<Warehouse> findByName(String warehouseName);

}
