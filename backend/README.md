# Java Spring Boot + SQLite + Oauth2

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

## Getting started
To run the spring boot API: \
`mvn clean install -DskipTests` \
`mvn spring-boot:run`

## API Endpoints
This section outlines the available RESTful API endpoints of the application.
Each endpoint's purpose, required request method, and expected inputs/outputs are described below.
All API endpoints listed below are accessible from the local development server, which runs on `localhost:8080` by default.
Ensure that your local development server is running before attempting to access these endpoints. 

### Base URL

- **Index**
  - **GET** `/`
    - Returns a welcome message.
    - **Responses:**
      - `200 OK` with string `Welcome!`

- **Get Status**
  - **GET** `/status`
    - Provides a quick health check of the server, indicating if the service is operational.
    - **Responses:**
      - `200 OK` with string `Service is up and running`

- **Secured**
  - **GET** `/secured`
    - An example secured endpoint, accessible only to authenticated users, demonstrating basic access control.
    - **Responses:**
      - `202 Accepted` with string `Hello [user name]!`, where [user name] is the name of the authenticated user.

### User Management

- **Read User**
  - **GET** `/users`
  - Returns a list of all users.
  - **Responses:**
    - `200 OK` with a list of `User` entities.

- **Read User**
  - **GET** `/{username}`
  - Retrieves a specific user by username.
  - **Path Variables:**
    - `username`: The username of the user to retrieve.
  - **Responses:**
    - `200 OK` with the `User` entity.
    - `404 Not Found` if the user does not exist.

- **Get User**
  - **GET** `/myProfile`
  - Retrieves the currently authenticated user's profile.
  - **Responses:**
    - `200 OK` with the authenticated `User` entity.

- **Get User Games**
  - **GET** `/myProfile/games`
  - Retrieves the games created by the currently authenticated user.
  - **Responses:**
    - `200 OK` with a list of `Game` entities created by the user.

- **Get User Game-lists**
  - **GET** `/myProfile/lists`
  - Retrieves the game lists created by the currently authenticated user.
  - **Responses:**
    - `200 OK` with a list of `GameList` entities created by the user.

- **Get User Reviews**
  - **GET** `/myProfile/reviews`
  - Retrieves the reviews written by the currently authenticated user.
  - **Responses:**
    - `200 OK` with a list of `Review` entities written by the user.

- **Delete User**
  - **DELETE** `/{username}`
  - Deletes a specific user by username. This endpoint should be secured to allow only authorized users (e.g., admins) to delete users.
  - **Path Variables:**
    - `username`: The username of the user to delete.
  - **Responses:**
    - `200 OK` with a confirmation message if deletion was successful.
    - `401 Unauthorized` if the current user does not have permission to delete the user.
    - `404 Not Found` if the user does not exist

### Game Management

- **List Games**
  - **GET** `/games`
    - Retrieves a list of all available games.
    - **Responses:**
      - `200 OK` with JSON array of `Game` entities.

- **Get Game**
  - **GET** `/games/{ID}`
    - Retrieves details of a specific game by its ID.
    - **Path Parameters:**
      - `ID` (required): The ID of the game to retrieve.
    - **Responses:**
      - `200 OK` with JSON object of the `Game` entity.
      - `404 Not Found` if the game is not found.

- **Create Game**
  - **POST** `/games/create`
    - Creates a new game with the provided details.
    - **Request Body:** JSON object containing game details.
    - **Responses:**
      - `201 Created` with JSON object of the created `Game` entity.

- **Update Game**
  - **PATCH** `/games/{ID}/update`
    - Updates an existing game with new details.
    - **Path Parameters:**
      - `ID` (required): The ID of the game to update.
    - **Request Body:** JSON object containing updated game details.
    - **Responses:**
      - `200 OK` with confirmation message.
      - `404 Not Found` if the game is not found.

- **Delete Game**
  - **DELETE** `/games/{ID}`
    - Deletes a game by its ID.
    - **Path Parameters:**
      - `ID` (required): The ID of the game to delete.
    - **Responses:**
      - `200 OK` with confirmation message.
      - `404 Not Found` if the game is not found.

- **Get Game Reviews**
  - **GET** `/games/{ID}/reviews`
    - Retrieves all reviews for a specific game.
    - **Path Parameters:**
      - `ID` (required): The ID of the game whose reviews are to be retrieved.
    - **Responses:**
      - `200 OK` with JSON array of `ReviewDTO` entities.

- **Get Game Categories**
  - **GET** `/games/{ID}/categories`
    - Retrieves all categories associated with a specific game.
    - **Path Parameters:**
      - `ID` (required): The ID of the game whose categories are to be retrieved.
    - **Responses:**
      - `200 OK` with JSON array of `Category` entities.

