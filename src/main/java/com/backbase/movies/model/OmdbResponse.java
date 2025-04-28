package com.backbase.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbResponse {
    private String title;
    private String year;
    private String released;
    private String writer;
    private String actors;
    private String awards;
    private String country;
    private String language;
    private String type;
    private String imdbRating;
    private String boxOffice;
    private double rating;
}
