package com.backbase.movies.controller;

import com.backbase.movies.dto.RatingRequest;
import com.backbase.movies.entity.Movie;
import com.backbase.movies.exception.MovieNotFoundException;
import com.backbase.movies.service.MovieService;
import com.backbase.movies.token.ApiTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ApiTokenProvider tokenProvider;

    @Autowired
    public MovieController(MovieService movieService, ApiTokenProvider tokenProvider){
        this.movieService = movieService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/{title}")
    public ResponseEntity<Boolean> getMovieByTitle(@PathVariable String title, @RequestHeader("Authorization") String token) {
        validateToken(token);
        boolean isBestPicture = movieService.hasWonBestPicture(title);
        return ResponseEntity.ok(isBestPicture);
    }

    @PostMapping("/{title}/ratings")
    public ResponseEntity<Void> addRatingsToMovie(@PathVariable String title, @RequestBody @Valid RatingRequest ratingRequest, @RequestHeader("Authorization") String token) throws MovieNotFoundException {
        validateToken(token);
        movieService.addRatings(title, ratingRequest.getScore());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Movie>> getTop10RatedMovies(@RequestHeader("Authorization") String token) {
        validateToken(token);
        List<Movie> top10Movies = movieService.getTop10Movies();
        return ResponseEntity.ok(top10Movies);
    }

    private void validateToken(String token) {
        if (token == null || !token.equals("Bearer " + tokenProvider.getToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }
}
