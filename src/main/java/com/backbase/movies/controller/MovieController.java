package com.backbase.movies.controller;

import com.backbase.movies.exception.MovieServiceException;
import com.backbase.movies.model.*;
import com.backbase.movies.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping(path = "/search/best-picture/{title}")
    public ResponseEntity<MovieSearchResponse> search(@NotNull @PathVariable String title) {
        Assert.hasText(title, "Movie Title cannot be empty");
        LOGGER.info("MovieController -> Searching Movie : {}", title);
        return ResponseEntity.ok(movieService.searchMovie(title));
    }

    @GetMapping(path = "/list/top-ten")
    public ResponseEntity<List<OmdbResponse>> listTopTenMovies() {
        LOGGER.info("MovieController -> Fetching Top Ten Best Movies on Box Office Value Bases");
        return ResponseEntity.ok(movieService.listTopTenMovies());
    }

    @PostMapping(path = "/rating")
    public ResponseEntity<Void> rateMovie(@Valid @NotNull @RequestBody MovieRatingRequest ratingRequest) throws MovieServiceException {
        LOGGER.info("MovieController -> Adding rating {} for movie {}",ratingRequest.getRating(), ratingRequest.getMovieTitle());
        movieService.rateMovie(ratingRequest.getMovieTitle(), ratingRequest.getUserId(), ratingRequest.getRating());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/rating/{title}")
    public ResponseEntity<MovieRatingSearchResponse> getMovieRating(@NotNull @PathVariable String title) {
        Assert.hasText(title, "Movie Title cannot be empty");
        LOGGER.info("MovieController -> Fetch Rating for movie {}",title);
        return ResponseEntity.ok(movieService.getAverageRating(title));
    }
}
