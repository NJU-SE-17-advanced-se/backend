package org.njuse17advancedse.entityresearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@EnableEurekaClient
@SpringBootApplication
@EnableAsync
public class EntityResearcherApplication {

  public static void main(String[] args) {
    SpringApplication.run(EntityResearcherApplication.class, args);
  }
}
