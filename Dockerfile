# syntax=docker/dockerfile:1

FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /workspace

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY src src

RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]