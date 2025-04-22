# Dockerfile (в корне проекта)
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

# Предположим, что api генерирует jar в target/api-*.jar
COPY --from=builder /build/api/target/api-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

