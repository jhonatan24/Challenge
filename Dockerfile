FROM eclipse-temurin:17-alpine

WORKDIR /tenpo

COPY target/*.jar /tenpo/app.jar

CMD ["java","-jar","app.jar"]