@echo off

start "eureka-server" cmd /k "cd eureka-server && mvn spring-boot:run"
start "jugador-service" cmd /k "cd jugador-service && mvn spring-boot:run"
start "recinto-service" cmd /k "cd recinto-service && mvn spring-boot:run"
start "torneos-service" cmd /k "cd torneos-service && mvn spring-boot:run"
start "api-gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
