package org.njuse17advancedse.taskcitationanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@EnableEurekaClient
//@EnableFeignClients
@SpringBootApplication
public class TaskCitationAnalysisApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskCitationAnalysisApplication.class, args);
  }
}
