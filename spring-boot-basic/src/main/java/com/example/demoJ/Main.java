package com.example.demoJ;

import com.example.demoJ.config.SequenceGeneratorConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(SequenceGeneratorConfiguration.class);

    SequenceGenerator generator = context.getBean(SequenceGenerator.class);

    System.out.println(generator.getSequence());
    System.out.println(generator.getSequence());
    System.out.println(generator.getSequence());
    System.out.println(generator.getSequence());
  }
}
