FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
# Install netcat (used by wait-for-db script) and copy wait script
RUN apt-get update && apt-get install -y curl netcat-openbsd && rm -rf /var/lib/apt/lists/*

COPY wait-for-db.sh ./wait-for-db.sh
RUN chmod +x ./wait-for-db.sh

ENTRYPOINT ["sh", "-c", "./wait-for-db.sh && java -jar app.jar"]
