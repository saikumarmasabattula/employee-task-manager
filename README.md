# Employee Task Manager REST API

A secure and production-ready Task Management REST API built using Spring Boot.

## Features

- User Registration & Login
- JWT Authentication
- Role-Based Authorization (USER / ADMIN)
- CRUD Operations for Tasks
- Pagination & Sorting
- DTO Pattern
- Global Exception Handling
- Bean Validation
- Swagger/OpenAPI Documentation
- Spring Boot Actuator
- Unit Testing with JUnit & Mockito
- MySQL Database Integration

---

## Tech Stack

- Java 25
- Spring Boot 3
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- Hibernate
- MySQL
- Maven
- Swagger/OpenAPI
- JUnit 5
- Mockito

---

## Project Structure

```
src
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── enums
 ├── exception
 ├── mapper
 ├── repository
 ├── security
 ├── service
```

---

## Authentication

The API uses JWT Authentication.

### Register

POST

```
/auth/register
```

### Login

POST

```
/auth/login
```

Returns

```
JWT Token
```

Use the token in Swagger:

```
Authorize

Bearer <your_token>
```

---

## Task APIs

- Create Task
- Update Task
- Delete Task
- Get Task By ID
- Get All Tasks
- Pagination
- Sorting

---

## Admin APIs

```
GET /admin/dashboard
```

Accessible only by ADMIN users.

---

## API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## Monitoring

Spring Boot Actuator

```
http://localhost:8080/actuator
```

Health Endpoint

```
/actuator/health
```

Info Endpoint

```
/actuator/info
```

---

## Database

MySQL

Configure credentials inside

```
application.properties
```

---

## Build

```bash
./mvnw clean install
```

Run

```bash
./mvnw spring-boot:run
```

---

## Testing

Run all tests

```bash
./mvnw test
```

---

## Author

Sai Kumar

Spring Boot Backend Project