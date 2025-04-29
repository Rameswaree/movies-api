# How to Scale

## Problem statement
When the list of movies increases, fetching the data would be time-consuming. Also, with multiple users accessing the application to rate movies, it can lead to high latency. 

## Solution to scaling
- Using distributed caches like Redis with appropriate TTL (Time To Live) helps in reducing database load in multi-instance applications, avoiding repeated inserts and this can be mainly used in the scenario where we insert data from the CSV file (a one-time insertion).
- If the application is going to be deployed on-premise, then using Apache HTTPD as a load balancer will be sufficient. If the deployment is Cloud-Based, then AWS or Kubernetes cluster could be used. With future plans of making this into a microservice application, a Spring Cloud load balancer can also be used to make the microservices communicate with each other. This way, handling the issue with high traffic for multi-instance application is resolved.
- Currently the application runs synchronously. To make it asynchronous, using queueing system like Kafka helps to resolve the scenario of multiple users adding ratings to the same movie at the same time.
