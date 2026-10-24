# Taskmaster

Taskmaster is a Spring Boot-based task tracking and collaboration backend for teams. It supports user authentication, task management, team/project membership, comments, and file attachments.

## Features

- Secure user registration and login
- JWT-based authentication
- Profile management
- Team creation and member invitations
- Task creation, assignment, updates, completion, and deletion
- Task filtering, sorting, and search
- Comments on tasks
- Attachment upload support
- H2 in-memory database for local development

## Tech Stack

- Java 21
- Spring Boot 3.3.5
- Spring Security
- Spring Data JPA
- H2 Database
- Maven

## Project Structure

- `src/main/java/com/airtribe/taskmaster` — application code
- `src/main/resources/application.yml` — app configuration
- `http/` — REST client request files for quick API testing

## Prerequisites

- Java 21+
- Maven

## Run the Application

From the project root:

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## Demo Credentials

The app seeds demo users automatically:

- Admin: `admin@taskmaster.io` / `admin123`
- Member: `jane@taskmaster.io` / `jane123`

## API Testing

The project includes HTTP request files in the `http/` folder for testing endpoints quickly in VS Code REST Client or IntelliJ HTTP Client.

1. Start the app.
2. Log in using the auth endpoint.
3. Copy the JWT token from the login response.
4. Add it as a variable such as:

```http
@token = <your-jwt-token>
```

5. Run the requests from the `.http` files.

## Main Endpoints

### Authentication

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/logout`

### Users

- `GET /api/users/me`
- `PUT /api/users/me`

### Teams

- `POST /api/teams`
- `GET /api/teams`
- `POST /api/teams/{teamId}/members/{userId}`

### Tasks

- `POST /api/tasks`
- `GET /api/tasks`
- `GET /api/tasks/my`
- `GET /api/tasks/{taskId}`
- `PUT /api/tasks/{taskId}`
- `PATCH /api/tasks/{taskId}/complete`
- `PATCH /api/tasks/{taskId}/assign`
- `DELETE /api/tasks/{taskId}`
- `POST /api/tasks/{taskId}/comments`
- `POST /api/tasks/{taskId}/attachments`

## Notes

- The project uses an in-memory H2 database for local development.
- JWT tokens are stateless, so logout is handled by removing the token on the client side.
- This backend is a clean starting point for a real-world task management product and is easy to extend with notifications, email, AI summaries, and a frontend.

## HTTP Files

- `http/auth.http`
- `http/users.http`
- `http/teams.http`
- `http/tasks.http`
- `http/test.http`
