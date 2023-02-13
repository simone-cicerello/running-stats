package com.thealmighty.runningstats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class STokenRespModel extends BaseTokenModel {

    STokenRespModel() {
        super();
    }

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_at")
    private int expiresAt;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("athlete")
    private AthleteModel athleteModel;

    @Override
    @JsonIgnore
    public synchronized Boolean isNotExpired() {
        return LocalDateTime.now(ZoneId.of("UTC")).isBefore(getCreatedOn().plusSeconds(expiresIn));
    }
}
