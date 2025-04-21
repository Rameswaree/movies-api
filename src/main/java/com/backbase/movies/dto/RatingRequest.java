package com.backbase.movies.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class RatingRequest {

    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 10, message = "Score must be at most 10")
    private int score;

    public int getScore() {
        return score;
    }
}
