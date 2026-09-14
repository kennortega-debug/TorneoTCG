#!/bin/bash

cd eureka-server
mvn spring-boot:run &
cd ..

cd jugador-service
mvn spring-boot:run &
cd ..

cd recinto-service
mvn spring-boot:run &
cd ..

cd torneos-service
mvn spring-boot:run &
cd ..

cd api-gateway
mvn spring-boot:run &
cd ..

wait
