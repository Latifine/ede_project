## Enterprise Development Project - Ilias Latifine

## Subject: Bar Events

## Contents

- [Introduction](#introduction)
- [Diagram](#diagram)
- [CI-CD pipline](#ci-cd-pipeline)
- [0Auth2](#0auth2)



## Introduction

In this project i developed a Java backend architecture with microservices.

- 3 microservices
  - 2 MySQL services
  - 1 MongoDB service
- Github Actions
- Docker Compose
- API gateway with 0Auth2

with this project you can see bars and their events but also which beers each bar has and a list of all the beers.

## Diagram

Below you can see the diagram of my project

![Diagram](https://github.com/Latifine/ede_project/blob/main/images%20ede/Ede-Project-Diagram.jpg)

## CI-CD pipeline

### Github Actions
A Github workflow is made that includes building an uploading docker containers for each service.

![Github Actions](https://github.com/Latifine/ede_project/blob/main/images%20ede/Github-actions.JPG)


### Docker Compose

The Docker Compose file makes sure that all the containers are made and started for all the services. These containers include databases but also the custom services.

![Docker Compose](https://github.com/Latifine/ede_project/blob/main/images%20ede/dockercompose.JPG)

### Docker Desktop

All the containers are runned local on my own device and we can check them via Docker Desktop.

![Docker Desktop](https://github.com/Latifine/ede_project/blob/main/images%20ede/dockerdesktop.JPG)

## 0Auth2

We used 0Auth2 on the API gateway. So you can only do basic functions without being autorized.

### Without key

When i try to delete a beer from the list without being autorized it will not work.
![No Key](https://github.com/Latifine/ede_project/blob/main/images%20ede/noauth.JPG)

### With key

When i try to delete a beer with the right autorization key it will work.
![Key](https://github.com/Latifine/ede_project/blob/main/images%20ede/auth.JPG)

## Postman Requests

### Beer Service

Get all beers
![AllBeers](https://github.com/Latifine/ede_project/blob/main/images%20ede/allBeers.JPG)

Get beers by name
![Beer](https://github.com/Latifine/ede_project/blob/main/images%20ede/beerlist.JPG)

Add beer
![New Beer 1](https://github.com/Latifine/ede_project/blob/main/images%20ede/newBeer1.JPG)

Show new beer
![New Beer 2](https://github.com/Latifine/ede_project/blob/main/images%20ede/newBeer2.JPG)

Delete beer
![Delete Beer 1](https://github.com/Latifine/ede_project/blob/main/images%20ede/deleteBeer1.JPG)

Show Deleted beer
![Delete Beer 2](https://github.com/Latifine/ede_project/blob/main/images%20ede/deleteBeer2.JPG)


