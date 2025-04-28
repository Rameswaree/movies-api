package com.backbase.movies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MovieRatingRequest {
    @NotNull
    private String movieTitle;
    @NotNull
    private String userId;
    @NotNull
    @Min(value = 1, message = "Movie rating should not be less than 1")
    @Max(value = 10, message = "Movie rating should not be greater than 10")
    private int rating;
}

