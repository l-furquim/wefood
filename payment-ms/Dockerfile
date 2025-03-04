FROM gradle:7.5.0-jdk17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -f ./payment-ms/pom.xml
FROM openjdk:17
COPY --from=build ./payment-ms/target/*.jar payment-ms.jar
ENTRYPOINT ["java", "-jar", "payment-ms.jar"]