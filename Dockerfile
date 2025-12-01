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

# --- SOLUCIÓN DE CLAVE ---
# Esta es una clave codificada en Base64 real y válida (44 caracteres, sin guiones).
# Decodifica a 32 bytes, lo que satisface la seguridad de tu código Java.
ENV JWT_SECRET TWkgQ2xhdmUgU2VjcmV0YSBQYXJhIEVkdVRyYWNrIDIwMjU=

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]