FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY --from=build /app/target/reserva-ms-0.0.1-SNAPSHOT.jar reserva-ms.jar
RUN chown spring:spring reserva-ms.jar

USER spring

EXPOSE 8082
ENTRYPOINT ["java","-jar","reserva-ms.jar"]
