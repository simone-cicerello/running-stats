package com.thealmighty.runningstats.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class StravaConfig {

  // URIs
  @Value("${strava.baseUrl}")
  private String stravaBaseUrl;

  @Value("${strava.athleteUrl}")
  private String stravaAthleteUrl;

  @Value("${strava.athleteActivitiesUrl}")
  private String stravaAthleteActivitiesUrl;

  @Value("${strava.tokenUri}")
  private String tokenUri;

  // Auth stuffs
  @Value("${clientId}")
  private String clientId;

  @Value("${clientSecret}")
  private String clientSecret;

  @Value("${grantTypeRefresh}")
  private String grantTypeRefresh;
}
