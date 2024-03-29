package com.thealmighty.runningstats.controller;

import com.thealmighty.runningstats.model.AthleteModel;
import com.thealmighty.runningstats.model.SummaryActivityModel;
import com.thealmighty.runningstats.service.StravaService;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("running-stats/api")
@Slf4j
public class MainController {

  private StravaService stravaService;

  @Autowired
  public void setStravaService(StravaService stravaService) {
    this.stravaService = stravaService;
  }

  @GetMapping("athlete")
  public AthleteModel getAthlete() {
    return stravaService.getAthlete();
  }

  @GetMapping("athlete-activities")
  public List<SummaryActivityModel> getAthleteActivities() {
    Date today = new Date();
    return stravaService.getAthleteActivities(today, null, 1, 100);
  }
}
