# Square Games API

A Spring Boot REST API for managing board game sessions. Supports multiple game types through a plugin system, with internationalization, JPA persistence, and Spring profiles for H2/MySQL.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Data JPA + JDBC
- H2 (development) / MySQL (production)
- Maven

## Project Structure

```
src/
├── controller/
│   ├── GameCatalogController.java    # Game catalog endpoints
│   ├── GameSessionController.java    # Game session endpoints
│   └── HeartbeatController.java      # Health check
├── service/
│   ├── GameCatalog.java              # Catalog interface
│   ├── GameCatalogImpl.java          # Catalog implementation
│   └── GameSessionService.java       # Session business logic
├── dao/
│   ├── GameDao.java                  # DAO interface
│   ├── InMemoryGameDao.java
│   ├── JdbcGameDao.java
│   └── JpaGameDao.java
├── plugins/
│   ├── GamePlugin.java               # Plugin interface
│   ├── TicTacToePlugin.java
│   ├── ConnectFourPlugin.java
│   └── TaquinPlugin.java
├── entity/                           # JPA entities
├── dto/                              # Request/response DTOs
├── config/                           # i18n configuration
└── sensor/                           # Heartbeat sensor

users-service/                        # Standalone user management microservice
```

## Available Games

| Game        | Type          | Players | Board Size |
|-------------|---------------|---------|------------|
| Tic-Tac-Toe | `tictactoe`   | 2       | 3×3        |
| Connect Four| `connectfour` | 2       | 7          |
| Taquin      | `taquin`      | 1       | 4×4        |

## API Endpoints

### Heartbeat

| Method | Endpoint     | Description      |
|--------|--------------|------------------|
| `GET`  | `/heartbeat` | Returns a health check value |

### Game Catalog

| Method | Endpoint                  | Description                              |
|--------|---------------------------|------------------------------------------|
| `GET`  | `/games/catalog`          | List available game type IDs             |
| `GET`  | `/games/catalog/detailed` | List games with full info (i18n support) |

The `/games/catalog/detailed` endpoint supports the `Accept-Language` header for localized game names (e.g. `fr-FR`, `en-US`).

### Game Sessions

| Method   | Endpoint                                          | Description              | Status  |
|----------|---------------------------------------------------|--------------------------|---------|
| `POST`   | `/api/games/{gameId}/sessions`                    | Create a new session     | 201     |
| `GET`    | `/api/games/{gameId}/sessions`                    | List all sessions        | 200     |
| `GET`    | `/api/games/{gameId}/sessions/{sessionId}`        | Get session state        | 200/404 |
| `GET`    | `/api/games/{gameId}/sessions/{sessionId}/legal-moves` | Get legal moves    | 200/404 |
| `POST`   | `/api/games/{gameId}/sessions/{sessionId}/move`   | Play a move              | 200/400 |
| `DELETE` | `/api/games/{gameId}/sessions/{sessionId}`        | Delete a session         | 204     |

### Example: Create a Tic-Tac-Toe Session

```json
POST /api/games/tictactoe/sessions
Content-Type: application/json

{
  "playerCount": 2
}
```

### Example: Play a Move

```json
POST /api/games/tictactoe/sessions/{sessionId}/move
Content-Type: application/json

{
  "position": 4
}
```

## Configuration

The service runs on port **8080**. Active profiles: `jpa,h2` by default.

```properties
server.port=8080

game.tictactoe.default-player-count=2
game.tictactoe.default-board-size=3

game.connectfour.default-player-count=2
game.connectfour.default-board-size=7

game.taquin.default-player-count=1
game.taquin.default-board-size=4

spring.profiles.active=jpa,h2
```

The H2 console is available at `http://localhost:8080/h2-console` when running locally.

## Running Locally

```bash
./mvnw spring-boot:run
```

Or build and run the jar:

```bash
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Running with MySQL

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=jpa,mysql
```

## Running Tests

```bash
./mvnw test
```

## Related Services

- **[users-service](./users-service/README.md)** — Standalone microservice for user management (port 8081)
