package com.thealmighty.runningstats.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class STokenRespModel {

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("expires_at")
  private long expiresAt;

  @JsonProperty("expires_in")
  private long expiresIn;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("athlete")
  private AthleteModel athleteModel;
}
