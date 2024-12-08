package com.example.demoJ;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {

  private final AtomicInteger counter = new AtomicInteger();
  private String prefix;
  private String suffix;
  private int initial;

  public SequenceGenerator() {
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public void setInitial(int initial) {
    this.initial = initial;
  }

  public String getSequence() {
    String builder = prefix +
        initial +
        counter.getAndIncrement() +
        suffix;
    return builder;
  }
}
