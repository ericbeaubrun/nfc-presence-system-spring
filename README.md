# Attendance Management and Verification System

## System Architecture

The application consists of three main components:

1. **Backend Server**: Built with Java 21 and Spring Boot 4.1.0 using a simplified Hexagonal Architecture. It encapsulates core business logic such as timestamp processing, room schedule cross-referencing, and double-check-in prevention. Administrative endpoints are secured via Spring Security using HTTP Basic Authentication.
2. **Web Administration Interface**: An asynchronous dashboard interacting with the secure REST API. It implements a complete CRUD system allowing authorized administrators to create, read, update, and delete attendance records manually.
3. **Hardware Client**: A lightweight Python script running on a terminal connected to an ACR122U hardware reader. It extracts card UIDs and transmits structured JSON payloads to the central backend.

---

## Technical Stack and Dependencies

### Backend Dependencies (Gradle)
* **Core & MVC**: `spring-boot-starter-webmvc` (version 4.1.0)
* **Security**: `spring-boot-starter-security`
* **Persistence & Driver**: `spring-boot-starter-data-jpa`, `postgresql` (runtimeOnly)
* **Database Migrations**: `spring-boot-starter-flyway`, `flyway-database-postgresql`
* **Data Validation**: `spring-boot-starter-validation` (Jakarta constraints)
* **Boilerplate Reduction**: `lombok` (compileOnly / annotationProcessor)

### Hardware Client Dependencies
* **Runtime**: Python 3.x
* **Libraries**: `pyscard` (Smartcard interface), `requests` (HTTP communication)

---

## Testing Suite

All validation and integration utilities are organized within the `src/tests/` directory:

* **`admin_test.html`**: A functional browser test suite performing asynchronous AJAX requests (`Fetch API`) to validate Cross-Origin Resource Sharing (CORS) configurations and verify CRUD mutations against protected endpoints.
* **`nfc_reader_client_test.py`**: A validation script that tests hardware-to-server data transmission by capturing physical smartcard data via the ACR122U reader, formatting the UID payload, and posting it to the backend.
* **`postman_test.json`**: A Postman collection mapped out for API integration testing. It defines standard request structures and asserts specific HTTP status codes (e.g., 200 OK for valid check-ins, 400 Bad Request for duplicate attempts, unrecognized badges, or schedule mismatches).

---
