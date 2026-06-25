# Multi-stage
# Build
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

RUN useradd -ms /bin/bash appuser

COPY --from=build /app/target/*.jar app.jar

RUN chown appuser:appuser app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]