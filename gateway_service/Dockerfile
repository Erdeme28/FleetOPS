# ETAPA 1: Build (Compilarea)
# Folosim o imagine oficială de Maven cu Java 17
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copiem fișierul de configurare Maven și descărcăm dependențele
# (Asta ajută la cache, să nu descarce internetul de fiecare dată)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiem codul sursă și îl compilăm
COPY src ./src
# -DskipTests e important ca să nu crape build-ul încercând să se lege la baza de date care nu e pornită
RUN mvn clean package -DskipTests

# ETAPA 2: Runtime (Rularea)
# Folosim o imagine mai mică, doar cu Java (fără Maven), pentru a rula aplicația
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiem jar-ul generat în etapa anterioară
COPY --from=build /app/target/*.jar app.jar

# Expunem portul 8080
EXPOSE 8080

# Comanda de start
ENTRYPOINT ["java", "-jar", "app.jar"]