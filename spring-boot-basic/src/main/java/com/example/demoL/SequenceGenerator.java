package com.example.demoL;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {

  private final AtomicInteger counter = new AtomicInteger();
  private PrefixGenerator prefixGenerator;
  private String suffix;
  private int initial;

  public SequenceGenerator() {
  }

  public SequenceGenerator(PrefixGenerator prefixGenerator, String suffix, int initial) {
    this.prefixGenerator = prefixGenerator;
    this.suffix = suffix;
    this.initial = initial;
  }

  public void setPrefixGenerator(PrefixGenerator prefixGenerator) {
    this.prefixGenerator = prefixGenerator;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public void setInitial(int initial) {
    this.initial = initial;
  }

  public String getSequence() {
    String builder = prefixGenerator.getPrefix() + initial + counter.getAndIncrement() + suffix;
    return builder;
  }
}
