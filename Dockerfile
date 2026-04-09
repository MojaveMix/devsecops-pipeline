# -------------------------
# Stage 1: Build
# -------------------------
# Stage 1: Build & Test
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn test
RUN mvn package -DskipTests
# -------------------------
# Stage 2: Run
# -------------------------
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]