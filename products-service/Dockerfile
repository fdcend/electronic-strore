FROM openjdk:21-jdk
ARG JAR_FILE=target/products-service-0.0.1.jar
COPY ${JAR_FILE} app-products-service.jar
ENTRYPOINT ["java", "-jar", "app-products-service.jar"]