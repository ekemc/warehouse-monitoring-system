package com.assessment.warehouse.spring;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "props")
public class SystemParameters {

	private List<MeasurementEndpoint> endpoints;

	private String warehouseName;
	
	private String centralServerEndpoint;

	public List<MeasurementEndpoint> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<MeasurementEndpoint> endpoints) {
		this.endpoints = endpoints;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	
	public String getCentralServerEndpoint() {
		return centralServerEndpoint;
	}

	public void setCentralServerEndpoint(String centralServerEndpoint) {
		this.centralServerEndpoint = centralServerEndpoint;
	}

	@Override
	public String toString() {
		return "SystemParameters [endpoints=" + endpoints + ", warehouseName=" + warehouseName
				+ ", centralServerEndpoint=" + centralServerEndpoint + "]";
	}

	

}
