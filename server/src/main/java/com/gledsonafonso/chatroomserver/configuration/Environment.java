package com.gledsonafonso.chatroomserver.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {
  private static String PROPERTIES_FILEPATH = "src/main/resource/application.properties";
  private Properties properties;

  public Environment() {
    this.properties = new Properties();

    try (var fileInputStream = new FileInputStream(PROPERTIES_FILEPATH)) {
      this.properties.load(fileInputStream);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public String getProperty(String propertyName) {
    return this.properties.getProperty(propertyName);
  }
}
