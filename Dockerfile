FROM eclipse-temurin:17-jre

EXPOSE 8080

COPY boscop-jlink/target/maven-jlink/default /opt/boscop
COPY conf /opt/boscop/conf
COPY frontend /opt/boscop/frontend

WORKDIR /opt/boscop
ENTRYPOINT ["sh", "/opt/boscop/bin/BOSCOP"]