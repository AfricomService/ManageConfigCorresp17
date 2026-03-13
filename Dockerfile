FROM eclipse-temurin:17-jdk

EXPOSE 8058

ADD target/config-manage-0.0.1-SNAPSHOT.jar application.jar

ENTRYPOINT ["java", "-jar", "/application.jar"]
