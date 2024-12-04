package com.assessment.warehouse.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.assessment.warehouse.udp.MeasurementUdpServer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import jakarta.annotation.PreDestroy;

@Component
public class MeasurementUdpServerBootStrapper implements ApplicationRunner {

	private final SystemParameters parameters;

	private final SpringExtension springExtension;

	private final ActorSystem actorSystem;

	private final List<MeasurementUdpServer> servers;

	private final ExecutorService executorService;
	
	private static final Logger logger = LoggerFactory.getLogger(MeasurementUdpServerBootStrapper.class);

	public MeasurementUdpServerBootStrapper(SystemParameters parameters, SpringExtension springExtension,
			ActorSystem actorSystem) {
		this.parameters = parameters;
		this.springExtension = springExtension;
		this.actorSystem = actorSystem;
		this.executorService = Executors.newFixedThreadPool(parameters.getEndpoints().size());
		this.servers = new ArrayList<MeasurementUdpServer>(parameters.getEndpoints().size());
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Bootstrapping UDP servers {}", parameters.getEndpoints());
		ActorRef measurementParserActor = actorSystem.actorOf(springExtension.props("measurementParserActor"),
				"measurement-parser-actor");
		parameters.getEndpoints().forEach(endpoint -> {
			MeasurementUdpServer measurementUdpServer = new MeasurementUdpServer(endpoint.getHost(), endpoint.getPort(),
					endpoint.getType(), measurementParserActor);
			executorService.submit(() -> {
				try {
					measurementUdpServer.startServer();
				} catch (IOException e) {
					logger.error("Failed to start UDP server {} due to error", endpoint, e);
				}
			});
			servers.add(measurementUdpServer);
		});

	}

	@PreDestroy
	public void stopServers() {
		servers.forEach(MeasurementUdpServer::stopServer);
	}

}
