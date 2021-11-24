FROM openjdk:11-jre-slim

ADD com.modular.build.jar /opt/build/

EXPOSE 8080

WORKDIR /opt/build/

CMD ["java", "-jar", "com.modular.build.jar"]