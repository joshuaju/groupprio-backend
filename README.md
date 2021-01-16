# docker

Make sure to build the application first 

> mvn package

Now build the docker image

> docker build -t groupprio-backend . 

Run the image

> docker run -p 8080:8080 -i groupprio-backend