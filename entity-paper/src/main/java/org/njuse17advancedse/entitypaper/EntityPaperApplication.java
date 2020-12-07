package org.njuse17advancedse.entitypaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EntityPaperApplication {

  public static void main(String[] args) {
    SpringApplication.run(EntityPaperApplication.class, args);
  }
}
