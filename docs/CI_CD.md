# Diseño del pipeline CI/CD

## Objetivo

Automatizar la integración y entrega del sistema TorneoTCG con controles verificables antes de ejecutar el despliegue simulado.

## Flujo

```text
Push / Pull Request
        |
        v
JUnit + Maven (5 módulos)
        |
        v
SonarQube Quality Gate
        |
        v
Docker Compose build
        |
        v
Trivy (HIGH / CRITICAL)
        |
        +---- PR o develop: validación y fin
        |
        +---- push main: despliegue simulado
                         |
                         v
                 Healthcheck Gateway
                         |
                         v
                 Logs y limpieza
```

## Políticas de calidad

- Todos los módulos compilan con Java 21, la misma versión declarada en Maven y utilizada por los Dockerfiles.
- La etapa de análisis depende de que todas las pruebas terminen correctamente.
- Un Quality Gate no aprobado impide construir y desplegar.
- Trivy devuelve código distinto de cero ante vulnerabilidades HIGH o CRITICAL no corregidas; eso bloquea el job posterior.
- El despliegue se ejecuta solo desde `main` y utiliza imágenes etiquetadas con el SHA completo del commit.

## Entorno cloud simulado

El runner Ubuntu de GitHub Actions funciona como entorno temporal. Docker Compose crea MySQL, Eureka, los tres microservicios de dominio y API Gateway en una red privada. El despliegue espera que los healthchecks estén correctos y valida el endpoint de salud del Gateway. Al terminar, se eliminan contenedores, red y volúmenes del entorno de prueba.

## Dependencias y seguridad

Dependabot mantiene actualizados los POM de los cinco módulos, las imágenes base de Docker y las acciones de GitHub. SonarQube analiza el código Java. Trivy escanea las imágenes construidas. Las contraseñas no se guardan en el repositorio: para la ejecución CI se genera una clave aleatoria de vida corta y para el entorno local se usa un archivo `.env` excluido por Git.

## Evidencias a revisar

- Pestaña **Actions**: ejecución de los jobs `Pruebas`, `Análisis estático SonarQube` y `Construcción, escaneo y despliegue Compose`.
- Artefactos `surefire-*`: resultados de JUnit/Surefire.
- Logs del job de despliegue: resultado de `docker compose ps` y healthcheck del Gateway.
- Pestaña **Insights / Dependency graph**: actualizaciones de Dependabot.
- SHA de la imagen: corresponde al commit desplegado.
