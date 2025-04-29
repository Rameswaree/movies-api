package com.backbase.movies.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Entity class for movie_ratings table
 */
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"movie_id", "userId"})
})
public class MovieRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private AcademyAwards movie;
}