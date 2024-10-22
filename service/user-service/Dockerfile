FROM openjdk:17-slim
VOLUME /tmp
ADD ./build/libs/*.jar user-service.jar

ENTRYPOINT ["java", "-jar", "/user-service.jar"]