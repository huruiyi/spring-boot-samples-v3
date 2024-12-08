package com.example.demoQ;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SequenceGenerator {

  private final AtomicInteger counter = new AtomicInteger();
  @Autowired
  @Qualifier("datePrefixGenerator")
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

  public synchronized String getSequence() {
    return prefixGenerator.getPrefix() + initial + counter.getAndIncrement() + suffix;
  }
}
