# 1. Fase de Construcción
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Fase de Producción
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Clave secreta (simple para evitar errores)
ENV JWT_SECRET CLAVEFINALSECRETAEDUCTRACKFINAL

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]