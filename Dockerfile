# build
FROM maven:3.6.3-adoptopenjdk-11 as build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

# run
FROM adoptopenjdk:11-jre-openj9
WORKDIR /groupprio-backend
COPY --from=build /usr/src/app/target/groupprio-jar-with-dependencies.jar /usr/app/groupprio-jar-with-dependencies.jar
CMD ["java", "-jar", "/usr/app/groupprio-jar-with-dependencies.jar"]