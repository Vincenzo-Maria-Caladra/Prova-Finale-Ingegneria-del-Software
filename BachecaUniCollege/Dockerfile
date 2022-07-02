FROM openjdk:11
COPY target/BachecaUniCollege-0.0.1-SNAPSHOT.jar BachecaUniCollege.jar
EXPOSE 8080
VOLUME ["/tmp/activity-photos", "/var/log"]
ENTRYPOINT ["java","-jar","/BachecaUniCollege.jar"]