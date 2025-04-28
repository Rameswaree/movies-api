package com.backbase.movies.remote.client;

import com.backbase.movies.model.OmdbResponse;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Component
@Getter
@Setter
public class OmdbClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmdbClient.class);

    @Value("${omdb.api.key}")
    private String omdbApiKey;
    @Value("${omdb.api.url}")
    private String omdbApiUrl;
    @Autowired
    private RestTemplate restTemplate;

    public OmdbResponse search(final String title) {
        Assert.hasText(title, "Movie title can't be empty.");
        LOGGER.info("Calling OMDB API to fetch movie details : {}", title);
        OmdbResponse response = restTemplate.getForObject(String.format(omdbApiUrl, omdbApiKey, title), OmdbResponse.class);
        return response;

    }
}
