package com.thealmighty.runningstats.exception.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Fault implements Serializable {

    @JsonProperty("errors")
    private Error errors;

    @JsonProperty("message")
    private String message;
}
