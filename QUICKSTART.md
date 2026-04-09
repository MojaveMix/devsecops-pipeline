# Quick Start Guide

This guide will help you get the Spring Boot Authentication API running in minutes.

## Step 1: Install Prerequisites

Ensure you have:
- Java 21+ installed: `java -version`
- Maven 3.6+ installed: `mvn -version`
- MySQL 8.0+ running

## Step 2: Setup MySQL Database

1. Start MySQL server
2. Create database (or let the app create it automatically):
   ```sql
   CREATE DATABASE myapp_db;
   ```

## Step 3: Configure Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/myapp_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Replace `YOUR_MYSQL_PASSWORD` with your MySQL root password.

## Step 4: Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will start at `http://localhost:8080`

## Step 5: Test the API

### Option 1: Using cURL

**Test the API:**
```bash
curl http://localhost:8080/api/auth/test
```

**Login with default admin user:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

**Register a new user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "myuser",
    "email": "myuser@example.com",
    "password": "mypassword123"
  }'
```

### Option 2: Using Swagger UI

Open your browser and go to:
```
http://localhost:8080/swagger-ui.html
```

You can test all endpoints interactively!

## Default Test Accounts

| Username | Password | Roles                 |
|----------|----------|-----------------------|
| admin    | admin123 | ROLE_ADMIN, ROLE_USER |
| user     | user123  | ROLE_USER             |

## Common Issues

### MySQL Connection Failed
- Verify MySQL is running: `sudo systemctl status mysql`
- Check credentials in `application.properties`
- Ensure the database exists or set `createDatabaseIfNotExist=true` in the URL

### Port 8080 Already in Use
Change the port in `application.properties`:
```properties
server.port=8081
```

### JWT Secret Warning
For production, generate a secure secret:
```bash
openssl rand -base64 64
```

Then update `jwt.secret` in `application.properties`.

## Next Steps

- Read the full [README.md](README.md) for detailed documentation
- Explore the Swagger UI at `http://localhost:8080/swagger-ui.html`
- Check out the test cases in `src/test/java/com/myapp/controller/AuthControllerTest.java`
- Customize the application for your needs

## Running Tests

```bash
mvn test
```

All tests should pass!

## Need Help?

Check the [README.md](README.md) for detailed troubleshooting and configuration options.
