FROM openjdk:21-jdk
RUN mkdir /server
WORKDIR /server
COPY target/*.jar /server/app.jar
CMD ["java", "-jar", "/server/app.jar"]