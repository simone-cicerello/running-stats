package com.thealmighty.runningstats.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thealmighty.runningstats.config.AuthConfig;
import com.thealmighty.runningstats.config.StravaConfig;
import com.thealmighty.runningstats.exception.UtilException;
import com.thealmighty.runningstats.model.STokenRespModel;
import com.thealmighty.runningstats.service.util.ServiceUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class STokenService {

  private AuthConfig authConfig;

  private StravaConfig stravaConfig;

  private ServiceUtils serviceUtils;

  @Autowired
  public void setAuthConfig(AuthConfig authConfig) {
    this.authConfig = authConfig;
  }

  @Autowired
  public void setStravaConfig(StravaConfig stravaConfig) {
    this.stravaConfig = stravaConfig;
  }

  @Autowired
  public void setServiceUtils(ServiceUtils serviceUtils) {
    this.serviceUtils = serviceUtils;
  }

  public String getStravaToken() {
    log.debug("Checking for expiration date...");
    if (Strings.isBlank(authConfig.getExpiresAt())
        || serviceUtils.isTokenExpired(Long.valueOf(authConfig.getExpiresAt()))) {
      log.debug(
          "Expiration date is null or {}",
          authConfig.getExpiresAt() != null
              ? new Timestamp(Long.parseLong(authConfig.getExpiresAt()))
              : "");
      log.debug("Needed new token...");
      fetchAccessTokenBySecretUnirest();
    }
    log.debug("Getting the existing token {}", authConfig.getCurrentToken());
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

  @PostConstruct
  private void infoToken(){
    Instant instant = Instant.ofEpochSecond(Long.parseLong(authConfig.getExpiresAt()));
    var expireDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    log.info("Existing token expires on {}", serviceUtils.formatDate(expireDate));
  }
}
