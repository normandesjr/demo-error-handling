FROM openjdk:8-jdk-alpine

COPY build/libs/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8080
CMD java $JAVA_OPTIONS -jar app.jar