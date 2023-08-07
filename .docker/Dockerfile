# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /build
COPY src . ./
RUN mvn package

#Stage 2: Create the final lightweight image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /build/target/app-0.0.1-SNAPSHOT.jar ./app-0.0.1-SNAPSHOT.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","./app-0.0.1-SNAPSHOT.jar"]
