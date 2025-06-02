# Use Maven image to build the project
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy Maven descriptor and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# Use a minimal Java runtime image
FROM openjdk:21-jdk AS runner

WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/target/service-registry-0.0.1-SNAPSHOT.jar ./app.jar

# Expose port
EXPOSE 8761

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
