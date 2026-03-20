FROM openjdk:17.0.1-jdk-slim

RUN useradd spring
USER spring:spring
ARG DEPENDENCY=target/dependency

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENV PORT 8080
EXPOSE $PORT
ENTRYPOINT ["java","-cp","app:app/lib/*","-Dserver.port=${PORT}","org.springframework.samples.banking.BankingApplication"]
