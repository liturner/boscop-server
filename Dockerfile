FROM eclipse-temurin:17-jre

EXPOSE 8080

COPY target/cop-server.jar /opt/thw-cop/cop-server.jar
COPY target/lib /opt/thw-cop/lib

WORKDIR /opt/thw-cop
ENTRYPOINT ["java", "-jar", "/opt/thw-cop/cop-server.jar"]