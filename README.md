# Spring Boot Authentication API

A production-ready Spring Boot 3.2+ REST API with JWT authentication, MySQL database, and comprehensive security features.

## Features

- **Spring Boot 3.2+** with Java 21
- **JWT Authentication** with secure token-based auth
- **MySQL Database** with JPA/Hibernate
- **Spring Security** with role-based authorization (ROLE_USER, ROLE_ADMIN)
- **BCrypt Password Hashing**
- **OWASP Top 10 Security**:
  - SQL Injection protection (JPA/Hibernate)
  - Input validation and XSS prevention
  - CSRF protection
  - Secure headers (CSP, X-Frame-Options, etc.)
  - CORS configuration
  - IP-based rate limiting for auth endpoints
- **Swagger/OpenAPI** documentation
- **Comprehensive unit tests** with H2 in-memory database
- **Exception handling** with proper error responses
- **Logging** with SLF4J
- **Data validation** with Jakarta Validation

## Prerequisites

- **Java 21+**
- **Maven 3.6+**
- **MySQL 8.0+**

## Quick Start

### 1. Configure Database

Edit `src/main/resources/application.properties`:

```properties
# Change these values to match your MySQL setup
spring.datasource.url=jdbc:mysql://localhost:3306/myapp_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password_here
```

**To change the database name:**
- Replace `myapp_db` with your desired database name in the URL above

### 2. Configure JWT Secret

In `application.properties`, update the JWT secret (IMPORTANT for production):

```properties
jwt.secret=your-secret-key-change-this-to-a-long-secure-random-string-at-least-256-bits
```

Generate a secure secret key:
```bash
openssl rand -base64 64
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR:
```bash
java -jar target/spring-boot-auth-api-1.0.0.jar
```

The API will start on `http://localhost:8080`

### 5. Default Users

The application automatically seeds two default users:

| Username | Password  | Roles                  | Email             |
|----------|-----------|------------------------|-------------------|
| admin    | admin123  | ROLE_ADMIN, ROLE_USER  | admin@myapp.com   |
| user     | user123   | ROLE_USER              | user@myapp.com    |

## API Endpoints

### Authentication

#### Register a New User
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "username": "newuser",
  "email": "newuser@example.com",
  "roles": "ROLE_USER"
}
```

#### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "username": "admin",
  "email": "admin@myapp.com",
  "roles": "[ROLE_ADMIN, ROLE_USER]"
}
```

#### Test API
```bash
GET /api/auth/test
```

### Using JWT Token

Include the JWT token in the Authorization header for protected endpoints:

```bash
GET /api/protected-endpoint
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## Testing

### Run Unit Tests

```bash
mvn test
```

### Test Coverage

The project includes comprehensive unit tests for:
- User registration (valid/invalid data)
- User login (valid/invalid credentials)
- Input validation
- Duplicate username/email handling
- Authentication errors

### Manual Testing with cURL

**Register a new user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

## API Documentation

### Swagger UI

Access interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON

```
http://localhost:8080/v3/api-docs
```

## Security Features

### OWASP Top 10 Protection

1. **SQL Injection**: Protected by JPA/Hibernate parameterized queries
2. **XSS**: Input validation with Jakarta Validation, XSS headers enabled
3. **CSRF**: CSRF protection configured (disabled for stateless JWT)
4. **Security Headers**:
   - Content Security Policy (CSP)
   - X-Frame-Options: DENY
   - X-XSS-Protection: 1; mode=block
   - Referrer-Policy: strict-origin-when-cross-origin
5. **CORS**: Configurable CORS policy
6. **Rate Limiting**: IP-based rate limiting for auth endpoints (5 requests per 60 seconds)
7. **Proper Error Handling**: No sensitive information in error responses
8. **Password Security**: BCrypt hashing with salt
9. **JWT Security**: HMAC-SHA256 signed tokens with expiration
10. **Input Validation**: Comprehensive validation on all inputs

### Rate Limiting

Configure rate limiting in `application.properties`:

```properties
rate.limit.capacity=5      # Maximum requests
rate.limit.tokens=5        # Tokens to refill
rate.limit.duration=60     # Duration in seconds
```

## Configuration

### Application Properties

Key configuration options in `src/main/resources/application.properties`:

```properties
# Database (CHANGE THESE)
spring.datasource.url=jdbc:mysql://localhost:3306/myapp_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password_here

