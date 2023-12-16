FROM eclipse-temurin:17.0.7_7-jre

EXPOSE 8080

COPY boscop-application/target/boscop*.war /opt/boscop

WORKDIR /opt/boscop
ENTRYPOINT ["sh", "/opt/boscop/BOSCOP.sh"]