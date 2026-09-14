# Guía de buenas prácticas — TorneoTCG

Guía de referencia para el equipo al trabajar en este repositorio dentro de un flujo DevOps colaborativo.

## 1. Naming de ramas

- `feature/<accion-descriptiva>` — ej. `feature/agregar-ranking-jugadores`
- `hotfix/<descripcion-bug>` — ej. `hotfix/corregir-timeout-partida`
- `release/<version>` (opcional, si se agrega ciclo de versionado) — ej. `release/1.1.0`
- Todo en minúsculas, con guiones medios, sin tildes ni espacios.
- El nombre debe describir el **qué**, no el **quién** (evitar `feature/juan-cambios`).

## 2. Mensajes de commit

- Formato: `tipo(alcance): descripción en presente e imperativo`
- Máximo ~72 caracteres en la primera línea; detalle adicional en el cuerpo del commit si es necesario.
- Un commit = un cambio lógico. Evitar commits gigantes que mezclen features distintas.
- Tipos permitidos: `feat`, `fix`, `docs`, `chore`, `test`, `refactor`, `style`, `perf`.

## 3. Estructura de carpetas

- Separar código fuente (`src/main`) de pruebas (`src/test`).
- Documentación técnica bajo `docs/`.
- Configuración de CI/CD bajo `.github/workflows/`.
- No commitear artefactos de build (`target/`, `.class`, `.jar`) — deben estar en `.gitignore`.

## 4. Control de versiones

- `main` siempre debe estar en estado desplegable. Nunca commits directos.
- `develop` es la rama de integración; debe compilar y pasar tests en todo momento.
- Sincronizar la rama local (`git pull`) antes de comenzar trabajo nuevo, para evitar conflictos grandes.
- Resolver conflictos localmente antes de abrir o actualizar un PR, nunca en la interfaz web para cambios complejos.
- Eliminar ramas `feature/*`/`hotfix/*` una vez fusionadas, para mantener el repositorio limpio.

## 5. Pull Requests y revisión

- Todo cambio entra por PR, nunca por push directo a `main`/`develop`.
- Descripción del PR debe incluir: objetivo del cambio, cómo probarlo, e issue relacionado (si aplica).
- Mínimo 1 revisor antes de aprobar.
- El PR debe pasar el pipeline de GitHub Actions (build + tests) antes de fusionarse.
- Preferir PRs pequeños y frecuentes sobre PRs grandes difíciles de revisar.

## 6. Buenas prácticas generales

- Documentar cualquier decisión de arquitectura relevante en `docs/`.
- Mantener el README actualizado con cada cambio significativo de flujo o estructura.
- Usar `.gitignore` apropiado para el stack (Java/Maven: `target/`, `.idea/`, `*.class`).
- No incluir credenciales ni secretos en el repositorio; usar `GitHub Secrets` para el pipeline.

