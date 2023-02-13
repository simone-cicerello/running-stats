package com.thealmighty.runningstats.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AthleteModel implements Serializable {

	@JsonProperty("country")
	private String country;

	@JsonProperty("profile_medium")
	private String profileMedium;

	@JsonProperty("firstname")
	private String firstname;

	@JsonProperty("follower")
	private Object follower;

	@JsonProperty("city")
	private String city;

	@JsonProperty("resource_state")
	private int resourceState;

	@JsonProperty("sex")
	private String sex;

	@JsonProperty("profile")
	private String profile;

	@JsonProperty("bio")
	private String bio;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("weight")
	private Object weight;

	@JsonProperty("summit")
	private boolean summit;

	@JsonProperty("lastname")
	private String lastname;

	@JsonProperty("premium")
	private boolean premium;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("badge_type_id")
	private int badgeTypeId;

	@JsonProperty("friend")
	private Object friend;

	@JsonProperty("id")
	private int id;

	@JsonProperty("state")
	private String state;

	@JsonProperty("username")
	private String username;
}