package com.backbase.movies.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class MovieRatingSearchResponse {
    private long movieId;
    private String movieTitle;
    private double rating;
}
