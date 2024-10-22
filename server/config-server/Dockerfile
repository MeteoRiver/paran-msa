FROM openjdk:17-slim
VOLUME /tmp
ADD ./build/libs/*.jar config-server.jar

ENTRYPOINT ["java", "-jar", "/config-server.jar"]