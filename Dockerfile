FROM gradle:7.5-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim

EXPOSE 80

RUN mkdir /app

COPY build/libs/*.jar /app/rmr-proshivka-messenger.jar

ENTRYPOINT ["java","-jar","/app/rmr-proshivka-messenger.jar"]
