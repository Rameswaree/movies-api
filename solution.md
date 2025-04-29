# Movie API
Movie API Backend Service

# Technology
- Java 11
- Maven
- Spring Boot
- Rest API
- Lombok
- Opencsv 
- H2 Database
- Spring JPA
- Custom Service Token Interceptor for endpoint Security

# Prerequisite
The provided CSV file was not formatted properly and there were some adjustments made in the file to make the data loading successful on application startup.

# Solution
- The application first persists all the Academy Awards data into H2 Database on startup.
- When the user wants to search best movie by title then it calls OMDB API and then checks if the movie is present in our table. If it is present, then the details are displayed with both OMDB ratings and our own ratings. 
- The user can also search for the ten top-rated movies on the basis of box office revenue.
- At first, the application lists all the Best Pictures from the database AcademyAwards and then calls the OMDB API to fetch the movie details. Based on these, the top ten movies are listed.
- The user can rate a movie and view rating of movies present in the database.
- The rating can be given from 1 to 10.


# How to start
- Run **mvn clean install** to build the application.
- Start the application using any IDE or using mvn spring-boot:run command.
- The application server will start up on localhost:8083

## EndPoints

- The supported endpoints can be found in the Postman collection present in the project. 