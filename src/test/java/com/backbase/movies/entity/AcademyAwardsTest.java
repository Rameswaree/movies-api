package com.backbase.movies.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AcademyAwardsTest {

    @Test
    public void testNoArgsConstructor() {
        AcademyAwards awards = new AcademyAwards();

        assertNotNull(awards);
        assertNull(awards.getId());
        assertNull(awards.getReleaseYear());
        assertNull(awards.getCategory());
        assertNull(awards.getNominee());
        assertNull(awards.getAdditionalInfo());
        assertNull(awards.getWon());
        assertNull(awards.getMovieRatings());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        String releaseYear = "2020";
        String category = "Best Picture";
        String nominee = "Nominee Name";
        String additionalInfo = "Additional info";
        String won = "YES";
        List<MovieRating> ratings = List.of(new MovieRating());

        AcademyAwards awards = new AcademyAwards(id, releaseYear, category, nominee,
                additionalInfo, won, ratings);

        assertEquals(id, awards.getId());
        assertEquals(releaseYear, awards.getReleaseYear());
        assertEquals(category, awards.getCategory());
        assertEquals(nominee, awards.getNominee());
        assertEquals(additionalInfo, awards.getAdditionalInfo());
        assertEquals(won, awards.getWon());
        assertEquals(ratings, awards.getMovieRatings());
    }

    @Test
    public void testBuilder() {
        Long id = 2L;
        String releaseYear = "2021";
        String category = "Best Director";
        String nominee = "Another Nominee";
        String additionalInfo = "More info";
        String won = "NO";

        AcademyAwards awards = AcademyAwards.builder()
                .id(id)
                .releaseYear(releaseYear)
                .category(category)
                .nominee(nominee)
                .additionalInfo(additionalInfo)
                .won(won)
                .build();

        assertEquals(id, awards.getId());
        assertEquals(releaseYear, awards.getReleaseYear());
        assertEquals(category, awards.getCategory());
        assertEquals(nominee, awards.getNominee());
        assertEquals(additionalInfo, awards.getAdditionalInfo());
        assertEquals(won, awards.getWon());
        assertNull(awards.getMovieRatings()); // Not set in builder
    }

    @Test
    public void testSettersAndGetters() {
        AcademyAwards awards = new AcademyAwards();
        Long id = 3L;
        String releaseYear = "1994";
        String category = "Best Actor";
        String nominee = "Tom Hanks";
        String additionalInfo = "Forrest Gump";
        String won = "YES";
        List<MovieRating> ratings = List.of(new MovieRating());

        awards.setId(id);
        awards.setReleaseYear(releaseYear);
        awards.setCategory(category);
        awards.setNominee(nominee);
        awards.setAdditionalInfo(additionalInfo);
        awards.setWon(won);
        awards.setMovieRatings(ratings);
        
        assertEquals(id, awards.getId());
        assertEquals(releaseYear, awards.getReleaseYear());
        assertEquals(category, awards.getCategory());
        assertEquals(nominee, awards.getNominee());
        assertEquals(additionalInfo, awards.getAdditionalInfo());
        assertEquals(won, awards.getWon());
        assertEquals(ratings, awards.getMovieRatings());
    }
}