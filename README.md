# warehouse-monitoring-system

Warehouse monitoring system has a warehouse service managing sensors and a central service which processes measurements and raise alarms when thresholds are exceeded. It has 3 Maven modules commons, warehouse-service and central-service

## Installation

In the root folder warehouse-monitoring-system run 

```bash
mvn clean install
```

In central-service subfolder execute
 
```bash
java -jar target\central-service.jar 
```

to run the central-service. It exposes a Swagger endpoint on

[Central Service Swagger](https://localhost:8081/central-service/swagger-ui.html)

It uses an embedded H2 database to store warehosues, sensors, measurements and alarms

Its a Spring Boot based REST service

In warehouse-service folder execute 

```bash
java -jar target\central-service.jar 
```
It starts 2 UDP endpoints to receive temperature and humidity. It uses AKKA actors to push measurements to the database and publish to the central service
