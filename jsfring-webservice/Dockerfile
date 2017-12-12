FROM openjdk:8-jdk-alpine

COPY ./target/webservice-1.0-SNAPSHOT.war .

ENTRYPOINT ["java", "-jar", "-Dspring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration", "webservice-1.0-SNAPSHOT.war"]
