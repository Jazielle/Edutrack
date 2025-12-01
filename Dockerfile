# 1. Fase de Construcción (Build Stage)
# Usa una imagen base que ya tiene Java y Maven preinstalados
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven para descargar dependencias
COPY pom.xml .

# Descarga las dependencias (optimiza el caché)
RUN mvn dependency:go-offline

# Copia el código fuente (src/ main/ java/ resources)
COPY src ./src

# Compila y empaqueta el código en un JAR
RUN mvn clean package -DskipTests

# 2. Fase de Producción (Run Stage)
# Usa una imagen base más ligera (solo Java JRE) para el runtime
FROM eclipse-temurin:17-jre-alpine

# Copia el archivo JAR generado en la fase de construcción a la carpeta de producción
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando de inicio FINAL:
# Inyecta la clave secreta directamente como propiedad del sistema (-D) para asegurar
# que la clave se pase sin el carácter ilegal que rompe Base64.
# Usamos la clave limpia: CLAVEFINALSECRETAEDUCTRACKFINAL
ENTRYPOINT ["java", "-Djwt.secret=CLAVEFINALSECRETAEDUCTRACKFINAL", "-jar", "app.jar"]