package com.thealmighty.runningstats.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class StravaConfig {

    @Value("${strava.base-url}")
    private String stravaBaseUrl;

    @Value("${strava.token-uri}")
    private String stravaTokenUri;

    @Value("${strava.client-id}")
    private String stravaClientId;

    @Value("${strava.client-secret}")
    private String stravaClientSecret;

    @Value("${strava.code}")
    private String stravaTempCode;

    @Value("${strava.grant-type-auth}")
    private String stravaGrantTypeAuth;

    @Value("${strava.grant-type-refresh}")
    private String stravaGrantTypeRefresh;

    @Value("${strava.athlete-url}")
    private String stravaAthleteUrl;

    @Value("${strava.athlete-activities-url}")
    private String stravaAthleteActivitiesUrl;
}
