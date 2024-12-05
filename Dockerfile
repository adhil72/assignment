FROM openjdk:21
WORKDIR /app
COPY target/assignment-1.0.jar /app/assignment-1.0.jar
EXPOSE 3000
CMD ["java", "-jar", "assignment-1.0.jar"]
