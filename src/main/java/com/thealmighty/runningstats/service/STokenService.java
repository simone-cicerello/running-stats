package com.thealmighty.runningstats.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thealmighty.runningstats.config.AuthConfig;
import com.thealmighty.runningstats.config.StravaConfig;
import com.thealmighty.runningstats.exception.UtilException;
import com.thealmighty.runningstats.model.STokenRespModel;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class STokenService {

  private AuthConfig authConfig;

  private StravaConfig stravaConfig;

  @Autowired
  public void setAuthConfig(AuthConfig authConfig) {
    this.authConfig = authConfig;
  }

  @Autowired
  public void setStravaConfig(StravaConfig stravaConfig) {
    this.stravaConfig = stravaConfig;
  }

  private boolean isTokenExpired(Long expiresAt) {
    return expiresAt <= Instant.now().getEpochSecond();
  }

  public String getStravaToken() {
    if (Strings.isBlank(authConfig.getExpiresAt())
        || isTokenExpired(Long.valueOf(authConfig.getExpiresAt()))) {
      fetchAccessTokenBySecretUnirest();
    }
    return authConfig.getCurrentToken();
  }

  private void fetchAccessTokenBySecretUnirest() {

    try {
      var tokenUri = stravaConfig.getStravaBaseUrl() + "/" + stravaConfig.getTokenUri();
      log.info("STRAVA - Getting token from {}", tokenUri);

      Map<String, String> parameters = new HashMap<>();
      parameters.put("client_id", stravaConfig.getClientId());
      parameters.put("client_secret", stravaConfig.getClientSecret());
      parameters.put("grant_type", stravaConfig.getGrantTypeRefresh());
      parameters.put("refresh_token", authConfig.getRefreshToken());

      String form =
          parameters.entrySet().stream()
              .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
              .collect(Collectors.joining("&"));
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(new URI(tokenUri))
              .headers("Content-Type", "application/x-www-form-urlencoded")
              .POST(HttpRequest.BodyPublishers.ofString(form))
              .build();
      var tokenResponseWrap =
          HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

      var tokenResponse =
          new ObjectMapper().readValue(tokenResponseWrap.body(), STokenRespModel.class);

      if (tokenResponseWrap.statusCode() != 200) {
        throw new UtilException("error in retrieve token");
      } else {
        authConfig.updateApplicationFields(tokenResponse);
      }
    } catch (URISyntaxException | IOException | InterruptedException e) {
      log.error(e.getMessage());
      throw new UtilException(e.getMessage(), e.getCause());
    }
  }
}
