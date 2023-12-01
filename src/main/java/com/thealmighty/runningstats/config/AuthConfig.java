package com.thealmighty.runningstats.config;

import com.thealmighty.runningstats.exception.UtilException;
import com.thealmighty.runningstats.model.STokenRespModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.DefaultPropertiesPersister;

@Configuration
@Getter
@PropertySource("classpath:auth.properties")
public class AuthConfig {

  private static final String CURRENT_TOKEN_KEY = "currentToken";
  private static final String REFRESH_TOKEN_KEY = "refreshToken";
  private static final String EXPIRES_AT_KEY = "expiresAt";
  private static final String EXPIRES_IN_KEY = "expiresIn";

  @Value("${refreshToken}")
  private String refreshToken;

  @Value("${currentToken}")
  private String currentToken;

  @Value("${expiresAt}")
  private String expiresAt;

  @Value("${expiresIn}")
  private String expiresIn;

  public void updateApplicationFields(STokenRespModel newTokenStuffs) {
    try {
      Properties props = new Properties();
      props.setProperty(CURRENT_TOKEN_KEY, newTokenStuffs.getAccessToken());
      props.setProperty(REFRESH_TOKEN_KEY, newTokenStuffs.getRefreshToken());
      props.setProperty(EXPIRES_AT_KEY, String.valueOf(newTokenStuffs.getExpiresAt()));
      props.setProperty(EXPIRES_IN_KEY, String.valueOf(newTokenStuffs.getExpiresIn()));
      File f = new File("src/main/resources/auth.properties");
      OutputStream out = new FileOutputStream(f);
      DefaultPropertiesPersister p = new DefaultPropertiesPersister();
      p.store(props, out, "");
    } catch (Exception e ) {
      throw new UtilException(e.getMessage());
    }
  }
}

