## Enterprise Development Project - Ilias Latifine

## Subject: Bar Events

## Contents

- [Introduction](#introduction)
- [Diagram](#diagram)

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
![Github Actions]()

### Docker Compose

The Docker Compose file makes sure that all the containers are made and started for all the services. These containers include databases but also the custom services.
![Docker Compose]()

### Docker Desktop

All the containers are runned local on my own device and we can check them via Docker Desktop.
![Docker Desktop]()


