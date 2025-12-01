# 1. Fase de Construcción (Build Stage)
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Fase de Producción (Run Stage)
FROM eclipse-temurin:17-jre-alpine

# Copia el archivo JAR generado
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando FINAL de INICIO:
# Inyección de la clave secreta directamente como propiedad del sistema (-D)
# *Esta es la sintaxis más robusta de Shell para forzar la variable*
ENTRYPOINT ["java", "-Djwt.secret=CLAVEFINALSECRETAEDUCTRACKFINAL", "-jar", "app.jar"]