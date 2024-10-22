FROM openjdk:17-slim
VOLUME /tmp
ADD ./build/libs/*.jar file-service.jar

ENTRYPOINT ["java", "-jar", "/file-service.jar"]