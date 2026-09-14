# Torneo TCG - Magic: The Gathering (Backend)

Sistema distribuido basado en arquitectura de microservicios para la gestión completa de torneos de Magic Commander. Este proyecto permite administrar jugadores, mazos, inscripciones, generación de rondas y registro de resultados.


##  Arquitectura y Microservicios Implementados

El sistema está construido bajo el patrón de diseño **CSR (Controller-Service-Repository)**  y se compone de los siguientes microservicios independientes:

1. **Microservicio Jugadores y Mazos:** Gestión de usuarios y la composición de sus cartas.
2. **Microservicio Torneos y Rondas:** Estructura de competiciones e inscripciones.
3. **Microservicio Partidas y Resultados:** Registro de enfrentamientos y puntajes.
4. **Microservicio Infraestructura:** Gestión de locales, organizadores y ubicaciones.
5. **API Gateway:** Enrutador central que unifica los endpoints (ej. `http://localhost:8080/api/v1/...`).
6. **Eureka Server:** Servicio de descubrimiento (Service Discovery) para la conexión dinámica entre microservicios sin depender de puertos estáticos.

---

## 🛠️ Tecnologías y Configuraciones Destacadas

Este ecosistema backend incorpora las siguientes tecnologías y buenas prácticas:

* **Java & Spring Boot:** Framework principal.
* **Spring Cloud Netflix Eureka:** Para el registro y descubrimiento automático de microservicios.
* **Spring Cloud Gateway:** Enrutamiento centralizado y balanceo de carga (`@LoadBalanced`).
* **Comunicación Inter-servicios:** Uso de DTOs externos con `@JsonIgnoreProperties(ignoreUnknown = true)` para manejar datos cruzados.
* **HATEOAS:** Implementado para respuestas de API más enriquecidas y navegables.
* **Perfiles YAML (`application.yml`):** Configuraciones dinámicas para entornos de desarrollo (`dev`), pruebas (`test`) y producción (`prod`).
* **Documentación Centralizada (Swagger/OpenAPI):** Unificación de la documentación de todos los microservicios accesible desde el Gateway.
* **CORS Origin:** Configuración implementada en los microservicios mediante `WebConfig` para permitir el consumo desde aplicaciones Frontend.
* **Validaciones:** Lógica de validación separada en clases específicas (ej. `JugadorValidaciones`) para mantener los Services ligeros.

---

## Instrucciones de Ejecución (Local)

Para facilitar el despliegue de todos los microservicios al mismo tiempo, hemos creado scripts de automatización.

**Para Windows:**
1. Clona el repositorio.
2. Haz doble clic en el archivo `iniciar-todo.bat`.

**Para Mac / Linux:**
1. Abre una terminal en la raíz del proyecto.
2. Otorga permisos de ejecución: `chmod +x iniciar-todo.sh`
3. Ejecuta el script: `./iniciar-todo.sh`

---

## 🔗 Enlaces y Rutas Principales

### API Gateway (Rutas Base)
Todas las peticiones deben pasar por el API Gateway en el puerto `8080`:
* Jugadores: `http://localhost:8080/api/v1/jugadores`
* Torneos: `http://localhost:8080/api/v1/torneos`
* 

### Documentación Swagger
Puedes visualizar los endpoints, modelos de datos y probar la API directamente en:
* 📖 **[Swagger UI Unificado](http://localhost:8080/swagger-ui/index.html)**

### Eureka Server
Para verificar que todos los microservicios están levantados y registrados:
* 🌐 **[Eureka Dashboard](http://localhost:8761/eureka/)**

---

## Pruebas Unitarias
El proyecto cuenta con pruebas unitarias implementadas con **JUnit 5 y Mockito**, alcanzando más del 80% de cobertura. Las pruebas siguen la estructura *Given-When-Then* validando la lógica de negocio sin depender de la base de datos real.

---

## Ejecución con Docker

Para construir y levantar la arquitectura completa con Docker Compose:

```bash
docker compose build --no-cache
docker compose up
docker compose down
```

Alternativa antigua:

```bash
docker-compose build --no-cache
docker-compose up
docker-compose down
```
