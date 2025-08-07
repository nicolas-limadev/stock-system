FROM openjdk:21-slim AS build
WORKDIR /app

COPY . .
RUN ./gradlew build -x test

FROM openjdk:21-slim
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]