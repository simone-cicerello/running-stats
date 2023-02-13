package com.thealmighty.runningstats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseTokenModel implements Serializable {

    @JsonIgnore
    private LocalDateTime createdOn;

    protected BaseTokenModel() {
        createdOn = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public abstract Boolean isNotExpired();
}
