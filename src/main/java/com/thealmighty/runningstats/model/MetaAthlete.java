package com.thealmighty.runningstats.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaAthlete {

  @JsonProperty("id")
  private long id;
}
