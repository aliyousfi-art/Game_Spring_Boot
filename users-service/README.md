# Users Service

A standalone Spring Boot microservice for user management, exposing a REST API to create, retrieve, delete, and validate users.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- H2 (development) / MySQL (production)
- Maven

## Project Structure

```
users-service/
├── controller/     # REST endpoints
├── service/        # Business logic
├── dao/            # Data access abstraction (interface + JPA impl)
├── repository/     # Spring Data JPA repository
├── entity/         # JPA entity (User → table app_user)
├── dto/            # Request/response DTOs
└── resources/
    └── application.properties
```

## API Endpoints

| Method   | Endpoint           | Description                          | Status  |
|----------|--------------------|--------------------------------------|---------|
| `POST`   | `/users`           | Create a new user                    | 201     |
| `GET`    | `/users/{id}`      | Get a user by ID                     | 200/404 |
| `DELETE` | `/users/{id}`      | Delete a user by ID                  | 204     |
| `GET`    | `/users/{id}/valid`| Check if a user exists               | 200     |

### Create User

**Request**
```json
POST /users
Content-Type: application/json

{
  "name": "Alice"
}
```

**Response**
```json
HTTP 201 Created

{
  "id": 1,
  "name": "Alice"
}
```

### Get User

```
GET /users/1
```

Returns `404 Not Found` if the user does not exist.

### Delete User

```
DELETE /users/1
```

Returns `204 No Content`.

### Validate User

```
GET /users/1/valid
```

Returns `true` or `false`.

## Configuration

The service runs on port **8081** by default.

```properties
# application.properties
server.port=8081

spring.datasource.url=jdbc:h2:file:./data/users;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

The H2 console is available at `http://localhost:8081/h2-console` when running locally.

## Running Locally

```bash
cd users-service
./mvnw spring-boot:run
```

Or build and run the jar:

```bash
./mvnw clean package
java -jar target/users-service-0.0.1-SNAPSHOT.jar
```

## Running with MySQL

Add a `application-mysql.properties` file (or set environment variables) with your MySQL connection details, then activate the profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Running Tests

```bash
./mvnw test
```
