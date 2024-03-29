package com.thealmighty.runningstats.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolylineMapModel {

  @JsonProperty("id")
  private String id;

  @JsonProperty("polyline")
  private String polyline;

  @JsonProperty("summary_polyline")
  private String summaryPolyline;
}
