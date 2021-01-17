# docker

Make sure to build the application first 

> mvn package

Now build the docker image

> docker build -t groupprio-backend . 

Run the image

> docker run -p 8080:8080 -i groupprio-backend

# docker-compose
Run the backend along with the database.

> docker-compose up

Shutdown services

> docker-compose down

Shutdown services and clean up (e.g. network, database)

> docker-compose down --rmi all --volumes