FROM openjdk:21
ARG JAR_FILE=target/api-gateway-0.0.1.jar
COPY ${JAR_FILE} app-api-gateway.jar
ENTRYPOINT ["java", "-jar", "app-api-gateway.jar"]