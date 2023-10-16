FROM eclipse-temurin:17-jdk-alpine
LABEL authors="Saalik Khan"

COPY target/velocity-limits-0.0.1-SNAPSHOT.jar velocity-limits-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/velocity-limits-0.0.1-SNAPSHOT.jar"]
