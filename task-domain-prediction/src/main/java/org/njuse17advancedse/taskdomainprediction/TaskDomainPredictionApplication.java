package org.njuse17advancedse.taskdomainprediction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TaskDomainPredictionApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskDomainPredictionApplication.class, args);
  }
}
