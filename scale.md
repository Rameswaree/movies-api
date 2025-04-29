# How to Scale
- Load balancers can be used to ensure that there is high availability when there is high traffic and also when multiple instances of the application is running.
- While rating a movie, it is possible to have multiple users add ratings to the same movie at the same time. By using queues or kafka, the scaling can be done and this makes it asynchronous.
- Performing asynchronous calls to call OMDB API is the other option for scalability.