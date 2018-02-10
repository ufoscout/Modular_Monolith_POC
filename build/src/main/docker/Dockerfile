FROM iron/java:1.8
ADD com.modular.build.jar /opt/build/
EXPOSE 8080
WORKDIR /opt/build/
CMD ["java", "-jar", "com.modular.build.jar"]