package com.backbase.movies.controller;

import com.backbase.movies.entity.Movie;
import com.backbase.movies.entity.Rating;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @GetMapping("/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        return null;
    }

    @PostMapping("/{title}/ratings")
    public ResponseEntity<Rating> addRatingsToMovie(@PathVariable String title) {
        return null;
    }

    @GetMapping("/top-rated")
    public ResponseEntity<Movie> getTop10RatedMovies() {
        return null;
    }
}
