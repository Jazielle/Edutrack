# 1. Build
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Run
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Usamos una clave de texto simple directamente aquí.
# Al haber cambiado el código Java, esta clave de texto YA NO DARÁ ERROR.
ENV JWT_SECRET ClaveTextoNormalSinBase64_Edutrack2025

ENTRYPOINT ["java", "-jar", "app.jar"]