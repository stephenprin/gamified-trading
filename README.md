Gamified Trading System 

###  Overview

A Spring Boot--based backend system that simulates a gamified trading
experience for users.\
Players can create portfolios, trade assets, earn gems based on trading
activity, and compete on a dynamic leaderboard.\
The system also features real-time asset price simulation**


------------------------------------------------------------------------




##  Tech Stack

-   **Language:** Java 17+\
-   **Framework:** Spring Boot 3.4+\
-   **Dependencies:** Spring Web, Validation, Lombok\
-   **Database:** In-memory (ConcurrentHashMap)\
-   **Scheduler:** `@EnableScheduling` with Spring Task Scheduler\
-   **Testing:** JUnit 5, Mockito


------------------------------------------------------------------------

## Running and Testing

###   **Run the Application**

``` bash
./mvnw spring-boot:run
```

-   The app starts on **http://localhost:8080**
-   Check the console for simulated asset price updates every 10 seconds
    ( messages).

------------------------------------------------------------------------

###  **Run All Tests**

``` bash
./mvnw test
```

-   Runs all unit and service-layer tests (e.g., `TradingServiceTest`,
    `UserServiceTest`, `LeaderboardServiceTest`).
-   Uses **JUnit 5**.
-   No external DB required; tests use **in-memory repositories**.


### Swagger Documentation of APIs

http://localhost:8081/api/v1/swagger-ui/index.html



``` bash
# Create a user
curl -X POST http://localhost:8080/api/v1/users -H "Content-Type: application/json" -d '{"username":"prince"}'

# Buy an asset
curl -X POST http://localhost:8080/api/v1/trades/buy -H "Content-Type: application/json" -d '{"userId":"<123343fffggfw44>", "assetId":"BTC", "quantity":5}'

# Get leaderboard
curl http://localhost:8080/api/v1/leaderboard/top/3
