package com.backbase.movies.repository;

import com.backbase.movies.entity.AcademyAwards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademyAwardsRepository extends JpaRepository<AcademyAwards, Long> {
    Optional<AcademyAwards> findByNomineeAndCategory(String title, String category);
    List<AcademyAwards> findAllByCategory(String category);
}
