package org.njuse17advancedse.taskdomainprediction.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskdomainprediction.repository.ResearcherRepository;
import org.njuse17advancedse.taskdomainprediction.service.TaskDomainPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class ResearcherControllerTest {
  @Autowired
  ResearcherController researcherController;

  @MockBean
  ResearcherRepository researcherRepository;

  @MockBean
  TaskDomainPredictionService taskDomainPredictionService;

  @Test
  void getFutureDomains() {
    List<String> predictDomains = Lists.newArrayList("6", "5", "4", "3", "2");
    Mockito
      .when(taskDomainPredictionService.containResearcher("test"))
      .thenReturn(true);
    Mockito
      .when(taskDomainPredictionService.getFutureDomains("test"))
      .thenReturn(predictDomains);
    assertEquals(predictDomains, researcherController.getFutureDomains("test"));
  }
}
