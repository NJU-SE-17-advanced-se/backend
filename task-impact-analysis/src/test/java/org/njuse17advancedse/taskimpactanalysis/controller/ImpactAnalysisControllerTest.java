package org.njuse17advancedse.taskimpactanalysis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskimpactanalysis.data.AllRepository;
import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class ImpactAnalysisControllerTest {
  @MockBean
  TaskImpactAnalysisService service;

  @MockBean
  AllRepository repository;

  @Autowired
  TaskImpactAnalysisController controller;

  @Autowired
  PaperController paperController;

  @Autowired
  ResearcherController researcherController;

  @BeforeEach
  @Test
  void testGetHIndex1() {
    Mockito.when(service.getHIndex("researcher")).thenReturn(4);
    assertEquals(4, controller.getHIndex("researcher", "hIndex"));
    assertEquals(4, researcherController.getHIndex("researcher", "hIndex"));

    Mockito.when(service.getHIndex("not exist")).thenReturn(-1);
    try {
      controller.getHIndex("not exist", "hIndex");
    } catch (Exception ignored) {}

    try {
      researcherController.getHIndex("not exist", "hIndex");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetPaperImpact() {
    Mockito.when(service.getPaperImpact("SKT")).thenReturn(12d);
    assertEquals(12d, controller.getPaperImpact("SKT"));
    assertEquals(12d, paperController.getPaperImpact("SKT"));

    Mockito.when(service.getPaperImpact("not exist")).thenReturn(-1d);
    try {
      controller.getPaperImpact("not exist");
    } catch (Exception ignored) {}

    try {
      paperController.getPaperImpact("not exist");
    } catch (Exception ignored) {}
  }
}
