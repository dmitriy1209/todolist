#Maven Build
FROM maven:3.9.4-eclipse-temurin-17-alpine AS builder
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests

#Run
FROM openjdk:17-jdk-alpine
COPY --from=builder /app/target/todolist-0.0.1-SNAPSHOT.war todolist.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "todolist.war"]