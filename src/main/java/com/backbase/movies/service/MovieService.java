package com.backbase.movies.service;

import com.backbase.movies.entity.Movie;
import com.backbase.movies.entity.Rating;
import com.backbase.movies.exception.MovieNotFoundException;
import com.backbase.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean hasWonBestPicture(String title) {
        return movieRepository.findByTitleIgnoreCase(title)
                .map(Movie::isBestPictureWinner).orElse(false);
    }

    public List<Movie> getTop10Movies() {
        return movieRepository.findTop10ByOrderByBoxOfficeDesc();
    }

    public void addRatings(String title, int ratingScore) throws MovieNotFoundException {
        Movie movie = movieRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        Rating rating = new Rating();
        rating.setScore(ratingScore);

        movie.getRatings().add(rating);
        movieRepository.save(movie);
    }
}
