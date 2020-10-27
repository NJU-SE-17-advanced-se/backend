package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IAffiliation;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperBasic;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.njuse17advancedse.taskreviewerrecommendation.service.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TaskReviewerRecommendationServiceImpTest {
  @MockBean
  private TaskImpactAnalysisService taskImpactAnalysisService;

  @MockBean
  private PaperEntityService paperEntityService;

  @MockBean
  private AffiliationEntityService affiliationEntityService;

  @MockBean
  private DomainEntityService domainEntityService;

  @MockBean
  private ResearcherEntityService researcherEntityService;

  @MockBean
  private PublicationEntityService publicationEntityService;

  @MockBean
  private TaskPartnershipService taskPartnershipService;

  private TaskReviewerRecommendationServiceImp taskReviewerRecommendationService;

  @BeforeEach
  public void init() {
    taskReviewerRecommendationService =
      new TaskReviewerRecommendationServiceImp(
        paperEntityService,
        domainEntityService,
        publicationEntityService,
        researcherEntityService,
        affiliationEntityService,
        taskImpactAnalysisService,
        taskPartnershipService
      );
  }

  @Test
  void getRecommendReviewer() {
    IPaperUpload testPaper = new IPaperUpload();
    testPaper.setTitle("testPaper");
    testPaper.setId("test");
    testPaper.setJournal("EMSE");
    testPaper.setDate("2020/10/09");
    List<String> testDomains = Lists.newArrayList(
      "domain0",
      "domain1",
      "domain2"
    );
    testPaper.setDomainIds(testDomains);
    List<String> referenceIds = Lists.newArrayList("1", "2", "3");
    testPaper.setReferenceIds(referenceIds);

    List<String> rid1 = Lists.newArrayList("2", "5", "3", "4", "1", "6", "0");

    for (String pid : referenceIds) {
      IPaperBasic iPaperBasic = new IPaperBasic();
      iPaperBasic.setResearchers(
        Lists.newArrayList(pid + "", (Integer.parseInt(pid) + 1) + "")
      );
      Mockito
        .when(paperEntityService.getPaperBasicInfo(pid))
        .thenReturn(iPaperBasic);
    }

    Mockito
      .when(
        publicationEntityService.getPapersByPublication(
          testPaper.getJournal(),
          testPaper.getDate(),
          null
        )
      )
      .thenReturn(Lists.newArrayList("2", "5"));

    for (String rid : rid1) {
      Mockito
        .when(researcherEntityService.getDomainsByResearcherId(rid))
        .thenReturn(
          Lists.newArrayList(
            "domain" + rid,
            "domain" + (Integer.parseInt(rid) - 1),
            "domain" + (Integer.parseInt(rid) - 2)
          )
        );
      Mockito
        .when(taskImpactAnalysisService.getImpactByResearcherId(rid))
        .thenReturn(Double.parseDouble(rid));
    }

    for (int i = 0; i < 3; i++) {
      Mockito
        .when(domainEntityService.getResearcherByDomain("domain" + i))
        .thenReturn(Lists.newArrayList(i + ""));
    }

    List<String> result = Lists.newArrayList("4", "3", "2", "1", "0");
    assertEquals(
      taskReviewerRecommendationService
        .getRecommendReviewer(testPaper)
        .getBody(),
      result
    );
  }

  @Test
  void getNotRecommendReviewer() {
    IPaperUpload testPaper = new IPaperUpload();
    testPaper.setTitle("testPaper");
    testPaper.setId("test");
    List<String> researcherIds = Lists.newArrayList("0", "1", "2");
    testPaper.setResearcherIds(researcherIds);

    List<String> testDomains = new ArrayList<>();
    for (int i = 2; i < 5; i++) {
      testDomains.add(i + "");
    }
    testPaper.setDomainIds(testDomains);
    for (String rid : testPaper.getResearcherIds()) {
      Mockito
        .when(taskPartnershipService.getPartnersByResearcherId(rid))
        .thenReturn(
          Lists.newArrayList(
            Integer.parseInt(rid) * 2 + "",
            Integer.parseInt(rid) * 2 + 1 + ""
          )
        );
      IResearcher iResearcher = new IResearcher();
      iResearcher.setAffiliation(Lists.newArrayList("a" + rid));
      Mockito
        .when(researcherEntityService.getResearcherById(rid))
        .thenReturn(iResearcher);
      for (String aid : iResearcher.getAffiliation()) {
        IAffiliation iAffiliation = new IAffiliation();
        iAffiliation.setResearchers(
          Lists.newArrayList(
            Integer.parseInt(rid) * 2 - 1 + "",
            Integer.parseInt(rid) * 2 + ""
          )
        );
        Mockito
          .when(affiliationEntityService.getAffiliationById(aid))
          .thenReturn(iAffiliation);
      }
    }

    for (int i = -1; i < 6; i++) {
      Mockito
        .when(researcherEntityService.getDomainsByResearcherId(i + ""))
        .thenReturn(Lists.newArrayList((i + 1) + "", i + "", (i - 1) + ""));
    }
    List<String> result = Lists.newArrayList("3", "2", "4", "1", "5");
    assertEquals(
      taskReviewerRecommendationService
        .getNotRecommendReviewer(testPaper)
        .getBody(),
      result
    );
  }
}
