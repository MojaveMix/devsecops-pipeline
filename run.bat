@echo off
echo ================================================
echo Spring Boot Authentication API
echo ================================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed. Please install Java 21 or higher.
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed. Please install Maven 3.6 or higher.
    exit /b 1
)

echo Java and Maven are installed. Starting build...
echo.

REM Build the project
echo Building the project...
call mvn clean install -DskipTests

if errorlevel 1 (
    echo.
    echo Build failed. Please check the error messages above.
    exit /b 1
)

echo.
echo Build successful! Starting the application...
echo.
echo The API will be available at: http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
echo Default credentials:
echo   Admin: admin / admin123
echo   User:  user / user123
echo.
echo Press Ctrl+C to stop the server
echo ================================================
echo.

REM Run the application
call mvn spring-boot:run
