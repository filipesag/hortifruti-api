# Build stage
FROM maven:3.9.9-amazoncorretto-17-alpine AS build
WORKDIR /build

# Primeiro copia apenas o POM para aproveitar cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Depois copia o código fonte
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM amazoncorretto:17.0.11-alpine3.19
WORKDIR /app
COPY --from=build /build/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

