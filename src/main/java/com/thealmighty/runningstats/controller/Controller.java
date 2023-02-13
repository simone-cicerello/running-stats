package com.thealmighty.runningstats.controller;

import com.thealmighty.runningstats.model.AthleteModel;
import com.thealmighty.runningstats.model.SummaryActivityModel;
import com.thealmighty.runningstats.service.StravaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("running-stats/api")
@Slf4j
public class Controller {

    @Autowired
    private StravaService stravaService;

    @GetMapping("athlete")
    public AthleteModel getAthlete() {
        return stravaService.getAthlete();
    }

    @GetMapping("athlete-activities")
    public List<SummaryActivityModel> getAthleteActivities() {
        Date today = new Date();
        return stravaService.getAthleteActivities(today, null, 1, 100 );
    }
}
