package org.njuse17advancedse.taskpartnershipanalysis.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.repository.ResearcherRepository;
import org.njuse17advancedse.taskpartnershipanalysis.service.TaskPartnershipAnalysisService;
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
  TaskPartnershipAnalysisService taskPartnershipAnalysisService;

  @Test
  void getPartners() {
    Mockito
      .when(taskPartnershipAnalysisService.containResearcher("test"))
      .thenReturn(true);
    Mockito
      .when(taskPartnershipAnalysisService.getPartners("test"))
      .thenReturn(Lists.newArrayList("a", "b", "c"));
    assertEquals(
      Lists.newArrayList("a", "b", "c"),
      researcherController.getPartners("test")
    );
  }

  @Test
  void getPartnership() {
    IResearcherNet iResearcherNet = new IResearcherNet();
    iResearcherNet.setPartners(Lists.newArrayList("0", "1", "2", "3"));
    iResearcherNet.setWeight(
      Lists.newArrayList(
        new Double[] { 0.25, 1.0 },
        new Double[] { 0.5, 0.75 },
        new Double[] { 0.75, 0.5 },
        new Double[] { 1.0, 0.25 }
      )
    );
    Mockito
      .when(taskPartnershipAnalysisService.containResearcher("test"))
      .thenReturn(true);
    Mockito
      .when(taskPartnershipAnalysisService.getPartnership("test", 2018, 2020))
      .thenReturn(iResearcherNet);
    assertEquals(
      iResearcherNet,
      researcherController.getPartnership("test", "2018", "2020")
    );
  }

  @Test
  void getPotentialPartners() {
    HashMap<String, Double> result = new HashMap<>();
    List<Double> values = Lists.newArrayList(
      1.58,
      1.58,
      1.58,
      1.58,
      1.58,
      1.58,
      1.58,
      1.58
    );
    for (int i = 0; i < 8; i++) {
      result.put(i + "", values.get(i));
    }
    Mockito
      .when(taskPartnershipAnalysisService.containResearcher("test"))
      .thenReturn(true);
    Mockito
      .when(taskPartnershipAnalysisService.getPotentialPartners("test"))
      .thenReturn(result);
    assertEquals(result, researcherController.getPotentialPartners("test"));
  }
}
