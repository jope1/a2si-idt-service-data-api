FROM openjdk:8-jdk-alpine
VOLUME /tmp

ADD ./target/idt-system-case-api-0.0.1-SNAPSHOT.jar idt-system-case-api.jar

# Expose 7060, the default port for the IDT System Case API
EXPOSE 7060
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar idt-system-case-api.jar" ]