# JWT (CHANGE THIS IN PRODUCTION)
jwt.secret=your-secret-key-change-this-to-a-long-secure-random-string
jwt.expiration=86400000  # 24 hours in milliseconds

# Server
server.port=8080

# Rate Limiting
rate.limit.capacity=5
rate.limit.tokens=5
rate.limit.duration=60
```

## Project Structure

```
src/
├── main/
│   ├── java/com/myapp/
│   │   ├── config/           # Configuration classes
│   │   │   ├── CorsConfig.java
│   │   │   ├── DataSeeder.java
│   │   │   ├── OpenApiConfig.java
│   │   │   ├── RateLimitingFilter.java
│   │   │   ├── SecurityConfig.java
│   │   │   └── WebSecurityConfig.java
│   │   ├── controller/       # REST controllers
│   │   │   └── AuthController.java
│   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── AuthResponse.java
│   │   │   ├── ErrorResponse.java
│   │   │   ├── LoginRequest.java
│   │   │   └── RegisterRequest.java
│   │   ├── exception/        # Exception handling
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── model/            # Entity models
│   │   │   └── User.java
│   │   ├── repository/       # Data repositories
│   │   │   └── UserRepository.java
│   │   ├── security/         # Security components
│   │   │   ├── AuthEntryPointJwt.java
│   │   │   ├── AuthTokenFilter.java
│   │   │   ├── JwtUtils.java
│   │   │   ├── UserDetailsImpl.java
│   │   │   └── UserDetailsServiceImpl.java
│   │   ├── service/          # Business logic
│   │   │   ├── AuthService.java
│   │   │   └── UserService.java
│   │   └── Application.java  # Main application class
│   └── resources/
│       ├── application.properties
│       └── data.sql
└── test/                     # Unit tests
    ├── java/com/myapp/
    │   └── controller/
    │       └── AuthControllerTest.java
    └── resources/
        └── application-test.properties
```

## Database Schema

### Users Table

| Column      | Type         | Constraints                    |
|-------------|--------------|--------------------------------|
| id          | UUID         | PRIMARY KEY                    |
| username    | VARCHAR(50)  | NOT NULL, UNIQUE               |
| email       | VARCHAR(100) | NOT NULL, UNIQUE               |
| password    | VARCHAR(255) | NOT NULL                       |
| roles       | VARCHAR(255) | NOT NULL                       |
| created_at  | TIMESTAMP    | NOT NULL, DEFAULT CURRENT_TIME |
| updated_at  | TIMESTAMP    | DEFAULT CURRENT_TIME           |

## Troubleshooting

### Database Connection Issues

1. Ensure MySQL is running:
   ```bash
   sudo systemctl status mysql
   ```

2. Verify credentials in `application.properties`

3. Check if the database exists:
   ```bash
   mysql -u root -p
   SHOW DATABASES;
   ```

### JWT Token Issues

1. Ensure the JWT secret is at least 256 bits (32 bytes)
2. Check token expiration time
3. Verify the Authorization header format: `Bearer <token>`

### Rate Limiting

If you're rate-limited, wait 60 seconds or adjust the rate limit configuration in `application.properties`

## Production Deployment

### Security Checklist

- [ ] Change default database credentials
- [ ] Generate and set a strong JWT secret (256+ bits)
- [ ] Update CORS configuration for your frontend domain
- [ ] Configure HTTPS/TLS
- [ ] Review and adjust rate limiting settings
- [ ] Disable H2 console if enabled
- [ ] Set `spring.jpa.show-sql=false` in production
- [ ] Configure proper logging levels
- [ ] Remove or secure Swagger UI in production
- [ ] Set up database backups
- [ ] Configure firewall rules

### Environment Variables

For production, use environment variables instead of hardcoded values:

```bash
export DB_URL="jdbc:mysql://your-db-host:3306/myapp_db"
export DB_USERNAME="your_username"
export DB_PASSWORD="your_password"
export JWT_SECRET="your-production-secret-key"
```

Update `application.properties`:
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

## License

Apache 2.0

## Support

For issues or questions, please open an issue on the repository.
