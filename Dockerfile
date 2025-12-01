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

# Establece la variable de entorno JWT_SECRET (Solo el valor, sin comillas)
ENV JWT_SECRET CLAVEFINALSECRETAEDUCTRACKFINAL

# Comando para ejecutar la aplicación JAR e inyectar la URL de la DB
# *Nota: La clave JWT_SECRET debe ser leída por Spring Boot desde ENV.*
ENTRYPOINT ["java", "-jar", "app.jar"]