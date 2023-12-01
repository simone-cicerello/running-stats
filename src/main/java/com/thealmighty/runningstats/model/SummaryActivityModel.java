package com.thealmighty.runningstats.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummaryActivityModel {

  @JsonProperty("id")
  private long id;

  @JsonProperty("external_id")
  private String externalId;

  @JsonProperty("upload_id")
  private long uploadId;

  @JsonProperty("athlete")
  private MetaAthlete athlete;

  @JsonProperty("name")
  private String name;

  @JsonProperty("distance")
  private float distance;

  @JsonProperty("moving_time")
  private int movingTime;

  @JsonProperty("elapsed_time")
  private int elapsedTime;

  @JsonProperty("total_elevation_gain")
  private float totalElevationGain;

  @JsonProperty("elev_high")
  private float elevHigh;

  @JsonProperty("elev_low")
  private float elevLow;

  @JsonProperty("sport_type")
  // TODO as written in documentation this is an Enum type
  private String sportType;

  @JsonProperty("start_date")
  private Date startDate;

  @JsonProperty("start_date_local")
  private Date startDateLocal;

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("start_latlng")
  // TODO as written in documentation this is an array of float element
  private Object startLatlng;

  @JsonProperty("end_latlng")
  // TODO as written in documentation this is an array of float element
  private Object endLatlng;

  @JsonProperty("achievement_count")
  private int achievementCount;

  @JsonProperty("kudos_count")
  private int kudosCount;

  @JsonProperty("comment_count")
  private int commentCount;

  @JsonProperty("athlete_count")
  private int athleteCount;

  @JsonProperty("photo_count")
  private int photoCount;

  @JsonProperty("total_photo_count")
  private int totalPhotoCount;

  @JsonProperty("map")
  private PolylineMapModel map;

  @JsonProperty("trainer")
  private boolean trainer;

  @JsonProperty("commute")
  private boolean commute;

  @JsonProperty("manual")
  private boolean manual;

  @JsonProperty("private")
  private boolean privateField;

  @JsonProperty("flagged")
  private boolean flagged;

  @JsonProperty("workout_type")
  private int workoutType;

  @JsonProperty("upload_id_str")
  private String uploadIdStr;

  @JsonProperty("average_speed")
  private float averageSpeed;

  @JsonProperty("max_speed")
  private int maxSpeed;

  @JsonProperty("has_kudoed")
  private boolean hasKudoed;

  @JsonProperty("hide_from_home")
  private boolean hideFromHome;

  @JsonProperty("gear_id")
  private String gearId;

  @JsonProperty("kilojoules")
  private float kilojoules;

  @JsonProperty("average_watts")
  private float averageWatts;

  @JsonProperty("device_watts")
  private boolean deviceWatts;

  @JsonProperty("weighted_average_watts")
  private int weightedAverageWatts;
}
