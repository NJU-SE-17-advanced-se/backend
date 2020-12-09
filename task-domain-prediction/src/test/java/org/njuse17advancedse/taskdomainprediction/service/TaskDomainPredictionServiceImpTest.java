package org.njuse17advancedse.taskdomainprediction.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskdomainprediction.repository.ResearcherRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class TaskDomainPredictionServiceImpTest {
  @MockBean
  private ResearcherRepository researcherRepository;

  private TaskDomainPredictionService taskDomainPredictionService;

  @BeforeEach
  public void init() {
    taskDomainPredictionService =
      new TaskDomainPredictionServiceImp(researcherRepository);
  }

  @Test
  void getFutureDomains() {
    String testRid = "171250661";
    List<String> pastDomains = Lists.newArrayList("1", "2", "3", "4", "5", "6");
    Mockito
      .when(researcherRepository.containResearcher(testRid))
      .thenReturn(true);
    Mockito
      .when(researcherRepository.getPastDomains(testRid))
      .thenReturn(pastDomains);
    for (String domain : pastDomains) {
      Mockito
        .when(researcherRepository.getDomainImpact(domain))
        .thenReturn(Double.parseDouble(domain));
    }
    List<String> predictDomains = Lists.newArrayList("6", "5", "4", "3", "2");
    assertEquals(
      taskDomainPredictionService.getFutureDomains(testRid),
      predictDomains
    );
  }

  @Test
  void containResearcher() {
    Mockito
      .when(researcherRepository.containResearcher("test"))
      .thenReturn(true);
    assertTrue(taskDomainPredictionService.containResearcher("test"));
  }
}
