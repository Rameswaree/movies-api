package com.backbase.movies.service;

import com.backbase.movies.dto.AcademyAwards;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AcademyAwardsService {

    private List<AcademyAwards> bestPictureMovies = new ArrayList<>();

    public List<AcademyAwards> getBestPictureMovies() {
        return bestPictureMovies;
    }
}
