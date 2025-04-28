package com.backbase.movies.repository;

import com.backbase.movies.entity.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    List<MovieRating> findByMovieId(long movieId);
}
