package com.backbase.movies.component;

import com.backbase.movies.entity.AcademyAwards;
import com.backbase.movies.repository.AcademyAwardsRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads Academy Awards data from a CSV file to the database on application startup.
 */
@Component
@AllArgsConstructor
public class AcademyAwardsDataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcademyAwardsDataLoader.class);

    private final AcademyAwardsRepository repository;

    @PostConstruct
    public void loadCSV() {
        try {
            final InputStream inputStream = new ClassPathResource("academy_awards.csv").getInputStream();
            final CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withQuoteChar('\"')
                    .build();
            final CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                    .withCSVParser(parser)
                    .withSkipLines(1)          // Skip header line
                    .build();

            List<AcademyAwards> academyAwards = new ArrayList<>();
            for (String[] record : reader.readAll()) {
                final String year = (record[0].split(" ")[0]).split("/")[0];
                final AcademyAwards result = AcademyAwards.builder()
                        .releaseYear(year)
                        .category(record[1])
                        .nominee(record[2])
                        .additionalInfo(record[3])
                        .won(record[4])
                        .build();
                academyAwards.add(result);
            }
            if(!CollectionUtils.isEmpty(academyAwards)) {
                repository.saveAll(academyAwards);
            }

        } catch (Exception e) {
            LOGGER.error("Unable to load csv file data : {}", e.getMessage());
        }
    }
}
