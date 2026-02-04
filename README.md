# Bug Tracker System
### A multi-module web application for tracking and managing defects.

---

## Quick Start

### 1. Database
Run the **MySQL** container:
```bash
docker-compose up -d
```

### 2. Build
Build the project using the Maven Wrapper:
```bash
./mvnw clean install
```

---

## Project Structure

* **bug-tracker-server**: Backend REST API and data persistence.
* **bug-tracker-client**: Frontend user interface and routing.
* **infra**: Infrastructure and database initialization scripts.

---

## Guidelines
* **Dependencies**: Versions are managed in the Parent POM.
* **Persistence**: DB data is stored in the `db_data` volume. Use `docker-compose down -v` to reset.