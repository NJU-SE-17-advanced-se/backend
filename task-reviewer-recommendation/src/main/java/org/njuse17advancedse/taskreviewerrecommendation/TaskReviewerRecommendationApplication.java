package org.njuse17advancedse.taskreviewerrecommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TaskReviewerRecommendationApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskReviewerRecommendationApplication.class, args);
  }
}
