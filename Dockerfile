FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar file-uploader-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/file-uploader-0.0.1-SNAPSHOT.jar.jar"]
EXPOSE 8080