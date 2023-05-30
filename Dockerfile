FROM maven:3.8.3-openjdk-17 as builder
COPY . .
RUN mvn package
ENTRYPOINT java -jar /target/*.jar