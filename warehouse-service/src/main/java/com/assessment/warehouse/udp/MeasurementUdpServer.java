package com.assessment.warehouse.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.commons.enums.MeasurementType;
import com.assessment.warehouse.actor.MeasurementParserActor.MeasurementParseRequest;
import akka.actor.ActorRef;

public class MeasurementUdpServer {

	private final String host;

	private final int port;

	private final MeasurementType measurementType;

	private final ActorRef measurementParserActor;

	private DatagramSocket socket;
	
	private static final Logger logger = LoggerFactory.getLogger(MeasurementUdpServer.class);
	
	private static final int BUFFER_SIZE = 65535;

	public MeasurementUdpServer(String host, int port, MeasurementType measurementType,
			ActorRef measurementParserActor) {
		this.host = host;
		this.port = port;
		this.measurementType = measurementType;
		this.measurementParserActor = measurementParserActor;
	}

	public void startServer() throws IOException {
		socket = new DatagramSocket(port, InetAddress.getByName(host));
		logger.info("Started {} UDP server {}/{}", measurementType, host, port);
		byte[] data = new byte[BUFFER_SIZE];
		DatagramPacket receivedPacket;
		while (true) {
			receivedPacket = new DatagramPacket(data, data.length);
			socket.receive(receivedPacket);
			String clientMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
			logger.info("Received {} from {}/{}", clientMessage, receivedPacket.getAddress(), receivedPacket.getPort());
			measurementParserActor.tell(new MeasurementParseRequest(clientMessage, measurementType),
					ActorRef.noSender());
			socket.send(new DatagramPacket(data, receivedPacket.getLength(), receivedPacket.getAddress(),
					receivedPacket.getPort()));
			data = new byte[BUFFER_SIZE];
		}
	}

	public void stopServer() {
		if (socket != null) {
			socket.close();
			logger.info("Stopped {} UDP server {}/{}", measurementType, host, port);
		}
	}
}