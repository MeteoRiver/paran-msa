FROM openjdk:17-slim
VOLUME /tmp
ADD ./build/libs/*.jar comment-service.jar

ENTRYPOINT ["java", "-jar", "/comment-service.jar"]