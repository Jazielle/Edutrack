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

# --- SOLUCIÓN DEL ERROR ---
# Tu código Java exige una clave en formato Base64 estándar.
# Esta clave de abajo YA está convertida a Base64 y tiene la longitud correcta.
# NO LA CAMBIES NI LE AGREGUES COMILLAS.
ENV JWT_SECRET RVNUQV9FU19VTkFfQ0xBVkVfU0VHVVJ1X1BBUkFfSkRXVF9QMjAyNQ==

# Comando simple para que tome la variable de arriba
ENTRYPOINT ["java", "-jar", "app.jar"]