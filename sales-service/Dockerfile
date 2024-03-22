FROM openjdk:21-jdk
ARG JAR_FILE=target/sales-service-0.0.1.jar
COPY ${JAR_FILE} app-sales-service.jar
ENTRYPOINT ["java", "-jar", "app-sales-service.jar"]