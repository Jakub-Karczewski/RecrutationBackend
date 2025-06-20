# Używamy oficjalnego obrazu JDK do uruchomienia aplikacji
FROM eclipse-temurin:17-jdk-alpine

# Ustaw katalog roboczy w kontenerze
WORKDIR /app

# Kopiujemy JAR do kontenera
COPY build/libs/*.jar app.jar

# Expose port (domyślnie Spring Boot działa na 8080)
EXPOSE 8080

# Komenda uruchamiająca aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]