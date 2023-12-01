package com.thealmighty.runningstats.exception.model;

import java.util.List;
import lombok.Data;

@Data
public class Fault {
  private List<Errors> errors;
  private String message;
}
