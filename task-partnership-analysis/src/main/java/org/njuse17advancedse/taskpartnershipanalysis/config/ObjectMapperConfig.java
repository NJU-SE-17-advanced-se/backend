package org.njuse17advancedse.taskpartnershipanalysis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

@Configuration
public class ObjectMapperConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
      .registerModule(new ProblemModule())
      .registerModule(new ConstraintViolationProblemModule());
  }
}
