FROM openjdk:17-slim
VOLUME /tmp
ADD ./build/libs/*.jar eureka-server.jar

ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]