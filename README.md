# brank-edge-team15
# Basic example of microservices with Spring Boot
This repository contains a basic example of coding microservices using Spring Boot. 
Tests, Dockerfiles, etc. are to be added in a later version.

The example architecture is as follows:

![Architecture](https://github.com/Tibeau/brank-edge-team15/blob/main/microservice-model.JPG)

One Edge service brank-edge-service will connect to two lower services book-info-service  and review-service  to request information which it will then process and combine into a single response to the user. The user is only supposed to communicate with the brank-edge-service.

## Running the example

The two lower services each connect to one Dockerized database each. These two databases need to be ran before the example can be tried out.

Set up the Docker container with the MySQL database:
 pwsh
docker run --name books-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=abc123 -d mysql 

Set up the Docker container with the MongoDB database:
 pwsh
docker run --name reviews-mongodb -p 27017-27019:27017-27019 -d mongo


Once this is done one can start the book-info-service, review-service and brank-edge-service applications.

The code of  book-info-service  and review-service includes two methods that will fill these databases with some dummy information. These methods are solely present as a shortcut and learning tool.

In case the example is used as is, and these methods are still present, one should be able to make this successful request to the brank-edge-service:

 pwsh
 (Invoke-WebRequest http://localhost:8050/rankings/book/687468435454).RawContent

 To get the output:
 
 
 HTTP/1.1 200
Transfer-Encoding: chunked
Content-Type: application/json
Date: [...]

{"bookTitle":"Test boek 1","userScores":[{"userId":3,"scoreNumber":5},{"userId":2,"scoreNumber":2}],"isbn":"687468435454"}
 
