FROM openjdk:17-slim
VOLUME /tmp
ADD ./build/libs/*.jar group-service.jar

ENTRYPOINT ["java", "-jar", "/group-service.jar"]