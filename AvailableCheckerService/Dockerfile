FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY pom.xml ./

COPY src src
RUN mvn package -DskipTests

FROM azul/zulu-openjdk-alpine:17
WORKDIR /app
COPY --from=build /app/target/availability-checker-service-0.0.1-SNAPSHOT.jar /app/availability.jar
EXPOSE 8080
CMD ["java", "-jar", "availability.jar"]
