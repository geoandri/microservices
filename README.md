# Microservices event driven example

A simple microservices event driven implementation architecture built with quarkus.
It consists of two microservices, developers and teams,  that use a Kafka broker to exchange events.
Open tracing and distributed logging capabilities are also supported.

## Running developers-service in dev mode
```shell script
cd developers-service
./mvn compile quarkus:dev
```
## Running teams-service in dev mode
```shell script
cd teams-service
./mvn compile quarkus:dev
```
