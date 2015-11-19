package com.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author novomind AG
 */
@Document
public class Master {

  @Id
  private String id;

  private List<Embedded> sub;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Embedded> getSub() {
    return sub;
  }

  public void setSub(List<Embedded> sub) {
    this.sub = sub;
  }
}
