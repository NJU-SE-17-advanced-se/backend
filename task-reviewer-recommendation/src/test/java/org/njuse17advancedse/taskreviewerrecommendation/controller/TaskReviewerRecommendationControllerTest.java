package org.njuse17advancedse.taskreviewerrecommendation.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;
import org.njuse17advancedse.taskreviewerrecommendation.repository.PaperRepository;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class TaskReviewerRecommendationControllerTest {
  @Autowired
  TaskReviewerRecommendationController taskReviewerRecommendationController;

  @MockBean
  PaperRepository paperRepository;

  @MockBean
  TaskReviewerRecommendationService taskReviewerRecommendationService;

  @Test
  void getRecommendReviewer() {
    IPaperUpload testPaper = new IPaperUpload();
    testPaper.setTitle("testPaper");
    testPaper.setPublication("EMSE");
    testPaper.setDate("2020");
    testPaper.setAbs("some word");
    List<String> researchers = Lists.newArrayList("1", "2", "3");
    testPaper.setResearcherIds(researchers);
    List<String> testDomains = Lists.newArrayList("a", "b", "c");
    testPaper.setDomainIds(testDomains);
    List<String> referenceIds = Lists.newArrayList("1", "2", "3");
    testPaper.setReferenceIds(referenceIds);
    Mockito
      .when(taskReviewerRecommendationService.containPublication("EMSE"))
      .thenReturn(false);
    Mockito
      .when(taskReviewerRecommendationService.getRecommendReviewer(testPaper))
      .thenReturn(Lists.newArrayList("a", "b", "c"));
    assertEquals(
      Lists.newArrayList("a", "b", "c"),
      taskReviewerRecommendationController.getRecommendReviewer(testPaper)
    );
  }

  @Test
  void getNotRecommendReviewer() {
    IPaperUpload testPaper = new IPaperUpload();
    testPaper.setTitle("testPaper");
    testPaper.setPublication("EMSE");
    testPaper.setDate("2020");
    testPaper.setAbs("some word");
    List<String> researchers = Lists.newArrayList("1", "2", "3");
    testPaper.setResearcherIds(researchers);
    List<String> testDomains = Lists.newArrayList("a", "b", "c");
    testPaper.setDomainIds(testDomains);
    List<String> referenceIds = Lists.newArrayList("1", "2", "3");
    testPaper.setReferenceIds(referenceIds);
    Mockito
      .when(taskReviewerRecommendationService.containPublication("EMSE"))
      .thenReturn(false);
    Mockito
      .when(
        taskReviewerRecommendationService.getNotRecommendReviewer(testPaper)
      )
      .thenReturn(Lists.newArrayList("a", "b", "c"));
    assertEquals(
      Lists.newArrayList("a", "b", "c"),
      taskReviewerRecommendationController.getNotRecommendReviewer(testPaper)
    );
  }
}
