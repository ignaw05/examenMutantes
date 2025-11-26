# 🧬 Mutant Detector API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Coverage](https://img.shields.io/badge/Coverage-High-success.svg)]()

API REST desarrollada para detectar mutantes basándose en su secuencia de ADN. Este proyecto fue creado como parte de un examen técnico, cumpliendo con altos estándares de calidad, arquitectura y testing.

---

## 📋 Tabla de Contenidos

1. [Descripción del Problema](#-descripción-del-problema)
2. [Tecnologías Utilizadas](#-tecnologías-utilizadas)
3. [Arquitectura](#-arquitectura)
4. [Instalación y Ejecución](#-instalación-y-ejecución)
5. [API Reference (Swagger)](#-api-reference)
6. [Testing y Cobertura](#-testing-y-cobertura)
7. [Optimizaciones](#-optimizaciones)

---

## 🧩 Descripción del Problema

Magneto quiere reclutar mutantes para su ejército. Para ello, ha decidido crear un programa que detecte si un humano es mutante basándose en su secuencia de ADN.

Se recibirá como parámetro un array de Strings que representan cada fila de una tabla de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada.

**Un humano es mutante si se encuentra más de una secuencia de cuatro letras iguales**, de forma oblicua, horizontal o vertical.

---

## 🛠 Tecnologías Utilizadas

- **Java 21**: Lenguaje de programación moderno y robusto.
- **Spring Boot 3.2.0**: Framework para el desarrollo de la API REST.
- **H2 Database**: Base de datos en memoria para persistencia rápida y pruebas.
- **Gradle**: Gestor de dependencias y construcción.
- **Lombok**: Librería para reducir código boilerplate.
- **JUnit 5 & Mockito**: Frameworks para testing unitario y de integración.
- **SpringDoc OpenAPI (Swagger)**: Documentación automática de la API.

---

## 🏗 Arquitectura

El proyecto sigue una arquitectura en capas limpia y escalable:

```
src/main/java/com/mutantes/mutantes/
├── controller/          # Controladores REST (Endpoints)
│   ├── MutantController.java
│   └── GlobalExceptionHandler.java
├── dto/                 # Data Transfer Objects
│   ├── DnaRequest.java
│   ├── StatsResponse.java
│   └── ErrorResponse.java
├── service/             # Lógica de Negocio
│   ├── MutantService.java
│   ├── MutantDetector.java
│   └── StatsService.java
├── repository/          # Acceso a Datos (JPA)
│   └── DnaRecordRepository.java
├── entity/              # Entidades de Base de Datos
│   └── DnaRecord.java
├── config/              # Configuración (Swagger, etc.)
│   └── SwaggerConfig.java
├── validation/          # Validaciones Personalizadas
│   ├── ValidDnaSequence.java
│   └── ValidDnaSequenceValidator.java
└── exception/           # Excepciones Personalizadas
    └── DnaHashCalculationException.java
```

---

## 🚀 Instalación y Ejecución

### Prerrequisitos
- Java JDK 21 instalado.
- Git.

### Pasos

1.  **Clonar el repositorio:**
    ```bash
    git clone <url-del-repo>
    cd mutantes
    ```

2.  **Compilar el proyecto:**
    ```bash
    ./gradlew clean build
    ```

3.  **Ejecutar la aplicación:**
    ```bash
    ./gradlew bootRun
    ```

La aplicación iniciará en el puerto **8080**.

---

## 🌐 API Reference

Una vez iniciada la aplicación, puedes acceder a la documentación interactiva (Swagger UI) en:

👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

### Endpoints Principales

#### 1. Detectar Mutante
- **URL**: `POST /mutant`
- **Descripción**: Envía una secuencia de ADN para verificar si es mutante.
- **Body**:
  ```json
  {
    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  }
  ```
- **Respuestas**:
  - `200 OK`: Es Mutante.
  - `403 Forbidden`: Es Humano.
  - `400 Bad Request`: ADN inválido (caracteres erróneos, matriz no cuadrada, null).

#### 2. Obtener Estadísticas
- **URL**: `GET /stats`
- **Descripción**: Devuelve estadísticas de las verificaciones realizadas.
- **Respuesta**:
  ```json
  {
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
  }
  ```

---

## 🧪 Testing y Cobertura

El proyecto cuenta con una suite de tests exhaustiva que cubre:
- **Tests Unitarios**: `MutantDetectorTest`, `MutantServiceTest`, `StatsServiceTest`.
- **Tests de Integración**: `MutantControllerTest`.

### Ejecutar Tests
```bash
./gradlew test
```

### Reporte de Cobertura (JaCoCo)
Para generar el reporte de cobertura:
```bash
./gradlew jacocoTestReport
```
El reporte estará disponible en `build/reports/jacoco/test/html/index.html`.

---

## ⚡ Optimizaciones

El algoritmo de detección (`MutantDetector`) ha sido altamente optimizado:

1.  **Early Termination**: El análisis se detiene inmediatamente al encontrar más de una secuencia, evitando recorridos innecesarios.
2.  **Complejidad Temporal**: O(N) en el mejor caso (mutantes obvios) y O(N²) en el peor caso.
3.  **Eficiencia de Memoria**: Uso de `char[][]` para acceso rápido y bajo consumo de memoria.
4.  **Deduplicación**: Se utiliza un hash **SHA-256** del ADN para evitar guardar registros duplicados en la base de datos, optimizando el almacenamiento y las búsquedas.
5.  **Validaciones Robustas**: Validación temprana de inputs (Null, Empty, NxN, Caracteres válidos) usando Bean Validation y validadores personalizados.

---

**Desarrollado por Ignacio Wuilloud**
