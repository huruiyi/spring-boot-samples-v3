package com.example.demoR.config;

import com.example.demoR.PrefixGenerator;
import com.example.demoR.SequenceGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(PrefixConfiguration.class)
public class SequenceConfiguration {

  @Value("#{datePrefixGenerator}")
  private PrefixGenerator prefixGenerator;

  @Bean
  public SequenceGenerator sequenceGenerator() {
    SequenceGenerator sequence = new SequenceGenerator();
    sequence.setInitial(100000);
    sequence.setSuffix("A");
    sequence.setPrefixGenerator(prefixGenerator);
    return sequence;
  }
}
