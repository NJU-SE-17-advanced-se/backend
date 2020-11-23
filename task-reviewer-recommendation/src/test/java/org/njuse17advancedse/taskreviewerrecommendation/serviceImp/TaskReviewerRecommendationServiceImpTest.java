package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;
import org.njuse17advancedse.taskreviewerrecommendation.repository.PaperRepository;
import org.njuse17advancedse.taskreviewerrecommendation.service.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class TaskReviewerRecommendationServiceImpTest {
  @MockBean
  private TaskImpactAnalysisService taskImpactAnalysisService;

  @MockBean
  private PaperRepository paperRepository;

  private TaskReviewerRecommendationServiceImp taskReviewerRecommendationService;

  @BeforeEach
  public void init() {
    taskReviewerRecommendationService =
      new TaskReviewerRecommendationServiceImp(
        paperRepository,
        taskImpactAnalysisService
      );
  }

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

    Mockito.when(paperRepository.containPublication("EMSE")).thenReturn(true);

    List<String> pastPartners = Lists.newArrayList("4", "5");
    Mockito
      .when(paperRepository.getPastPartners(researchers))
      .thenReturn(pastPartners);

    List<String> reviewersFromReferences = Lists.newArrayList("6", "7");
    Mockito
      .when(
        paperRepository.getResearcherIdsFromReferences(
          referenceIds,
          pastPartners
        )
      )
      .thenReturn(reviewersFromReferences);

    List<String> reviewersFormPublication = Lists.newArrayList("8", "9");
    Mockito
      .when(
        paperRepository.getResearchersFromPublication(
          "EMSE",
          2020,
          pastPartners
        )
      )
      .thenReturn(reviewersFormPublication);

    List<String> reviewersFromSimilarDomain = Lists.newArrayList(
      "10",
      "11",
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7"
    );
    Mockito
      .when(
        paperRepository.getResearcherFromSimilarDomain(
          testDomains,
          2020,
          pastPartners
        )
      )
      .thenReturn(reviewersFromSimilarDomain);

    List<String> result = Lists.newArrayList("10", "11", "1", "2", "3");
    assertEquals(
      taskReviewerRecommendationService.getRecommendReviewer(testPaper),
      result
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

    Mockito.when(paperRepository.containPublication("EMSE")).thenReturn(true);

    List<String> pastPartners = Lists.newArrayList("4", "5");
    Mockito
      .when(paperRepository.getPastPartners(researchers))
      .thenReturn(pastPartners);

    List<String> partnersFromSameAffiliation = Lists.newArrayList(
      "7",
      "8",
      "9"
    );
    Mockito
      .when(paperRepository.getPartnersByAffiliation(researchers))
      .thenReturn(partnersFromSameAffiliation);

    List<String> reviewersFromSimilarDomain = Lists.newArrayList(
      "10",
      "11",
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7"
    );
    Mockito
      .when(
        paperRepository.getResearcherFromSimilarDomain(testDomains, 2020, null)
      )
      .thenReturn(reviewersFromSimilarDomain);

    List<String> result = Lists.newArrayList("4", "5", "7");

    assertEquals(
      taskReviewerRecommendationService.getNotRecommendReviewer(testPaper),
      result
    );
  }
}
