# Task Tracker Backend

Welcome to the Task Tracker Backend repository! This repository contains the backend code for a task tracking application built using Spring Boot.

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)


## Overview

The Task Tracker Backend is a Java-based backend for a task management application. It consists of the following key components:

- **API Endpoints:** Provides RESTful API endpoints for tasks, user registration, login, and more.

- **Authentication:** Implements JWT-based authentication for secure user access to the application.

- **Validation:** Includes custom validation for email, username, and password to ensure data integrity.

- **Sending Welcome Emails:** Utilizes Kafka to send welcoming emails to newly registered users.

- **Task Management:** CRUD operations for tasks are available.


### Technologies

- Java
- Spring Boot 
- Kafka (for sending welcome emails)
- JSON Web Tokens (JWT) for authentication

## Project Structure

he project structure is organized as follows:

- `src/main/java`: Contains the Java source code.
  - `com.example.tasktracker.controller`: Controllers for handling HTTP requests.
  - `com.example.tasktracker.dto`: Data transfer objects (DTOs).
  - `com.example.tasktracker.entity`: Entity classes.
  - `com.example.tasktracker.exception`: Custom exception classes.
  - `com.example.tasktracker.security`: Security-related classes.
  - `com.example.tasktracker.service`: Business logic and services.
  - `com.example.tasktracker.validation`: Custom validation annotations.
- `src/main/resources`: Contains application properties and configuration.
- `src/test`: Contains unit and integration tests.
- `Dockerfile`: Docker configuration file (optional).

## API Endpoints

The backend exposes the following API endpoints:

- `POST /api/auth/register`: User registration.
- `POST /api/auth/login`: User login.
- `GET /api/tasks`: Retrieve all tasks for the authenticated user.
- `GET /api/tasks/{taskId}`: Retrieve a specific task.
- `POST /api/tasks`: Create a new task.
- `PUT /api/tasks/{taskId}`: Update an existing task.
- `DELETE /api/tasks/{taskId}`: Delete a task.

Please refer to the API documentation for detailed usage instructions.
