package com.backbase.movies.entity;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;

@Entity
public class Movie {

    @Id
    private long id;
}
