package com.backbase.movies.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class MovieSearchResponse {
    private String title;
    private String year;
    private String releaseDate;
    private String writer;
    private String actors;
    private String country;
    private String languages;
    private String type;
    private String omdbRating;
    private String boxOffice;
    private String wonOscar;
}
