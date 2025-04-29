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
- Custom Service Token Interceptor for endpoint security

# Solution
- The application first persists all the Academy Awards data into H2 Database on startup.
- Service token is a must for using the API end points.
- When the user wants to search best movie by title first the services calls the OMDB API and then checks if the movie is present in our table. If it is present, then the details are displayed with ratings from OMDB and ratings from users that is stored in our database. 
- The user can also search for the ten top-rated movies on the basis of box office revenue.
- At first, the application lists all the Best Pictures from the database AcademyAwards and then calls the OMDB API to fetch the movie details. Based on these, the top ten movies are listed.
- The user can rate a movie and view rating of movies present in the database.
- The rating can be given from 1 to 10.
- When invalid input is provided in the APIs, appropriate exceptions are thrown and this is handled in the application.
  - Example: If you search for a movie not present in the website/db, or you try giving a rating beyond 1 to 10 or if you try to rate the same movie more than once.

# How to start
- Run **mvn clean install** to build the application.
- Start the application using any IDE or using mvn spring-boot:run command.
- The application server will start up on localhost:8083

## EndPoints

- The supported endpoints can be found in the Postman collection present in the project. 
- POST /api/movies/rating (To rate movies)
- GET /api/movies/list/top-ten (To get list of top 10 movies based on box office value along with their rating)
- GET /api/movies/search/best-picture/{{title}} (To search for a movie based on title, result displays whether box office winner)
- GET //api/movies/rating/{{title}}  (To view the average rating of a specific movie)

# Notes
Some records in the provided CSV file were not formatted properly due to presence of additional commas, semicolons, hence some adjustments were made in the file to make the data loading successful on application startup.
