package com.thealmighty.runningstats.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thealmighty.runningstats.config.StravaConfig;
import com.thealmighty.runningstats.exception.ServiceException;
import com.thealmighty.runningstats.model.AthleteModel;
import com.thealmighty.runningstats.model.SummaryActivityModel;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StravaService {

  @Autowired private StravaConfig stravaConfig;
  @Autowired private STokenService sTokenService;

  public AthleteModel getAthlete() {
    try {
      var url = stravaConfig.getStravaBaseUrl() + "/" + stravaConfig.getStravaAthleteUrl();
      log.info("STRAVA - calling {}", url);

      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(new URI(url))
              .header("Authorization", "Bearer " + sTokenService.getStravaToken())
              .GET()
              .build();
      var athleteResponseWrap =
          HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

      return new ObjectMapper().readValue(athleteResponseWrap.body(), AthleteModel.class);
    } catch (URISyntaxException | IOException | InterruptedException e) {
      log.error(e.getMessage());
      throw new ServiceException(e.getMessage(), e.getCause());
    }
  }

  public List<SummaryActivityModel> getAthleteActivities(
      Date before, Date after, Integer page, Integer perPage) {
    try {
      var url =
          stravaConfig.getStravaBaseUrl() + "/" + stravaConfig.getStravaAthleteActivitiesUrl();
      log.info("STRAVA - calling {}", url);

      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(
                  new URIBuilder(url)
                      .addParameter("before", String.valueOf(before))
                      .addParameter("after", String.valueOf(after))
                      .addParameter("page", String.valueOf(page))
                      .addParameter("perPage", String.valueOf(perPage))
                      .build())
              .header("Authorization", "Bearer " + sTokenService.getStravaToken())
              .GET()
              .build();

      var athleteActivitiesResponseWrap =
          HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

      return new ObjectMapper()
          .readValue(
              athleteActivitiesResponseWrap.body(),
              new TypeReference<List<SummaryActivityModel>>() {});
    } catch (URISyntaxException | IOException | InterruptedException e) {
      log.error(e.getMessage());
      throw new ServiceException(e.getMessage(), e.getCause());
    }
  }
}
