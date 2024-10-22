FROM openjdk:17-slim
VOLUME /tmp
ADD ./build/libs/*.jar chat-service.jar

ENTRYPOINT ["java", "-jar", "/chat-service.jar"]