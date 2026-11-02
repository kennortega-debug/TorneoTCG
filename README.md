# TorneoTCG — Pipeline CI/CD con GitHub Actions

Microservicios Java 21 / Spring Boot para gestión de torneos TCG. Este repositorio implementa un flujo CI/CD automatizado con pruebas, análisis estático, escaneo de imágenes Docker y despliegue simulado mediante Docker Compose.

## Requisitos de la Evaluación Parcial 2

| Indicador | Implementación |
|---|---|
| IE1 — Contenedores | Dockerfile multi-stage por microservicio, imágenes Java 21 y construcción en GitHub Actions. |
| IE2 — Pruebas automatizadas | JUnit 5 / Spring Boot Test / Mockito ejecutados con Maven en cada módulo. |
| IE3 — Seguridad y escalabilidad | Dependabot para Maven, Docker y GitHub Actions; SonarQube Quality Gate; Trivy para imágenes; límites de recursos y contenedores no privilegiados. |
| IE4 — Despliegue y trazabilidad | Docker Compose en runner de GitHub Actions después de superar pruebas y análisis; etiqueta de imagen con SHA del commit. |
| IE5 — Orquestación | Docker Compose con red dedicada, dependencias por estado saludable, healthchecks, reinicios y límites de recursos. |

## Arquitectura de contenedores

- `api-gateway`: entrada HTTP, puerto 8080.
- `eureka-server`: descubrimiento de servicios, puerto 8761.
- `jugador-service`: puerto 8082.
- `recinto-service`: puerto 8081.
- `torneos-service`: puerto 8083.
- `mysql-db`: persistencia MySQL 8.

Los microservicios se conectan a MySQL y Eureka usando los nombres DNS de los servicios de Docker Compose, no `localhost`. Los servicios Spring Boot incluyen Spring Boot Actuator y publican `/actuator/health` para healthchecks.

## Ejecución local

1. Instalar Docker Desktop o Docker Engine con Docker Compose v2.
2. Crear el archivo de configuración local:

   ```bash
   cp .env.example .env
   ```

   En Windows PowerShell:

   ```powershell
   Copy-Item .env.example .env
   ```

3. Editar `.env` y reemplazar `MYSQL_ROOT_PASSWORD` por una contraseña segura.
4. Construir y levantar todos los servicios:

   ```bash
   docker compose up -d --build --wait
   ```

5. Revisar estado y salud:

   ```bash
   docker compose ps
   curl http://localhost:8080/actuator/health
   ```

6. Detener el entorno:

   ```bash
   docker compose down
   ```

Para borrar también los datos locales de MySQL: `docker compose down --volumes`.

> No subir el archivo `.env` al repositorio. Las credenciales se inyectan mediante variables de entorno y nunca deben escribirse en código o YAML versionado.

## Pipeline de GitHub Actions

Archivo: [`.github/workflows/ci.yml`](.github/workflows/ci.yml)

### Eventos

- `push` a `develop` o `main`.
- `pull_request` con destino a `main`.
- Ejecución manual con `workflow_dispatch`.

### Etapas y gates

### Comportamiento del análisis SonarQube

El job `security` tiene una condición explícita: solo se ejecuta cuando el evento es `push` y la rama de destino es `main` (`github.event_name == 'push' && github.ref == 'refs/heads/main'`). Por eso, en ejecuciones de Pull Request hacia `main` o en pushes a `develop`, GitHub Actions muestra SonarQube como **Skipped**. Es un comportamiento esperado por diseño, no un error ni un Quality Gate fallido.

En las Pull Requests se ejecutan las pruebas Maven y los controles de construcción/escaneo configurados; el análisis SonarQube Community y su Quality Gate se ejecutan al integrar el cambio mediante un push a `main`. SonarQube Community no proporciona análisis nativo de Pull Requests; para analizar cada PR se requiere una edición que soporte esa funcionalidad o un servicio como SonarCloud.

1. **Tests:** matriz para los cinco módulos, configura JDK 21 y ejecuta `mvn clean verify`. Publica reportes Surefire aunque falle una prueba. Los tests de carga de contexto usan H2 en memoria y desactivan Flyway/Eureka para no depender de servicios externos. Se agregaron pruebas unitarias Mockito para conversión y persistencia de jugadores, validación de recintos y eliminación/búsqueda de torneos.
2. **Seguridad / calidad:** en push a main inicia SonarQube Community y PostgreSQL como servicios efímeros del runner, compila los módulos, analiza el código y espera el Quality Gate. Un Quality Gate fallido detiene las etapas posteriores. En Pull Requests se ejecutan pruebas y escaneo Trivy; el análisis SonarQube se exige antes del despliegue en main.
3. **Build de contenedores:** Docker Compose construye las cinco imágenes desde sus Dockerfiles.
4. **Escaneo de imágenes:** Trivy analiza vulnerabilidades de sistema operativo y dependencias. Vulnerabilidades HIGH o CRITICAL sin corregir bloquean el pipeline.
5. **Despliegue simulado:** solo al recibir un push a `main`, levanta los seis contenedores con `docker compose up --wait`, comprueba la salud del API Gateway y guarda logs si ocurre un error.
6. **Limpieza:** el runner elimina contenedores, red y volumen temporal al finalizar el job.

El entorno de despliegue es simulado y efímero: corre dentro del runner cloud de GitHub Actions y se destruye al finalizar el job. No representa un servidor de producción permanente.

## Seguridad y gobernanza

- **Dependabot:** `.github/dependabot.yml` revisa semanalmente dependencias Maven de cada módulo, imágenes base Docker y acciones de GitHub.
- **SonarQube:** analiza el código Java y aplica el Quality Gate antes de construir/desplegar.
- **Trivy:** analiza cada imagen Docker y bloquea hallazgos HIGH/CRITICAL no corregidos.
- **Secretos:** `MYSQL_ROOT_PASSWORD` se genera de forma aleatoria dentro del runner para el despliegue temporal. En local se configura en `.env`.
- **Contenedores:** las imágenes de las aplicaciones ejecutan Java como usuario no-root, incluyen healthchecks y aplican `no-new-privileges`.
- **Escalabilidad:** Docker Compose define límites de CPU y memoria para evitar consumo ilimitado de recursos.

## Trazabilidad

Cada imagen se etiqueta con `${{ github.sha }}`, por lo que se puede asociar el artefacto desplegado con el commit exacto que lo generó. GitHub Actions conserva el resultado por job, los reportes de pruebas y los logs de ejecución. El despliegue ocurre únicamente después de que las pruebas, el análisis SonarQube, la construcción y el escaneo de imágenes terminan correctamente.

## Estructura relevante

```text
.github/
  dependabot.yml
  workflows/
    ci.yml
api-gateway/
  Dockerfile
eureka-server/
  Dockerfile
jugador-service/
  Dockerfile
recinto-service/
  Dockerfile
torneos-service/
  Dockerfile
docker-compose.yml
.env.example
sonar-project.properties
README.md
docs/
  BUENAS_PRACTICAS.md
  CI_CD.md
```

## Estrategia de ramas

Se mantiene GitFlow: `main` representa la versión estable; `develop` integra trabajo; `feature/*` desarrolla funcionalidades y `hotfix/*` corrige errores urgentes. Los cambios se integran mediante Pull Requests y validaciones automáticas.

## Comandos útiles

```bash
mvn -f recinto-service/pom.xml clean verify
docker compose config --quiet
docker compose build
docker compose logs -f
docker compose ps
```
