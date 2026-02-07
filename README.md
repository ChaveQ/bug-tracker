# Bug Tracker System
### A multi-module web application for tracking and managing defects.

---
### Key Features
* **CRUD Operations:** Full lifecycle management for Bugs
* **Filtering:** Filtering by **Severity**
* **Error Handling:** Standardized error responses (400, 404, 500)
---
### Architecture & Environment
Project Structure
* **bug-tracker-server**: REST API.
* **bug-tracker-client**: Frontend.
* **infra**: Infrastructure and database initialization scripts.

Infrastructure
* **Dev:** `docker-compose.yaml` (MySQL pre-loaded with schema and demo data, Client and Server containers).
* **Test:** `docker-compose-test.yaml` (Isolated DB for integration tests).
  * **Database Connection:** If test fails on the first run, ensure the **test_db** container is fully initialized.
  * Run: `docker-compose -f bug-tracker-server/src/test/resources/docker-compose-test.yml up -d test_db`
  * Remove: `docker-compose -f bug-tracker-server/src/test/resources/docker-compose-test.yml down -v test_db`
  * Logs: `docker-compose -f bug-tracker-server/src/test/resources/docker-compose-test.yml logs`
---
## Quick Start
Run commands from the project root.
### 1. Database
Run the **MySQL** container:
```bash
# Start the database container in detached mode
docker-compose up -d db
# Follow logs to verify the database is ready for connections
docker-compose logs -f db
# Full reset: Remove volumes and restart to force re-running initialization scripts
docker-compose down -v db && docker-compose up -d db
```

### 2. Build
Build the project using the Maven Wrapper:
```bash
./mvnw clean install
```

### 3. Local Run

**Frontend:** [http://localhost:8081](http://localhost:8081)
```bash
./mvnw -pl bug-tracker-client jetty:run
```
**Backend API:** [http://localhost:8080/api/bugs](http://localhost:8080/api/bugs)
```bash
./mvnw -pl bug-tracker-server jetty:run
```

### 4. Docker Run
Run the entire system (Frontend, Backend, and DB) in isolated containers:
```bash
# Build WAR artifacts
./mvnw clean package -DskipTests
# Run containers
docker-compose up -d client-ui
docker-compose up -d server-api
```
**Frontend UI:** [http://localhost:8085](http://localhost:8085)
**Backend API:** [http://localhost:8084/api/bugs](http://localhost:8084/api/bugs)

### 5. Stop and Remove
Stop all services and remove volumes
```bash
docker-compose down -v
```
