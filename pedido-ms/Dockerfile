FROM gradle:7.5.0-jdk17 AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test
FROM openjdk:17
COPY --from=build /app/build/libs/*.jar pedidos-ms.jar
ENTRYPOINT ["java", "-jar", "pedidos-ms.jar"]