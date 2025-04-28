# Assumptions
- The movies present in the CSV file are loaded into the table academyAwards while starting the application.
- Since OMDB API allows only GET requests, the user rating can be provided only for the movies present in the table academyAwards.
- Each user can give a one-time rating per movie.
- The user can provide service-token header while accessing the endpoints.
- Currently, the token is configured in properties, but later it can come from secret manager.