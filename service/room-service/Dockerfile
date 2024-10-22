FROM openjdk:17-slim
VOLUME /tmp
ADD ./build/libs/*.jar room-service.jar

ENTRYPOINT ["java", "-jar", "/room-service.jar"]