package com.thealmighty.runningstats.exception.model;

import lombok.*;

@Data
public class Errors {
  private String resource;
  private String field;
  private String code;
}
