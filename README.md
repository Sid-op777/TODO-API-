# To-Do List API

## Overview

This project is a RESTful API built with Spring Boot, Maven, and Java that allows users to manage their to-do lists. It includes user authentication using JWT (JSON Web Tokens) and provides endpoints for creating, reading, updating, and deleting to-do items.

## Features

*   **User Authentication:**
    *   User registration
    *   Login with JWT token generation
*   **To-Do Management:**
    *   Create, read, update, and delete To-Do items
    *   Secured API endpoints (requires JWT authentication)
    *   Pagination for listing To-Do items

## Technologies Used

*   Java
*   Spring Boot
*   Maven
*   Spring Data JPA
*   H2 Database
*   JWT (JSON Web Tokens)
*   BCryptPasswordEncoder

## Prerequisites

Before you begin, ensure you have the following installed:

*   Java Development Kit (JDK) 17 or higher
*   Maven
*   An IDE (such as IntelliJ IDEA or Eclipse)
*   A database (e.g., H2, PostgreSQL, MySQL)

## Setup and Installation

1.  **Clone the Repository:**

    ```bash
    git clone <repository_url>
    cd <project_directory>
    ```

    Replace `<repository_url>` with the URL of your GitHub repository and `<project_directory>` with the name of your project directory.

2.  **Configure the Database:**

    *   Update the `src/main/resources/application.properties` file with your database configuration. For example, for H2:

        ```properties
        spring.application.name=todolist
        spring.h2.console.enabled=true
        spring.datasource.url=jdbc:h2:mem:testdb
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=
        spring.jpa.defer-datasource-initialization=true
        spring.jpa.hibernate.ddl-auto=update
        ```

    *   If you're using a different database (e.g., PostgreSQL, MySQL), configure the appropriate properties.

3.  **Set JWT Secret Key:**

    *   Generate a strong, random secret key.
    *   Set the `jwt.secret` property in your `application.properties` file with the new secret key.

        ```properties
        jwt.secret=YOUR_JWT_SECRET_KEY
        jwt.expiration=86400000 # 24 hours
        ```



4.  **Build the Project:**

    ```bash
    mvn clean install
    ```

    This command will download dependencies and build the project.

## Running the Application

1.  **Run the Spring Boot Application:**

    You can run the application from your IDE or using the command line:

    ```bash
    mvn spring-boot:run
    ```

2.  **Access the API:**

    The API will be running at `http://localhost:8080`.

## API Endpoints

### User Authentication

*   **`POST /auth/register`**: Register a new user.

    ```json
    {
      "name": "Test User",
      "email": "test@example.com",
      "password": "securePassword123"
    }
    ```

    Response:

    ```json
    {
      "id": 1,
      "name": "Test User",
      "email": "test@example.com",
      "password": "hashed_password"
    }
    ```

*   **`POST /auth/login`**: Login and obtain a JWT.

    ```json
    {
      "email": "john.doe@example.com",
      "password": "securePassword123"
    }
    ```

    Response:

    ```
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."  (JWT)
    ```

### To-Do Management

**Authentication:** All To-Do management endpoints require a valid JWT in the `Authorization` header.
Authorization: Bearer <JWT>
*   **`POST /todos`**: Create a new To-Do item.

    ```json
    {
      "title": "Buy groceries",
      "description": "Milk, eggs, bread"
    }
    ```



*   **`GET /todos/{id}`**: Get a To-Do item by ID.


*   **`PUT /todos/{id}`**: Update a To-Do item.

    ```json
    {
      "title": "Buy groceries (updated)",
      "description": "Milk, eggs, bread, cheese"
    }
    ```


*   **`DELETE /todos/{id}`**: Delete a To-Do item.

*   **`GET /todos?page={page}&size={size}`**: Get all To-Do items with pagination.



## Data Validation

The API uses `@javax.validation.constraints` annotations to validate request data.  For example, the `User` entity has `@NotBlank`, `@Email`, and `@Size` annotations to ensure that user data is valid.

## Error Handling

The API returns appropriate HTTP status codes and error messages for common errors, such as:

*   `400 Bad Request`: For invalid request data (e.g., missing required fields, invalid email format).
*   `401 Unauthorized`: For invalid or missing JWTs.
*   `403 Forbidden`: For unauthorized access.
*   `404 Not Found`: For resources that cannot be found (e.g., To-Do item with a non-existent ID).
*   `500 Internal Server Error`: For unexpected server-side errors.

