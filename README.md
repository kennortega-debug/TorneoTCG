# TorneoTCG — Microservicio
Se uso google gemini para mejorar la redaccion y crear el diagrama de flujo.

Repositorio base del microservicio **TorneoTCG**, preparado como fundamento para el pipeline DevOps.

## Tabla de contenidos

1. [Estrategia de ramificación](#1-estrategia-de-ramificación)
2. [Flujo de trabajo DevOps](#2-flujo-de-trabajo-devops)
3. [Convenciones del repositorio](#3-convenciones-del-repositorio)
4. [Simulación de trabajo colaborativo](#4-simulación-de-trabajo-colaborativo)
5. [Integración continua (GitHub Actions)](#5-integración-continua-github-actions)

---

## 1. Estrategia de ramificación

### 1.1 Modelo elegido: GitFlow

Se adopta **GitFlow** en lugar de trunk-based development:

| Criterio | Por qué favorece a GitFlow en este contexto |
|---|---|
| Equipo pequeño  | GitFlow da roles y pasos explícitos (feature → develop → release → main), reduciendo ambigüedad mientras se aprende control de versiones. |
| Entregas por evaluación / hitos | Ciclos claros de trabajo (features y hotfixes) que se pueden auditar por separado; GitFlow separa cada cambio en su propia rama con un historial trazable. |
| Necesidad de estabilidad en `main` | `main` debe representar siempre una versión desplegable/estable simulando producción; GitFlow protege eso al no permitir commits directos, solo vía PR. |
| Corrección urgente sin frenar desarrollo | El flujo `hotfix/*` permite reparar `main` sin interrumpir el trabajo en curso sobre `develop`. |
| Trazabilidad para evaluación docente | Cada rama e historial de PR queda como evidencia verificable. |


### 1.2 Ramas del repositorio

| Rama | Rol | Se origina de | Se fusiona en |
|---|---|---|---|
| `main` | Código estable, listo para "producción" simulada. Solo recibe merges vía PR revisado. | — | — |
| `develop` | Integración de features en curso. Es la base de trabajo diaria del equipo. | `main` (una vez) | `main` (vía release/PR) |
| `feature/<nombre>` | Desarrollo de una funcionalidad puntual. | `develop` | `develop` |
| `hotfix/<nombre>` | Corrección urgente sobre producción. | `main` | `main` **y** `develop` |

### 1.3 Convención de naming de ramas

- `feature/<verbo-en-infinitivo-descripcion>` → ej. `feature/agregar-registro-torneo`, `feature/agregar-endpoint-partidas`
- `hotfix/<descripcion-del-bug>` → ej. `hotfix/corregir-validacion-puntaje`
- Minúsculas, palabras separadas por guion medio, sin espacios ni tildes.

---

## 2. Flujo de trabajo DevOps

Flujo que articula repositorio + automatización + colaboración:

```
 Dev A                Dev B
   │                    │
   ├─ feature/x         ├─ feature/y
   │     │              │     │
   │     ▼              │     ▼
   │  commits            commits
   │     │              │     │
   │     ▼              │     ▼
   │   Pull Request ───► develop ◄─── Pull Request
   │                       │
   │            [GitHub Actions: build + test]
   │                       │
   │                 Pull Request
   │                       │
   │                       ▼
   │                     main
   │                       │
   │            [GitHub Actions: build + test]
   │                       │
   └────── hotfix/z ───────┘ (desde main, PR a main y luego a develop)
```

**Etapas del pipeline:**

1. **Código** — el/la desarrollador/a trabaja en `feature/*` o `hotfix/*` localmente.
2. **Repositorio (GitHub)** — se sube la rama y se abre un **Pull Request**, que es el punto de control de calidad y colaboración (revisión de código, comentarios, aprobación).
3. **Automatización (GitHub Actions)** — cada `push` a `develop` y cada PR hacia `main` dispara el workflow de CI: build + tests. Si falla, el PR queda bloqueado.
4. **Colaboración** — revisiones cruzadas entre integrantes de la pareja antes de aprobar el merge.
5. **Entorno cloud simulado** — GitHub Actions actúa como el entorno de ejecución remoto (runner en la nube) donde se valida el código fuera de la máquina local, simulando un pipeline CI/CD real.

---

## 3. Convenciones del repositorio

### 3.1 Mensajes de commit

Se usa **Conventional Commits**: `<tipo>(<alcance opcional>): <descripción corta>`

| Tipo | Uso |
|---|---|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de bug |
| `docs` | Cambios de documentación |
| `chore` | Tareas de mantenimiento (config, dependencias) |
| `test` | Agregar o modificar pruebas |
| `refactor` | Cambio de código sin alterar comportamiento |

Ejemplos:
```
feat(torneos): agregar endpoint de creación de torneo
fix(partidas): corregir cálculo de puntaje final
docs(readme): documentar convenciones de ramas
```

### 3.2 Flujo de merge

1. Nunca se commitea directo a `main` ni `develop`.
2. Toda incorporación de código pasa por **Pull Request**.
3. El PR debe pasar el check de GitHub Actions (build + tests) antes de poder fusionarse.
4. Se usa **Squash and merge** para features (historial limpio en `develop`), y **merge commit** para integrar `release`/`hotfix` a `main` (preserva trazabilidad de la corrección).
5. Todo `hotfix` fusionado a `main` se replica inmediatamente a `develop` para no perder la corrección.

### 3.3 Estrategia de revisión (code review)

- Mínimo **1 aprobación** de la otra persona de la pareja antes del merge.
- El revisor valida: que el build/CI pase, que el nombre de rama y commits sigan la convención, y que el cambio resuelva lo que dice el PR.
- Comentarios de revisión se resuelven (`resolve conversation`) antes de aprobar.
- Se prohíbe auto-mergear el propio PR sin revisión de la contraparte.

### 3.4 Estructura de carpetas (microservicio Java/Spring Boot)

```
TorneoTCG/
├── .github/workflows/ci.yml
├── src/
│   ├── main/java/...
│   └── test/java/...
├── pom.xml (o build.gradle)
├── README.md
└── docs/
    └── BUENAS_PRACTICAS.md
```

---

## 4. Simulación de trabajo colaborativo

Evidencia:

- ✅ 2 Pull Requests tipo **feature** → `feature/<nombre>` hacia `develop`
- ✅ 1 Pull Request tipo **hotfix** → `hotfix/<nombre>` hacia `main` (con réplica a `develop`)

Cada PR debe documentar en su descripción: qué cambia, por qué, y quién revisó.

---

## 5. Integración continua (GitHub Actions)

Ver `.github/workflows/ci.yml`. Se ejecuta automáticamente en:
- `push` a `develop`
- `pull_request` con destino `main`

El workflow compila el proyecto con Maven y ejecuta las pruebas unitarias, actuando como gate de calidad antes de fusionar código.

