package com.thealmighty.runningstats.service.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServiceUtils {

  private static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

  public boolean isTokenExpired(Long expiresAt) {
    var now = LocalDateTime.now();
    Instant instant = Instant.ofEpochSecond(expiresAt);
    var expireDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    log.debug("Expiration date: {} VS Now: {}", formatDate(expireDate), formatDate(now));
    return expireDate.isBefore(now) || expireDate.isEqual(now);
  }

  public String formatDate(LocalDateTime dateToFormat) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
    return dtf.format(dateToFormat);
  }
}
