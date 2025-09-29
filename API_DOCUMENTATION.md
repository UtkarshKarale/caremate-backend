# API Documentation

This document provides the documentation for the API endpoints.

## User API

### 1. Register User

-   **Endpoint:** `POST /api/user/register`
-   **Description:** Registers a new user.
-   **Request Body:**
    ```json
    {
      "email": "user@example.com",
      "password": "password",
      "fullName": "John Doe",
      "mobile": "1234567890",
      "roles": "USER"
    }
    ```
-   **Response:**
    ```json
    {
      "email": "user@example.com",
      "fullName": "John Doe",
      "mobile": "1234567890",
      "roles": "USER",
      "message": "User registered successfully"
    }
    ```

### 2. Login User

-   **Endpoint:** `POST /api/user/login`
-   **Description:** Authenticates a user and returns a JWT token.
-   **Request Body:**
    ```json
    {
      "email": "user@example.com",
      "password": "password"
    }
    ```
-   **Response:**
    ```json
    {
      "token": "jwt_token",
      "role": "USER"
    }
    ```

### 3. Update User

-   **Endpoint:** `PUT /api/user/{id}/update/allfields`
-   **Description:** Updates a user's information.
-   **Path Variable:** `id` - The ID of the user to update.
-   **Request Body:**
    ```json
    {
      "fullName": "John Doe",
      "mobile": "1234567890"
    }
    ```
-   **Response:**
    ```json
    {
      "id": 1,
      "email": "user@example.com",
      "fullName": "John Doe",
      "mobile": "1234567890",
      "roles": "USER",
      "status": "ACTIVE"
    }
    ```

### 4. Find User by ID

-   **Endpoint:** `GET /api/user/{id}/lookup`
-   **Description:** Retrieves a user by their ID.
-   **Path Variable:** `id` - The ID of the user to retrieve.
-   **Response:**
    ```json
    {
      "id": 1,
      "email": "user@example.com",
      "fullName": "John Doe",
      "mobile": "1234567890",
      "roles": "USER",
      "status": "ACTIVE"
    }
    ```

### 5. Get All Users

-   **Endpoint:** `GET /api/user/lookup/all`
-   **Description:** Retrieves all users.
-   **Response:**
    ```json
    [
      {
        "id": 1,
        "email": "user@example.com",
        "fullName": "John Doe",
        "mobile": "1234567890",
        "roles": "USER",
        "status": "ACTIVE"
      }
    ]
    ```

### 6. Get All Active Users

-   **Endpoint:** `GET /api/user/lookup/all/active`
-   **Description:** Retrieves all active users.
-   **Response:**
    ```json
    [
      {
        "id": 1,
        "email": "user@example.com",
        "fullName": "John Doe",
        "mobile": "1234567890",
        "roles": "USER",
        "status": "ACTIVE"
      }
    ]
    ```

### 7. Forgot Password

-   **Endpoint:** `PUT /api/user/forgot-password`
-   **Description:** Sends a password reset code to the user's email.
-   **Request Parameter:** `email` - The email of the user.
-   **Response:**
    ```
    "Reset code sent to your email."
    ```

### 8. Reset Password

-   **Endpoint:** `PUT /api/user/reset-password`
-   **Description:** Resets the user's password.
-   **Request Body:**
    ```json
    {
      "email": "user@example.com",
      "resetCode": "reset_code",
      "newPassword": "new_password",
      "confirmPassword": "new_password"
    }
    ```
-   **Response:**
    ```
    "Password reset successfully."
    ```
