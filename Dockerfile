FROM maven:3.9.11-eclipse-temurin-24 AS build
WORKDIR /app

COPY pom.xml ./
COPY src/ ./src/

RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app

COPY --from=build /app/target/quarkus-app/ ./

EXPOSE 8081
CMD ["java", "-jar", "quarkus-run.jar"]