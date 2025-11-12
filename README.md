Gamified Trading System 

### Overview

This project simulates a Gamified Trading System for an investment platform — designed to evaluate the candidate’s ability to architect, implement, and document a backend system in Java (Spring Boot) while demonstrating clean code, OOP principles, and problem-solving skills.

The solution allows users to:

- Create portfolios and add assets

- Simulate trading of assets (buy/sell)

- Earn gems based on trading activities

- Compete in a dynamic ranking system that tracks users with the highest gem counts


### Core Features & Requirements
#### 1. User Management

Create and manage users with attributes:

userId (UUID)

username

gemCount (total gems earned)

rank (position in leaderboard)

Uses an in-memory data structure to store user data.

Validation: Enforces unique usernames and non-empty values.

#### 2. Portfolio Management

Each user owns a portfolio containing multiple assets.
Features include:

Add or remove assets (assetId, name, quantity, price)

Dynamically compute total portfolio value

Ensure consistent state when modifying asset quantities

Use thread-safe structures (CopyOnWriteArrayList) for concurrent updates

#### 3. Trading Functionality

Implements buy and sell operations:

Buy Assets → increases asset quantity

Sell Assets → reduces asset quantity

Validates transactions (e.g., prevents overselling)

Updates portfolio value in real time

#### 4. Gamification System (Gems)

Rewards users based on trading activities:

+1 gem for each trade (buy or sell)

Bonus gems for hitting trade milestones:

5 trades → +5 gems

10 trades → +10 gems

Maintains cumulative gem count per user

Optional Streak Feature:

Consecutive trades within 24 hours add streak bonuses (+3 gems for 3-day streak)

#### 5. Ranking System (Leaderboard)

Maintains a live leaderboard of top users by gem count:

Automatically updates after every trade or gem change

Handles ties (equal gem counts share rank)

Provides endpoint to retrieve top N ranked users

#### 6. Ranking Logic

Users are sorted by gemCount (descending)

Rank recalculates dynamically on updates

Ties share the same rank number


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

-   The app starts on **http://localhost:8081**
-   Check the console for simulated asset price updates every 10 seconds
    ( messages).

------------------------------------------------------------------------
## Testing

Unit tests implemented with JUnit 5

Covers:

User creation and validation

Trading (buy/sell logic)

Gem award and milestone calculation

Ranking updates and leaderboard ordering

Includes mock tests for edge cases (e.g., duplicate usernames, overselling)

###  **Run All Tests**

``` bash
./mvnw test
```


-   Runs service-layer tests (e.g., `TradingServiceTest`,
    `UserServiceTest`, `LeaderboardServiceTest`).
-   Uses **JUnit 5**.
-   No external DB required; tests use **in-memory repositories**.


### Swagger Documentation of APIs
Swagger UI is available at:
http://localhost:8081/api/v1/swagger-ui/index.html



``` bash
# Create a user
curl -X POST http://localhost:8080/api/v1/users -H "Content-Type: application/json" -d '{"username":"prince"}'

# Buy an asset
curl -X POST http://localhost:8080/api/v1/trades/buy -H "Content-Type: application/json" -d '{"userId":"<123343fffggfw44>", "assetId":"BTC", "quantity":5}'

# Get leaderboard
curl http://localhost:8080/api/v1/leaderboard/top/3
