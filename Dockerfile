FROM maven:3.8.3-openjdk-17 as builder

WORKDIR /app
COPY . .
RUN mvn clean test package

FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
