package com.backbase.movies.service.impl;

import com.backbase.movies.entity.AcademyAwards;
import com.backbase.movies.entity.MovieRating;
import com.backbase.movies.exception.MovieServiceException;
import com.backbase.movies.model.MovieRatingSearchResponse;
import com.backbase.movies.model.MovieSearchResponse;
import com.backbase.movies.model.OmdbResponse;
import com.backbase.movies.remote.client.OmdbClient;
import com.backbase.movies.repository.AcademyAwardsRepository;
import com.backbase.movies.repository.MovieRatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @Mock
    private OmdbClient omdbClient;

    @Mock
    private AcademyAwardsRepository academyAwardsRepository;

    @Mock
    private MovieRatingRepository ratingRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private static final String MOVIE_TITLE = "The Godfather";
    private static final String USER_ID = "user123";
    private static final int RATING = 8;
    private static final String BEST_PICTURE = "Best Picture";

    private AcademyAwards academyAward;
    private OmdbResponse omdbResponse;
    private MovieRating movieRating;

    @BeforeEach
    public void setUp() {
        academyAward = new AcademyAwards();
        academyAward.setId(1L);
        academyAward.setNominee(MOVIE_TITLE);
        academyAward.setCategory(BEST_PICTURE);
        academyAward.setWon("YES");

        omdbResponse = new OmdbResponse();
        omdbResponse.setTitle(MOVIE_TITLE);
        omdbResponse.setYear("1972");
        omdbResponse.setImdbRating("9.2");
        omdbResponse.setBoxOffice("$134,966,411");
        omdbResponse.setType("movie");

        movieRating = MovieRating.builder()
                .id(1L)
                .rating(RATING)
                .movie(academyAward)
                .userId(USER_ID)
                .build();
    }

    @Test
    public void searchMovie_Success() {
        when(omdbClient.search(MOVIE_TITLE)).thenReturn(omdbResponse);
        when(academyAwardsRepository.findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE))
                .thenReturn(Optional.of(academyAward));

        MovieSearchResponse response = movieService.searchMovie(MOVIE_TITLE);

        assertNotNull(response);
        assertEquals(MOVIE_TITLE, response.getTitle());
        assertEquals("9.2", response.getImdbRating());
        assertEquals("YES", response.getWonOscar());
        verify(omdbClient).search(MOVIE_TITLE);
        verify(academyAwardsRepository).findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE);
    }

    @Test
    public void searchMovie_OmdbNotFound_ThrowsException() {
        when(omdbClient.search(MOVIE_TITLE)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> movieService.searchMovie(MOVIE_TITLE));
    }

    @Test
    public void searchMovie_AcademyAwardNotFound_ThrowsException() {
        when(omdbClient.search(MOVIE_TITLE)).thenReturn(omdbResponse);
        when(academyAwardsRepository.findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.searchMovie(MOVIE_TITLE));
    }

    @Test
    public void searchMovie_EmptyTitle_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> movieService.searchMovie(""));
        assertThrows(IllegalArgumentException.class, () -> movieService.searchMovie(null));
    }

    @Test
    public void listTopTenMovies_Success() {
        AcademyAwards award1 = new AcademyAwards();
        award1.setNominee("Movie1");
        award1.setCategory(BEST_PICTURE);
        award1.setMovieRatings(Collections.emptyList());

        AcademyAwards award2 = new AcademyAwards();
        award2.setNominee("Movie2");
        award2.setCategory(BEST_PICTURE);
        award2.setMovieRatings(Collections.emptyList());

        OmdbResponse response1 = new OmdbResponse();
        response1.setTitle("Movie1");
        response1.setBoxOffice("$100,000,000");
        response1.setRating(0.0);

        OmdbResponse response2 = new OmdbResponse();
        response2.setTitle("Movie2");
        response2.setBoxOffice("$200,000,000");
        response2.setRating(0.0);

        when(academyAwardsRepository.findAllByCategory(BEST_PICTURE))
                .thenReturn(Arrays.asList(award1, award2));
        when(omdbClient.search("Movie1")).thenReturn(response1);
        when(omdbClient.search("Movie2")).thenReturn(response2);

        List<OmdbResponse> result = movieService.listTopTenMovies();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Movie2", result.get(0).getTitle()); // Should be sorted by box office descending
        verify(academyAwardsRepository).findAllByCategory(BEST_PICTURE);
        verify(omdbClient, times(2)).search(anyString());
    }

    @Test
    public void listTopTenMovies_EmptyList_ReturnsNull() {
        when(academyAwardsRepository.findAllByCategory(BEST_PICTURE)).thenReturn(Collections.emptyList());

        List<OmdbResponse> result = movieService.listTopTenMovies();

        assertNull(result);
    }

    @Test
    public void rateMovie_Success() throws MovieServiceException {
        when(academyAwardsRepository.findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE))
                .thenReturn(Optional.of(academyAward));
        when(ratingRepository.save(any(MovieRating.class))).thenReturn(movieRating);

        movieService.rateMovie(MOVIE_TITLE, USER_ID, RATING);

        verify(academyAwardsRepository).findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE);
        verify(ratingRepository).save(any(MovieRating.class));
    }

    @Test
    public void rateMovie_MovieNotFound_ThrowsException() {
        when(academyAwardsRepository.findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> movieService.rateMovie(MOVIE_TITLE, USER_ID, RATING));
    }

    @Test
    public void rateMovie_DuplicateRating_ThrowsException() {
        when(academyAwardsRepository.findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE))
                .thenReturn(Optional.of(academyAward));
        when(ratingRepository.save(any(MovieRating.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate rating"));

        assertThrows(MovieServiceException.class,
                () -> movieService.rateMovie(MOVIE_TITLE, USER_ID, RATING));
    }

    @Test
    public void rateMovie_InvalidInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> movieService.rateMovie("", USER_ID, RATING));
        assertThrows(IllegalArgumentException.class,
                () -> movieService.rateMovie(MOVIE_TITLE, "", RATING));
        assertThrows(IllegalArgumentException.class,
                () -> movieService.rateMovie(null, USER_ID, RATING));
        assertThrows(IllegalArgumentException.class,
                () -> movieService.rateMovie(MOVIE_TITLE, null, RATING));
    }

    @Test
    public void getAverageRating_Success() {
        List<MovieRating> ratings = Arrays.asList(
                MovieRating.builder().rating(8).build(),
                MovieRating.builder().rating(9).build(),
                MovieRating.builder().rating(7).build()
        );

        when(academyAwardsRepository.findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE))
                .thenReturn(Optional.of(academyAward));
        when(ratingRepository.findByMovieId(academyAward.getId())).thenReturn(ratings);

        MovieRatingSearchResponse response = movieService.getAverageRating(MOVIE_TITLE);

        assertNotNull(response);
        assertEquals(MOVIE_TITLE, response.getMovieTitle());
        assertEquals(8.0, response.getRating());
    }

    @Test
    public void getAverageRating_NoRatings_ReturnsZero() {
        when(academyAwardsRepository.findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE))
                .thenReturn(Optional.of(academyAward));
        when(ratingRepository.findByMovieId(academyAward.getId())).thenReturn(Collections.emptyList());

        MovieRatingSearchResponse response = movieService.getAverageRating(MOVIE_TITLE);

        assertNotNull(response);
        assertEquals(0.0, response.getRating());
    }

    @Test
    public void getAverageRating_MovieNotFound_ThrowsException() {
        when(academyAwardsRepository.findByNomineeAndCategory(MOVIE_TITLE, BEST_PICTURE))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> movieService.getAverageRating(MOVIE_TITLE));
    }

    @Test
    public void calculateRatingAverage_ValidInput() {
        List<MovieRating> ratings = Arrays.asList(
                MovieRating.builder().rating(5).build(),
                MovieRating.builder().rating(7).build(),
                MovieRating.builder().rating(9).build()
        );

        double average = movieService.calculateRatingAverage(ratings);
        assertEquals(7.0, average);
    }

    @Test
    public void calculateRatingAverage_EmptyList_ReturnsZero() {
        double average = movieService.calculateRatingAverage(Collections.emptyList());
        assertEquals(0.0, average);
    }

    @Test
    public void parseBoxOfficeValue_ValidInput() {
        OmdbResponse movie = new OmdbResponse();
        movie.setBoxOffice("$123,456,789");

        long value = movieService.parseBoxOfficeValue(movie);
        assertEquals(123456789L, value);
    }

    @Test
    public void parseBoxOfficeValue_NA_ReturnsZero() {
        OmdbResponse movie = new OmdbResponse();
        movie.setBoxOffice("N/A");

        long value = movieService.parseBoxOfficeValue(movie);
        assertEquals(0L, value);
    }

    @Test
    public void parseBoxOfficeValue_Null_ReturnsZero() {
        OmdbResponse movie = new OmdbResponse();
        movie.setBoxOffice(null);

        long value = movieService.parseBoxOfficeValue(movie);
        assertEquals(0L, value);
    }
}