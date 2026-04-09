# API Testing Examples

This document contains example requests for testing the Authentication API.

## Base URL

```
http://localhost:8080
```

## Endpoints

### 1. Test API Status

**Request:**
```http
GET /api/auth/test
```

**cURL:**
```bash
curl http://localhost:8080/api/auth/test
```

**Expected Response:**
```
Authentication API is running
```

---

### 2. Register New User

**Request:**
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePass123"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "securePass123"
  }'
```

**Success Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDg2NDAwfQ.xxx",
  "type": "Bearer",
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "username": "john_doe",
  "email": "john@example.com",
  "roles": "ROLE_USER"
}
```

**Error Response - Duplicate Username (400 Bad Request):**
```json
{
  "timestamp": "2024-03-30T10:15:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Username is already taken",
  "path": "/api/auth/register"
}
```

**Error Response - Invalid Email (400 Bad Request):**
```json
{
  "timestamp": "2024-03-30T10:15:30",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "email": "Email must be valid"
  },
  "path": "/api/auth/register"
}
```

---

### 3. Login

**Request:**
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Success Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDg2NDAwfQ.xxx",
  "type": "Bearer",
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "username": "admin",
  "email": "admin@myapp.com",
  "roles": "[ROLE_ADMIN, ROLE_USER]"
}
```

**Error Response - Invalid Credentials (401 Unauthorized):**
```json
{
  "timestamp": "2024-03-30T10:15:30",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password",
  "path": "/api/auth/login"
}
```

---

### 4. Using JWT Token for Protected Endpoints

After logging in or registering, you receive a JWT token. Use this token in the `Authorization` header for protected endpoints.

**Example Protected Request:**
```http
GET /api/protected-resource
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**cURL:**
```bash
# First, save the token to a variable
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}' \
  | grep -o '"token":"[^"]*' | cut -d'"' -f4)

# Then use it in subsequent requests
curl http://localhost:8080/api/protected-resource \
  -H "Authorization: Bearer $TOKEN"
```

---

## Complete Testing Workflow

### 1. Test API
```bash
curl http://localhost:8080/api/auth/test
```

### 2. Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### 3. Login with Admin
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### 4. Login with Regular User
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "user123"
  }'
```

---

## Testing Rate Limiting

The API has rate limiting on auth endpoints (default: 5 requests per 60 seconds).

**Test Rate Limiting:**
```bash
# Run this command multiple times quickly
for i in {1..10}; do
  echo "Request $i:"
  curl -X POST http://localhost:8080/api/auth/login \
    -H "Content-Type: application/json" \
    -d '{"username": "admin", "password": "wrongpassword"}'
  echo ""
  echo ""
done
```

After the 5th request, you should get:
```json
{
  "error": "Too many requests. Please try again later."
}
```

---

## Validation Testing

### Test Short Username
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "ab",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Test Invalid Email
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "invalid-email",
    "password": "password123"
  }'
```

### Test Short Password
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "123"
  }'
```

---

## Using Swagger UI

For an interactive API testing experience, open:

```
http://localhost:8080/swagger-ui.html
```

Swagger UI provides:
- Interactive API documentation
- Try-it-out functionality
- Request/response examples
- Schema validation
- Authorization setup

---

## Importing to Postman

1. Create a new Postman Collection
2. Add these requests as separate items
3. Set the base URL as a collection variable: `{{baseUrl}}`
4. After login/register, save the token as an environment variable: `{{token}}`
5. Use `Authorization: Bearer {{token}}` for protected endpoints

---

## Tips

1. **Pretty Print JSON Response:**
   ```bash
   curl ... | jq '.'
   ```

2. **Save Token to File:**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username": "admin", "password": "admin123"}' \
     | jq -r '.token' > token.txt
   ```

3. **Use Saved Token:**
   ```bash
   curl http://localhost:8080/api/protected \
     -H "Authorization: Bearer $(cat token.txt)"
   ```

4. **Verbose Output (see headers):**
   ```bash
   curl -v http://localhost:8080/api/auth/test
   ```
