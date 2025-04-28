package com.backbase.movies.service;

import com.backbase.movies.exception.MovieServiceException;
import com.backbase.movies.model.MovieRatingSearchResponse;
import com.backbase.movies.model.MovieSearchResponse;
import com.backbase.movies.model.OmdbResponse;

import java.util.List;

public interface MovieService {
    /**
     * searchMovie is used to search movies details from omdb api and check if it won oscar or not from DB
     * @param title title of the movie
     * @return MovieSearchResponse
     */
    MovieSearchResponse searchMovie(String title);

    /**
     * This method search for best pictures from Db and then Call omdb to get details of those movies. Its returen top ten
     * movies on bases of box office revenue
     * @return List<OmdbResponse>
     */
    List<OmdbResponse> listTopTenMovies();

    /**
     * This method is used to give rating of movie
     * @param movieTitle title of the movie
     * @param userId the user ID
     * @param rating the user provided rating
     */
    void rateMovie(String movieTitle, String userId, int rating) throws MovieServiceException;

    /**
     * This method calculate the average of movie rating
     * @param movieTitle title of the movie
     * @return MovieRatingSearchResponse
     */
    MovieRatingSearchResponse getAverageRating(String movieTitle);
}
