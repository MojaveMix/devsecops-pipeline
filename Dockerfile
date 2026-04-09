# -------------------------
# Stage 1: Build
# -------------------------
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copier uniquement pom.xml pour profiter du cache Docker
COPY pom.xml .

# Installer les dépendances
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Builder le projet et générer le jar, sans exécuter les tests
RUN mvn package -DskipTests -B

# -------------------------
# Stage 2: Run
# -------------------------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copier le jar buildé
COPY --from=build /app/target/*.jar app.jar

# Exposer le port
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]