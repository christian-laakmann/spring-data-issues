package com.example;

/**
 * @author novomind AG
 */
public class EmbeddedWithRef extends Embedded {

  private String ref;

  public EmbeddedWithRef(String ref) {
    this.ref = ref;
  }

  public String getRef() {
    return ref;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }
}
