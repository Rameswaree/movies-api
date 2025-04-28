# How to Scale
- While rating a movie, it is possible to have multiple users add ratings to the same movie at the same time. By using queues or kafka, the scaling can be done and this makes it asynchronous.
- Asynchronous call to call OMDB API is the other option for scalability.