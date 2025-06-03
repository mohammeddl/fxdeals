FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=build /app/target/fxdeals-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 2080

ENTRYPOINT ["java", "-jar", "app.jar"]
