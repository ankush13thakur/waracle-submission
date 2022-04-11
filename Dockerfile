FROM openjdk:8-jdk
EXPOSE 8080
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN chmod 755 /app/mvnw
RUN ./mvnw package 

ENTRYPOINT ["java","-jar","target/cake-0.0.1-SNAPSHOT.jar"]