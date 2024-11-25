FROM openjdk:17-jdk-alpine
COPY target/banca-0.0.1-SNAPSHOT.jar banca.jar
ENTRYPOINT ["java", "-jar", "banca.jar"]