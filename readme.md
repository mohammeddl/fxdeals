# FX Data Warehouse 📊

A Spring Boot REST application to import and query FX deals in a PostgreSQL database.  
This project uses a manually created `fx_deal` table and validates schema at startup.

![Project Status](https://img.shields.io/badge/Status-Production--Ready-green)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Coverage](https://img.shields.io/badge/Coverage-90%25-brightgreen)

---

## Features

1. **Data Ingestion**
   - Accepts FX deal details via structured JSON requests.
   - Validates and persists new deals, skipping duplicates.

2. **Validation Layer**
   - Enforces non-null UUID, 3-letter currency codes, valid timestamp, and positive amount.
   - Returns HTTP 400 with structured error response on validation failures.

3. **Duplicate Prevention**
   - Checks if a deal ID already exists; skips duplicates without rolling back prior inserts.

4. **Persistence Layer**
   - Stores FX deals in PostgreSQL.
   - Relies on a manually created `fx_deal` table matching the `FxDeal` entity.

5. **Error Handling**
   - Global exception handler returns `ErrorResponse` (timestamp, status, error, message, path).
   - Handles validation, parsing, constraint violations, and uncaught exceptions.

6. **Logging**
   - Uses SLF4J via Lombok’s `@Slf4j` for structured, informative logs.
   - Records inserts, skips, and errors.

7. **Testing**
   - Comprehensive unit tests (service + mapper) with Mockito.

8. **Deployment**
   - Dockerized setup for easy containerized deployment.
   - Includes Docker Compose configuration.

9. **Documentation**
   - Detailed documentation in Markdown (this README).
   - Covers setup, usage, and troubleshooting.

10. **Makefile Support**
    - Simplifies common tasks: build, test, run, and clean.
    - Streamlines development workflow.

---

## Technologies Used

- **Java 17**  
- **Spring Boot 3.4.2**  
- **PostgreSQL 16**  
- **Docker & Docker Compose**  
- **Maven**  
- **JUnit 5 / Mockito / AssertJ**  
- **MapStruct**  
- **Lombok**  
- **SLF4J**

---

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/mohammeddl/fxdeals.git
cd fxdeals
