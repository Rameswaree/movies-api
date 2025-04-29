package com.backbase.movies.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for AcademyAwards table
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AcademyAwards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String releaseYear;
    private String category;
    @Column(length = 1000)
    private String nominee;
    @Column(length = 1000)
    private String additionalInfo;
    private String won;
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<MovieRating> movieRatings;
}

