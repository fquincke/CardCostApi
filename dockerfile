# Build stage
FROM gradle:8.4.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Runtime stage
FROM eclipse-temurin:17.0.9_9-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Security best practices
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
USER javauser

EXPOSE 7070
ENTRYPOINT ["java", "-jar", "app.jar"]