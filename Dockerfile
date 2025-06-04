# FOR PRODUCTION 
#FROM openjdk:21-jdk-slim
#COPY target/demo-0.0.1-SNAPSHOT.jar app.jar 
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:21-slim
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY . . 
RUN  mvn clean install 
EXPOSE 5000 
CMD ["mvn" , "spring-boot:run"] 
