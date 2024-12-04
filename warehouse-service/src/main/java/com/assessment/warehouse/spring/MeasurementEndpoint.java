package com.assessment.warehouse.spring;

import com.assessment.commons.enums.MeasurementType;

public class MeasurementEndpoint {

	private String host;
	
	private int port;
	
	private MeasurementType type;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public MeasurementType getType() {
		return type;
	}

	public void setType(MeasurementType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MeasurementEndpoint [host=" + host + ", port=" + port + ", type=" + type + "]";
	}
	
	
	
}
