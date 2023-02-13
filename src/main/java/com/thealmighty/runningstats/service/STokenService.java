package com.thealmighty.runningstats.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thealmighty.runningstats.config.StravaConfig;
import com.thealmighty.runningstats.exception.UtilException;
import com.thealmighty.runningstats.model.STokenRespModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
@Slf4j
public class STokenService {

    @Autowired
    private StravaConfig stravaConfig;

    private final AtomicReference<String> tokenString = new AtomicReference<>();
    private final AtomicReference<String> refreshTokenString = new AtomicReference<>();

    private Optional<STokenRespModel> optionalStravaTokenModel;

    @Autowired
    public STokenService() {
        cleanToken();
    }

    public void cleanToken() {
        optionalStravaTokenModel = Optional.empty();
    }


    public String getStravaToken(Boolean forceNewOne) throws UtilException {
        optionalStravaTokenModel.ifPresentOrElse(
                sTokenRespModel -> {
                    if (Boolean.TRUE.equals(sTokenRespModel.isNotExpired()) && Boolean.FALSE.equals(forceNewOne)) {
                        tokenString.set(sTokenRespModel.getAccessToken());
                        refreshTokenString.set(sTokenRespModel.getRefreshToken());
                    } else {
                        fetchAccessTokenBySecretUnirest(stravaConfig.getStravaClientId(),
                                stravaConfig.getStravaClientSecret(),
                                stravaConfig.getStravaBaseUrl(),
                                stravaConfig.getStravaTokenUri(),
                                true,
                                refreshTokenString.get());
                        tokenString.set(optionalStravaTokenModel.orElseThrow().getAccessToken());
                        refreshTokenString.set(optionalStravaTokenModel.orElseThrow().getRefreshToken());
                    }
                },
                () -> {
                    fetchAccessTokenBySecretUnirest(stravaConfig.getStravaClientId(),
                            stravaConfig.getStravaClientSecret(),
                            stravaConfig.getStravaBaseUrl(),
                            stravaConfig.getStravaTokenUri(),
                            false,
                            refreshTokenString.get());
                    tokenString.set(optionalStravaTokenModel.orElseThrow().getAccessToken());
                    refreshTokenString.set(optionalStravaTokenModel.orElseThrow().getRefreshToken());
                });
        return tokenString.get();
    }


    private void fetchAccessTokenBySecretUnirest(String stravaClientId,
                                                 String stravaClientSecret,
                                                 String stravaBaseUrl,
                                                 String stravaTokenUri,
                                                 Boolean refreshFlag,
                                                 String refreshToken) {

        try {
            var tokenUri = stravaBaseUrl + "/" + stravaTokenUri;
            log.info("STRAVA - Getting token from {}", tokenUri);

            //first call this url, give browser authorization and put the code in the "CODE" headers parameter
            //http://www.strava.com/oauth/authorize?client_id=63357&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=read
            //TODO make automatic this procedure

            //TODO refactor this condition, factorize the common attributes
//            if (!refreshFlag) {
//                tokenResponse = Unirest.post(tokenUri)
//                        .header("Content-Type", "application/x-www-form-urlencoded")
//                        .field("client_id", stravaClientId)
//                        .field("client_secret", stravaClientSecret)
//                        .field("code", stravaConfig.getStravaTempCode())
//                        .field("grant_type", stravaConfig.getStravaGrantTypeAuth())
//                        .asObject(STokenRespModel.class);
//            } else {
//                tokenResponse = Unirest.post(tokenUri)
//                        .header("Content-Type", "application/x-www-form-urlencoded")
//                        .field("client_id", stravaClientId)
//                        .field("client_secret", stravaClientSecret)
//                        .field("grant_type", stravaConfig.getStravaGrantTypeRefresh())
//                        .field("refresh_token", refreshToken)
//                        .asObject(STokenRespModel.class);
//            }

            Map<String, String> parameters = new HashMap<>();
            parameters.put("client_id", stravaClientId);
            parameters.put("client_secret", stravaClientSecret);
            if (!refreshFlag) {
                parameters.put("code", stravaConfig.getStravaTempCode());
                parameters.put("grant_type", stravaConfig.getStravaGrantTypeAuth());
            } else {
                parameters.put("grant_type", stravaConfig.getStravaGrantTypeRefresh());
                parameters.put("refresh_token", refreshToken);
            }
            String form = parameters.entrySet()
                    .stream()
                    .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(tokenUri))
                    .headers("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();
            var tokenResponseWrap = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            var tokenResponse = new ObjectMapper().readValue(tokenResponseWrap.body(), STokenRespModel.class);

            if (tokenResponseWrap.statusCode() != 200) {
                optionalStravaTokenModel = Optional.empty();
            } else {
                optionalStravaTokenModel = Optional.ofNullable(tokenResponse);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage());
            throw new UtilException(e.getMessage(),
                    e.getCause(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName());
        }
    }
}
