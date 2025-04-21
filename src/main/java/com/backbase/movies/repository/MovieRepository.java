package com.backbase.movies.repository;

import com.backbase.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findTop10ByOrderByBoxOfficeDesc();

    Optional<Movie> findByTitleIgnoreCase(String title);
}
