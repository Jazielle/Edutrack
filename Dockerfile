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

# Comando de inicio: Inyecta la clave secreta directamente como propiedad del sistema (-D).
# Esto es más robusto contra los errores de Base64 de la librería JWT.
ENTRYPOINT ["java", "-Djwt.secret=CLAVEFINALSECRETAEDUCTRACKFINAL", "-jar", "app.jar"]