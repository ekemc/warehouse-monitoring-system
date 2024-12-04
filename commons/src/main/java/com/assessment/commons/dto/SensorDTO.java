package com.assessment.commons.dto;

public class SensorDTO {

	private Long id;

	private WarehouseDTO warehouse;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WarehouseDTO getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(WarehouseDTO warehouse) {
		this.warehouse = warehouse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SensorDTO [id=" + id + ", warehouse=" + warehouse + ", name=" + name + "]";
	}

}
