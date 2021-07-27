FROM adoptopenjdk:11-jre-hotspot
COPY ./build/libs/account-validator-1.0.0.jar /usr/src/app/
WORKDIR /usr/src/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "account-validator-1.0.0.jar"]