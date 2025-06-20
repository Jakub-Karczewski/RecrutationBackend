
FROM gradle:8.3-jdk17 AS builder


WORKDIR /app


COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .


RUN ./gradlew build --no-daemon || return 0


COPY src src

RUN ./gradlew clean build --no-daemon


FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
