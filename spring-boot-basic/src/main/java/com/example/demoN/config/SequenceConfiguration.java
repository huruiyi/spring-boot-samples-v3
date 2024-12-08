package com.example.demoN.config;

import com.example.demoN.DatePrefixGenerator;
import com.example.demoN.NumberPrefixGenerator;
import com.example.demoN.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SequenceConfiguration {

  @Bean
  public DatePrefixGenerator datePrefixGenerator() {
    DatePrefixGenerator dpg = new DatePrefixGenerator();
    dpg.setPattern("yyyyMMdd");
    return dpg;
  }

  @Bean
  public NumberPrefixGenerator numberPrefixGenerator() {
    return new NumberPrefixGenerator();
  }

  @Bean
  public SequenceGenerator sequenceGenerator() {
    SequenceGenerator sequence = new SequenceGenerator();
    sequence.setInitial(100000);
    sequence.setSuffix("A");
    return sequence;
  }
}
