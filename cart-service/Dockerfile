FROM openjdk:21-jdk
ARG JAR_FILE=target/cart-service-0.0.1.jar
COPY ${JAR_FILE} app-cart-service.jar
ENTRYPOINT ["java", "-jar", "app-cart-service.jar"]