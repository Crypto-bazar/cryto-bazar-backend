FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

EXPOSE 8080

ENTRYPOINT ["sh", "gradlew", "bootRun"]
