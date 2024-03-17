FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/eureka-service-0.0.1.jar
COPY ${JAR_FILE} app-eureka.jar
ENTRYPOINT ["java", "-jar", "app-eureka.jar"]