package com.backbase.movies.token;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ApiTokenProvider {

    private String token;

    @PostConstruct
    public void init() {
        this.token = UUID.randomUUID().toString();
        System.out.println("Generated API Token: " + token);
    }

    public String getToken(){
        return token;
    }
}
