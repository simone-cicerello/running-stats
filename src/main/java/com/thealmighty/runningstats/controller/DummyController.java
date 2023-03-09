package com.thealmighty.runningstats.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Objects;

@RestController
@RequestMapping("running-stats/api/dummy")
@Slf4j
public class DummyController {

    @GetMapping("greetings")
    public String greetings() {
        return "Greetings from running-stats!";
    }

    @RequestMapping(value = "echo",
            method = {RequestMethod.GET, RequestMethod.POST},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Serializable> echo(HttpServletRequest httpServletRequest,
                                             @RequestBody(required = false) Serializable rb) {

        log.debug("Method --> {}", httpServletRequest.getMethod());
        log.debug("Request URI --> {}", httpServletRequest.getRequestURI());
        log.debug("Context Path --> {}", httpServletRequest.getContextPath());

        if (Objects.nonNull(rb))
            return new ResponseEntity<>(rb, HttpStatus.OK);

        return new ResponseEntity<>("Echo Call -> No Body", HttpStatus.OK);
    }
}
