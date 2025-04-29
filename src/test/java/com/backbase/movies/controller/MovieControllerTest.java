package com.backbase.movies.controller;

import com.backbase.movies.exception.MovieServiceException;
import com.backbase.movies.model.*;
import com.backbase.movies.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private final String testTitle = "Inception";
    private final String userId = "user123";
    private final int rating = 6;

    private MovieSearchResponse createTestMovieSearchResponse() {
        MovieSearchResponse response = new MovieSearchResponse();
        response.setTitle(testTitle);
        response.setYear("2010");
        response.setReleaseDate("16 Jul 2010");
        response.setWriter("Christopher Nolan");
        response.setActors("Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page");
        response.setCountry("USA, UK");
        response.setLanguages("English, Japanese, French");
        response.setType("movie");
        response.setImdbRating("8.8");
        response.setBoxOffice("$836,836,967");
        response.setWonOscar("Yes");
        return response;
    }

    @Test
    public void searchValidTitle_ReturnsOkResponse() {
        MovieSearchResponse expectedResponse = createTestMovieSearchResponse();
        when(movieService.searchMovie(testTitle)).thenReturn(expectedResponse);

        ResponseEntity<MovieSearchResponse> response = movieController.search(testTitle);

        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(movieService).searchMovie(testTitle);
    }

    @Test
    public void searchEmptyTitle_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> movieController.search(""));
        verifyNoInteractions(movieService);
    }

    @Test
    public void listTopTenMovies_ReturnsOkResponseWithMovies() {
        OmdbResponse movie1 = new OmdbResponse();
        movie1.setTitle(testTitle);
        movie1.setYear("2010");
        movie1.setImdbRating("8.8");
        movie1.setBoxOffice("$836,836,967");

        OmdbResponse movie2 = new OmdbResponse();
        movie2.setTitle("The Shawshank Redemption");
        movie2.setYear("1994");
        movie2.setImdbRating("9.3");
        movie2.setBoxOffice("$28,341,469");

        List<OmdbResponse> expectedMovies = Arrays.asList(movie1, movie2);
        when(movieService.listTopTenMovies()).thenReturn(expectedMovies);

        ResponseEntity<List<OmdbResponse>> response = movieController.listTopTenMovies();

        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedMovies, response.getBody());
        verify(movieService).listTopTenMovies();
    }

    @Test
    public void rateMovieValidRequest_ReturnsNoContent() throws MovieServiceException {
        MovieRatingRequest request = new MovieRatingRequest(testTitle, userId, rating);

        ResponseEntity<Void> response = movieController.rateMovie(request);

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(movieService).rateMovie(testTitle, userId, rating);
    }

    @Test
    public void rateMovie_ServiceThrowsException() throws MovieServiceException {
        MovieRatingRequest request = new MovieRatingRequest(testTitle, userId, rating);
        doThrow(new MovieServiceException("Error")).when(movieService).rateMovie(testTitle, userId, rating);

        assertThrows(MovieServiceException.class, () -> movieController.rateMovie(request));
        verify(movieService).rateMovie(testTitle, userId, rating);
    }

    @Test
    public void getMovieRatingForValidTitle_ReturnsOkResponse() {
        MovieRatingSearchResponse expectedResponse = new MovieRatingSearchResponse(1L, testTitle, rating);

        when(movieService.getAverageRating(testTitle)).thenReturn(expectedResponse);

        ResponseEntity<MovieRatingSearchResponse> response = movieController.getMovieRating(testTitle);

        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(movieService).getAverageRating(testTitle);
    }

    @Test
    public void getMovieRatingForEmptyTitle_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> movieController.getMovieRating(""));
        verifyNoInteractions(movieService);
    }
}