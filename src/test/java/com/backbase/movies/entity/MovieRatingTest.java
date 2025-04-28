package com.backbase.movies.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieRatingTest {

    @Test
    public void testNoArgsConstructor() {
        MovieRating movieRating = new MovieRating();

        assertNotNull(movieRating);
        assertNull(movieRating.getId());
        assertNull(movieRating.getUserId());
        assertEquals(0, movieRating.getRating());
        assertNull(movieRating.getMovie());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        String userId = "user123";
        int rating = 8;
        AcademyAwards movie = new AcademyAwards();

        MovieRating movieRating = new MovieRating(id, userId, rating, movie);

        assertEquals(id, movieRating.getId());
        assertEquals(userId, movieRating.getUserId());
        assertEquals(rating, movieRating.getRating());
        assertEquals(movie, movieRating.getMovie());
    }

    @Test
    public void testBuilder() {
        Long id = 2L;
        String userId = "user456";
        int rating = 9;

        MovieRating movieRating = MovieRating.builder()
                .id(id)
                .userId(userId)
                .rating(rating)
                .build();

        assertEquals(id, movieRating.getId());
        assertEquals(userId, movieRating.getUserId());
        assertEquals(rating, movieRating.getRating());
        assertNull(movieRating.getMovie());
    }

    @Test
    public void testSettersAndGetters() {
        MovieRating movieRating = new MovieRating();
        Long id = 3L;
        String userId = "user789";
        int rating = 7;
        AcademyAwards movie = new AcademyAwards();

        movieRating.setId(id);
        movieRating.setUserId(userId);
        movieRating.setRating(rating);
        movieRating.setMovie(movie);

        assertEquals(id, movieRating.getId());
        assertEquals(userId, movieRating.getUserId());
        assertEquals(rating, movieRating.getRating());
        assertEquals(movie, movieRating.getMovie());
    }

    @Test
    public void testRatingValidation() {
        MovieRating movieRating = new MovieRating();

        movieRating.setRating(1);  // minimum valid rating
        movieRating.setRating(10); // maximum valid rating

        assertEquals(10, movieRating.getRating());
    }

    @Test
    void testUserAndMovieUniqueConstraint() {
        AcademyAwards movie1 = new AcademyAwards();
        movie1.setId(1L);

        AcademyAwards movie2 = new AcademyAwards();
        movie2.setId(2L);

        MovieRating rating1 = MovieRating.builder()
                .userId("uniqueUser")
                .movie(movie1)
                .build();

        MovieRating rating2 = MovieRating.builder()
                .userId("uniqueUser")
                .movie(movie2)
                .build();

        MovieRating rating3 = MovieRating.builder()
                .userId("uniqueUser")
                .movie(movie1)
                .build();

        assertNotEquals(rating1, rating2);
        assertNotEquals(rating1, rating3);
        assertNotEquals(rating1.getMovie(), rating2.getMovie());
    }
}