# springboot-elk-prometheus-grafana

The goal of this project is to implement a [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) application, called `movies-api`, and use [`Filebeat`](https://www.elastic.co/beats/filebeat) & `ELK Stack` ([`Elasticsearch`](https://www.elastic.co/elasticsearch), [`Logstash`](https://www.elastic.co/logstash) and [`Kibana`](https://www.elastic.co/kibana)) to collect and visualize application's **logs** and [`Prometheus`](https://prometheus.io/) & [`Grafana`](https://grafana.com/) to monitor application's **metrics**.

## Application

- ### movies-api

  `Spring Boot` Web Java application that exposes a REST API for managing movies. Its endpoints are displayed in the picture below.

  ![movies-api](images/movies-api-swagger.png)

## Prerequisites

- [`Java 11+`](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [`Docker`](https://www.docker.com/)
- [`Docker-Compose`](https://docs.docker.com/compose/install/)

## Build Docker image for movies-api

- In a terminal, make sure you are inside `springboot-elk-prometheus-grafana` root folder
  - Run the following script to build the image
  ```
  ./docker-build.sh
  ```

## Start Environment

- Open a terminal and inside `springboot-elk-prometheus-grafana` root folder run
  ```
  docker-compose up -d
  ```

- Wait for Docker containers to be with status `running (healthy)` (except `filebeat`). To check it, run
  ```
  docker-compose ps
  ```

## Application & Services URLs

- **movies-api**
  
  `movies-api` Swagger is http://localhost:8080/swagger-ui.html

- **Prometheus**

  `Prometheus` can be accessed at http://localhost:9090

  ![prometheus](images/prometheus.png)

- **Grafana**

  `Grafana` can be accessed at http://localhost:3000

  - In order to login, type `admin` for both `username` and `password`
  - You can skip the next screen that ask you to provide a new password
  - Click `General / Home` on the top
  - Click `movies-api-dashboard`

  ![grafana](images/grafana.png)

- **Kibana**

  `Kibana` can be accessed at http://localhost:5601

  _Configuration_

  - Access `Kibana` website
  - Click `Explore on my own`
  - In the main page, click the _"burger"_ menu icon and, then click `Discover`
  - Click `Create index pattern` button
  - In the `Index pattern name` field, set `filebeat-*` and click `> Next Step` button
  - In the `Time field` combo-box, select `@Timestamp` and click `Create index pattern`
  - Click the _"burger"_ menu icon again and then click `Discover` to start performing searches
  
  ![kibana](images/kibana.png)

- **Elasticsearch**

  `Elasticsearch` URL is http://localhost:9200

  _Useful queries_
  ```
  # Check ES is up and running
  curl localhost:9200
  
  # Check indexes in ES
  curl "localhost:9200/_cat/indices?v"
  
  # Check filebeat index mapping
  curl "localhost:9200/filebeat-*/_mapping"
  
  # Simple search
  curl "localhost:9200/filebeat-*/_search?pretty"
  ```

## Shutdown

- To stop and remove `docker-compose` containers, network and volumes, go to a terminal and, inside `springboot-elk-prometheus-grafana` root folder, run the following command
  ```
  docker-compose down -v
  ```
  