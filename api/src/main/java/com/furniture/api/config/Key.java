package com.furniture.api.config;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Key {

  public static String SECRET_KEY = "7A2B4C6D8E0F1A3B5C7D9E0F2A4B6C8D0E1F3A5B7C9D1E2F4A6B8C0D2E4F6A8B";
  // @Autowired
  // public static void setSecretKey() {
  // SECRET_KEY = Environment.getProperties().getProperty("jwt.secret");
  // }
}
