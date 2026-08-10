# Spring Quiz

Spring Quiz is a full-stack quiz application with a Spring Boot REST API and a React/Vite frontend. It supports two user roles:

- Regular users can register, log in, view available quizzes, submit answers, finish quizzes, and view their results.
- Administrators can manage subjects, questions, answers, and view quiz statistics.

The backend uses JWT authentication, Spring Security, Spring Data JPA, and an H2 file database. Demo data is created automatically on startup when the database is empty.

## Tech Stack

- Java 8 target, tested with JDK 17
- Spring Boot 2.7
- Spring Web
- Spring Security
- Spring Data JPA
- H2 Database
- JWT
- React
- Vite

## Default Accounts

The application creates these users automatically:

| Username | Password | Roles |
| --- | --- | --- |
| `student` | `student123` | `USER` |
| `admin` | `admin123` | `USER`, `ADMIN` |

## Running the Backend

Start the Spring Boot application from your IDE by running:

```text
com.example.quiz.Application
```

The API runs on:

```text
http://localhost:8080
```

The H2 database is stored in the project directory as `quiz-db.mv.db`.

## Running the Frontend

From the frontend directory:

```bash
cd frontend
npm install
npm run dev
```

The Vite development server will print the local frontend URL in the terminal.

## API Usage With Postman

Set a Postman environment variable:

| Variable | Value |
| --- | --- |
| `baseUrl` | `http://localhost:8080` |

For protected requests, add this header:

```http
Authorization: Bearer {{token}}
```

After login or registration, copy the `token` value from the response and save it as the Postman environment variable `token`.

## Authentication

### Login

```http
POST {{baseUrl}}/api/auth/login
Content-Type: application/json
```

Body:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Response:

```json
{
  "token": "jwt-token-here",
  "tokenType": "Bearer"
}
```

### Register

```http
POST {{baseUrl}}/api/auth/register
Content-Type: application/json
```

Body:

```json
{
  "username": "newuser",
  "password": "password123",
  "passwordConfirm": "password123"
}
```

## Public Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/hello` | Health check, returns `hello`. |
| `POST` | `/api/auth/login` | Log in and receive a JWT token. |
| `POST` | `/api/auth/register` | Register a regular user and receive a JWT token. |
| `GET` | `/api/quizzes` | List available quiz subjects. Works without auth, but authenticated users also get `canPass` based on their history. |

## User Endpoints

These endpoints require a bearer token.

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/profile` | Get current username and admin status. |
| `GET` | `/api/quizzes/{subjectId}` | Start or load a quiz for a subject. |
| `POST` | `/api/quizzes/answers` | Submit one answer. |
| `POST` | `/api/quizzes/{subjectId}/results` | Finish a quiz and calculate the score. |
| `GET` | `/api/quizzes/{subjectId}/answers` | View detailed answer results for a completed quiz. |

Submit an answer:

```http
POST {{baseUrl}}/api/quizzes/answers
Authorization: Bearer {{token}}
Content-Type: application/json
```

Body:

```json
{
  "subjectId": 1,
  "questionId": 1,
  "answerId": 1
}
```

Finish quiz:

```http
POST {{baseUrl}}/api/quizzes/1/results
Authorization: Bearer {{token}}
```

Example result:

```json
{
  "subjectId": 1,
  "subjectTitle": "Spring Boot basics",
  "quizScore": 4
}
```

## Admin Endpoints

These endpoints require an admin token. Log in as `admin` / `admin123`.

### Subjects

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/subjects` | List subjects. |
| `GET` | `/api/subjects/{subjectId}` | Get a subject with its questions. |
| `POST` | `/api/subjects` | Create a subject. |
| `PUT` | `/api/subjects/{subjectId}` | Update a subject. |
| `DELETE` | `/api/subjects/{subjectId}` | Delete a subject. |

Create or update a subject:

```json
{
  "title": "Java Collections",
  "isActive": true
}
```

### Questions

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/subjects/{subjectId}/questions` | List questions for a subject. |
| `POST` | `/api/subjects/{subjectId}/questions` | Create a question for a subject. |
| `GET` | `/api/questions/{questionId}` | Get one question. |
| `PUT` | `/api/questions/{questionId}` | Update a question. |
| `DELETE` | `/api/questions/{questionId}` | Delete a question. |

Create or update a question:

```json
{
  "title": "Which collection stores unique values?",
  "points": 1
}
```

### Answers

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/questions/{questionId}/answers` | List answers for a question. |
| `POST` | `/api/questions/{questionId}/answers` | Create an answer. |
| `PUT` | `/api/answers/{answerId}` | Update an answer. |
| `PUT` | `/api/answers/{answerId}/correct` | Mark an answer as correct. |
| `DELETE` | `/api/answers/{answerId}` | Delete an answer. |

Create or update an answer:

```json
{
  "answerTitle": "Set",
  "correct": true
}
```

### Statistics

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/admin/stats` | Get quiz pass counts by subject. |
| `GET` | `/api/admin/stats/subjects/{subjectId}` | Get user scores for one subject. |
| `GET` | `/api/admin/stats/users/{userId}` | Get quiz results for one user. |

## Recommended Postman Flow

1. `POST /api/auth/login` with `admin` / `admin123`.
2. Save the returned JWT as `token`.
3. Add `Authorization: Bearer {{token}}` to protected requests.
4. Create a subject with `POST /api/subjects`.
5. Create questions with `POST /api/subjects/{subjectId}/questions`.
6. Create answers with `POST /api/questions/{questionId}/answers`.
7. Log in as `student` / `student123`.
8. Start a quiz with `GET /api/quizzes/{subjectId}`.
9. Submit answers with `POST /api/quizzes/answers`.
10. Finish with `POST /api/quizzes/{subjectId}/results`.

## Error Responses

API errors are returned as JSON:

```json
{
  "error": "Error message"
}
```

Common statuses:

| Status | Meaning |
| --- | --- |
| `400` | Invalid request or validation error. |
| `401` | Missing or invalid JWT token. |
| `403` | Authenticated user does not have permission. |
| `404` | Entity was not found. |
| `409` | Conflict, for example trying to pass the same quiz twice. |
