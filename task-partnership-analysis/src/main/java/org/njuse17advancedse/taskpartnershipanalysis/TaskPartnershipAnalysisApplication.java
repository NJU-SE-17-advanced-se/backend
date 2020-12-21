package org.njuse17advancedse.taskpartnershipanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@EnableEurekaClient
//@EnableFeignClients
@SpringBootApplication
public class TaskPartnershipAnalysisApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskPartnershipAnalysisApplication.class, args);
  }
}
