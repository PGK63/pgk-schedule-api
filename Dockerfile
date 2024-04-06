FROM openjdk:17.0.2-jdk-slim
COPY build/libs/pgk-1.0.0-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
