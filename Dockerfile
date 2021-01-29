FROM adoptopenjdk:11-jre-openj9

WORKDIR /groupprio-backend
COPY ./target/groupprio-jar-with-dependencies.jar ./groupprio-jar-with-dependencies.jar

CMD ["java", "-jar", "./groupprio-jar-with-dependencies.jar"]