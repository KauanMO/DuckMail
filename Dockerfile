FROM openjdk:21-jdk
RUN mkdir /server
WORKDIR /server
COPY target/*.jar /server/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/server/app.jar"]