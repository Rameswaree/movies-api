package com.backbase.movies.service;

import com.backbase.movies.entity.Movie;
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

    public List<Movie> getTop10Movies() {
        return movieRepository.findTop10ByOrderByBoxOfficeDesc();
    }
}
