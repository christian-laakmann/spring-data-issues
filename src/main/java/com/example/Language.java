package com.example;

import org.springframework.data.annotation.Id;

/**
 * @author novomind AG
 */
public class Language {

  @Id
  private String id;

  private String locale;

  public Language(String id, String locale) {
    this.id = id;
    this.locale = locale;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }
}
