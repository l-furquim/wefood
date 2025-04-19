FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY mailsender-ms/ /app
RUN mvn clean package -DskipTests -f /app/pom.xml
FROM openjdk:17
COPY --from=build /app/target/*.jar mailsender-ms.jar
ENTRYPOINT ["java", "-jar", "mailsender-ms.jar"]
