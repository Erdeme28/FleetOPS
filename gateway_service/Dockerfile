# ETAPA 1: Build (Compilarea)
# Folosim imaginea Maven cu Java 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiem fișierul de configurare
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiem codul sursă și compilăm
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Runtime (Rularea)
# Folosim JRE 21 pentru rulare (imagine mică)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiem jar-ul generat
COPY --from=build /app/target/*.jar app.jar

# Expunem portul
EXPOSE 8080

# Comanda de start
ENTRYPOINT ["java", "-jar", "app.jar"]