# WomanCanCodeAPIChallenge

API created as a final project for the java back-end bootcamp

## Tv Series Collection API

The aplication is a REST API that save a collection of series, with the name, the year and the number of seasons of each serie.


## The aplication has those endpoints:

Default **port** : 8080

**Post** : "http://localhost:" + **port** + "/serie"  - Post a serie on the h2 database

 Ex Body :{
    "name":"Chaves",
    "year":1971,
    "totalSeasons":2
}

**Put by Id** : "http://localhost:" + **port** + "/serie/" + **id**  - Update a serie on the h2 database

 Ex Body :{
    "name":"Chaves",
    "year":**2020**,
    "totalSeasons":2
}

**Get All** : "http://localhost:" + **port** + "/serie" - Get the list of series of the h2 database

**Get by Id** :  "http://localhost:" + **port** + "/serie/"+**id** - Get the serie by Id from the h2 database

**Delete by Id** : "http://localhost:" + **port** + "/serie/"+**id** - Delete a serie on the h2 database by Id

**Get Search Serie By Name from TVMaze API** : "http://localhost:" + **port** + "/serie/search/" + **name** 
- Get a list of series from the TVMaze API ( **http://api.tvmaze.com/search/shows?q=name** and **http://api.tvmaze.com/shows/id/season**)
- Parse to the serie collection Serie format

## Tests
- Unit test on the h2 database methods
- Test on the endpoints
- Test on the Search Serie endpoint

## Docker 
```shell 
./mvnw package 
java -jar target/demo-0.0.1-SNAPSHOT.jar 
docker build --build-arg 'JAR_FILE=target/*.jar' -t alelochallenge .
docker run -p 8080:8080 alelochallenge
```
## Swagger documentation


## Setup
Java 14


