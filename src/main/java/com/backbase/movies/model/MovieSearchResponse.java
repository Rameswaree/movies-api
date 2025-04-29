package com.backbase.movies.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private String imdbRating;
    private String boxOffice;
    private String wonOscar;
}
