FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests -f /app/pom.xml
FROM openjdk:17
COPY --from=build /app/target/*.jar gateway-ms.jar
ENTRYPOINT ["java", "-jar", "gateway-ms.jar"]

