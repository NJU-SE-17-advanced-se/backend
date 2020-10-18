package org.njuse17advancedse.taskimpactanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TaskImpactAnalysisApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskImpactAnalysisApplication.class, args);
  }
}
