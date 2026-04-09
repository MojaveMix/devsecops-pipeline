#!/bin/bash

echo "================================================"
echo "Spring Boot Authentication API"
echo "================================================"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed. Please install Java 21 or higher."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "Error: Java 21 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

echo "Java and Maven are installed. Starting build..."
echo ""

# Build the project
echo "Building the project..."
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "Build successful! Starting the application..."
    echo ""
    echo "The API will be available at: http://localhost:8080"
    echo "Swagger UI: http://localhost:8080/swagger-ui.html"
    echo ""
    echo "Default credentials:"
    echo "  Admin: admin / admin123"
    echo "  User:  user / user123"
    echo ""
    echo "Press Ctrl+C to stop the server"
    echo "================================================"
    echo ""
    
    # Run the application
    mvn spring-boot:run
else
    echo ""
    echo "Build failed. Please check the error messages above."
    exit 1
fi
