package com.example.demoP.config;

import com.example.demoP.SequenceGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SequenceConfiguration {

  @Bean
  public SequenceGenerator sequenceGenerator() {
    SequenceGenerator sequence = new SequenceGenerator();
    sequence.setInitial(100000);
    sequence.setSuffix("A");
    return sequence;
  }
}