- **Add Game Categories**
  - **POST** `/games/{ID}/categories`
    - Associates new categories with a specific game.
    - **Path Parameters:**
      - `ID` (required): The ID of the game to add categories to.
    - **Request Body:** JSON array of category names.
    - **Responses:**
      - `201 Created` with confirmation message.

### GameList Management

- **List Game Lists**
  - **GET** `/lists`
    - Retrieves a list of all game lists.
    - **Responses:**
      - `200 OK` with JSON array of `GameList` entities.

- **Get Game List**
  - **GET** `/lists/{ID}`
    - Retrieves a specific game list by its ID.
    - **Path Parameters:**
      - `ID` (required): The ID of the game list to retrieve.
    - **Responses:**
      - `200 OK` with JSON object of the `GameList` entity.
      - `404 Not Found` if the game list is not found.

- **Create Game List**
  - **POST** `/lists/create`
    - Creates a new game list with the provided details.
    - **Request Body:** JSON object containing game list details.
    - **Responses:**
      - `201 Created` with JSON object of the created `GameList` entity.

- **Update Game List**
  - **PATCH** `/lists/{ID}`
    - Updates an existing game list with new details.
    - **Path Parameters:**
      - `ID` (required): The ID of the game list to update.
    - **Request Body:** JSON object containing updated game list details.
    - **Responses:**
      - `200 OK` with JSON object of the updated `GameList` entity.
      - `404 Not Found` if the game list is not found.

- **Delete Game List**
  - **DELETE** `/lists/{ID}`
    - Deletes a game list by its ID.
    - **Path Parameters:**
      - `ID` (required): The ID of the game list to delete.
    - **Responses:**
      - `200 OK` with confirmation message.
      - `404 Not Found` if the game list is not found.

- **Get List's Games**
  - **GET** `/lists/{ID}/games`
    - Retrieves all games included in a specific list.
    - **Path Parameters:**
      - `ID` (required): The ID of the game list.
    - **Responses:**
      - `200 OK` with JSON array of `Game` entities.
      - `404 Not Found` if the game list is not found.

- **Add Game to List**
  - **POST** `/lists/{ID}/{gameID}`
    - Adds a specific game to a game list.
    - **Path Parameters:**
      - `ID` (required): The ID of the game list.
      - `gameID` (required): The ID of the game to add.
    - **Responses:**
      - `200 OK` with confirmation message.
      - `404 Not Found` if the game or list is not found.

- **Remove Game from List**
  - **DELETE** `/lists/{ID}/{gameID}`
    - Removes a specific game from a game list.
    - **Path Parameters:**
      - `ID` (required): The ID of the game list.
      - `gameID` (required): The ID of the game to remove.
    - **Responses:**
      - `200 OK` with confirmation message.
      - `404 Not Found` if the game or list is not found.

### Review Management

- **Create Review**
  - **POST** `/games/{ID}/reviews`
    - Adds a new review for a specific game.
    - **Path Parameters:**
      - `ID` (required): The ID of the game to review.
    - **Request Body:** JSON object containing review details.
    - **Responses:**
      - `201 Created` with confirmation message.

## Dependencies
This project uses the following dependencies:

- **Spring Boot Starter Web**: Essential for building web applications, including RESTful APIs using Spring MVC. It comes with Tomcat as the default embedded container.
- **Spring Boot Starter Web Services**: Used for building SOAP web services.
- **Spring Boot Starter Data JPA**: Simplifies the implementation of data access layers by integrating Spring Data JPA and Hibernate.
- **Spring Boot DevTools**: Provides a range of development-time features like automatic restarts for a smoother development process. This dependency is marked as optional and is used at runtime only.
- **JsonPath**: A library for parsing JSON documents, making it easier to extract and query JSON data. We're using version `2.9.0`.
- **Spring Boot Starter Test**: Offers comprehensive testing capabilities with libraries such as JUnit, Mockito, and Hamcrest, scoped for test purposes.
- **SQLite JDBC**: The JDBC driver for SQLite, enabling Java applications to connect to SQLite databases. The version used is `3.41.2.2`.
- **Hibernate Community Dialects**: Provides additional dialects for Hibernate, extending support for various database systems. The project uses version `6.4.2.Final`.
- **Spring Boot Starter OAuth2 Client**: Adds support for OAuth2/OpenID Connect based authentication and authorization.
- **Spring Boot Starter Security**: Integrates Spring Security into the application for authentication and authorization mechanisms.
- **Spring Security Config & Core**: Provides essential security configurations and core functionalities. The version for `spring-security-core` used is `6.2.1`.