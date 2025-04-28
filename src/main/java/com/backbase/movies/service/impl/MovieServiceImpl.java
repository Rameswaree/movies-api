package com.backbase.movies.service.impl;

import com.backbase.movies.entity.AcademyAwards;
import com.backbase.movies.entity.MovieRating;
import com.backbase.movies.exception.MovieServiceException;
import com.backbase.movies.model.MovieRatingSearchResponse;
import com.backbase.movies.model.MovieSearchResponse;
import com.backbase.movies.model.OmdbResponse;
import com.backbase.movies.remote.client.OmdbClient;
import com.backbase.movies.repository.AcademyAwardsRepository;
import com.backbase.movies.repository.MovieRatingRepository;
import com.backbase.movies.service.MovieService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);
    private final static String BEST_PICTURE = "Best Picture";
    private static final String TITLE_MESSAGE = "Unable to find movie with provided title";

    private OmdbClient omdbClient;
    private AcademyAwardsRepository academyAwardsRepository;
    private MovieRatingRepository ratingRepository;

    @Override
    public MovieSearchResponse searchMovie(final String title) {
        Assert.hasText(title, "Movie title can't be empty.");
        OmdbResponse response = omdbClient.search(title);
        if(response!=null && response.getTitle() != null) {
            AcademyAwards academyAwards = academyAwardsRepository.findByNomineeAndCategory(title, BEST_PICTURE).orElseThrow(() -> new ResourceNotFoundException(TITLE_MESSAGE));
            return MovieSearchResponse.builder()
                    .title(response.getTitle())
                    .type(response.getType())
                    .year(response.getYear())
                    .actors(response.getActors())
                    .country(response.getCountry())
                    .omdbRating(response.getOmdbRating())
                    .boxOffice(response.getBoxOffice())
                    .languages(response.getLanguage())
                    .wonOscar(academyAwards.getWon())
                    .releaseDate(response.getReleased())
                    .writer(response.getWriter())
                    .build();
        } else {
            throw new ResourceNotFoundException("Unable to find movie with provided title in OMDB");
        }
    }

    @Override
    @Transactional
    public List<OmdbResponse> listTopTenMovies() {
        List<AcademyAwards> bestMovies = academyAwardsRepository.findAllByCategory(BEST_PICTURE);
        LOGGER.info("Best Movies List Size : {}", bestMovies.size());
        List<OmdbResponse> omdbMoviesDetail = new ArrayList<>();
        bestMovies.forEach(bestMovie -> {
            final OmdbResponse omdbResponse = omdbClient.search(bestMovie.getNominee());
            if(omdbResponse!=null) {
                if(!bestMovie.getMovieRatings().isEmpty()) {
                    omdbResponse.setRating(calculateRatingAverage(bestMovie.getMovieRatings()));
                }
                omdbMoviesDetail.add(omdbResponse);
            }
        });
        if(!CollectionUtils.isEmpty(omdbMoviesDetail)) {
            return omdbMoviesDetail.stream()
                    .sorted(Comparator.comparingLong(this::parseBoxOfficeValue).reversed())
                    .limit(10)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @Transactional
    public void rateMovie(final String movieTitle, final String userId, final int rating) throws MovieServiceException {
        Assert.hasText(movieTitle, "Movie title cannot be empty.");
        Assert.hasText(userId, "UserId cannot be empty.");

        final AcademyAwards academyAwards = academyAwardsRepository.findByNomineeAndCategory(movieTitle, BEST_PICTURE).orElseThrow(() -> new ResourceNotFoundException(TITLE_MESSAGE));

        final MovieRating movieRating = MovieRating.builder()
                .rating(rating)
                .movie(academyAwards)
                .userId(userId)
                .build();
        try {
            ratingRepository.save(movieRating);
            LOGGER.info("Movie {},  Rating is added successfully", movieTitle);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Failed to add rating for the movie {} by the user {}", movieTitle, userId);
            throw new MovieServiceException(String.format("Rating is already provided by the user : %s", userId));
        }
    }

    public MovieRatingSearchResponse getAverageRating(final String movieTitle) {
        Assert.hasText(movieTitle, "Movie title cannot be empty.");
        final AcademyAwards academyAwards = academyAwardsRepository.findByNomineeAndCategory(movieTitle, BEST_PICTURE).orElseThrow(() -> new ResourceNotFoundException(TITLE_MESSAGE));
        final List<MovieRating> ratings = ratingRepository.findByMovieId(academyAwards.getId());

        return MovieRatingSearchResponse.builder()
                .movieId(academyAwards.getId())
                .movieTitle(academyAwards.getNominee())
                .rating(calculateRatingAverage(ratings))
                .build();
    }

    private double calculateRatingAverage(List<MovieRating> ratings){
        return ratings.stream()
                .mapToDouble(MovieRating::getRating)
                .average()
                .orElse(0.0);
    }

    private long parseBoxOfficeValue(OmdbResponse movie) {
        final String boxOfficeValue = movie.getBoxOffice();
        if (boxOfficeValue == null || boxOfficeValue.isEmpty() || boxOfficeValue.equalsIgnoreCase("N/A")) {
            return 0;
        }
        try {
            // Remove $ and commas using NumberFormat
            final Number number = NumberFormat.getCurrencyInstance(Locale.US).parse(boxOfficeValue);
            return number.longValue();
        } catch (ParseException e) {
            LOGGER.error("Error while parsing the box office value {}", boxOfficeValue);
            return 0;
        }
    }
}
