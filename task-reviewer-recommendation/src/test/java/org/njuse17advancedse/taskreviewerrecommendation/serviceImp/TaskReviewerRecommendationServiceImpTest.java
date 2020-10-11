package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IImpact;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Domain;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Researcher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class TaskReviewerRecommendationServiceImpTest {
  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private TaskReviewerRecommendationServiceImp taskReviewerRecommendationService;

  @Test
  void getRecommendReviewer() {
    IPaperUpload testPaper = new IPaperUpload();
    testPaper.setTitle("testPaper");
    testPaper.setId("test");
    testPaper.setJournal("EMSE");
    testPaper.setDate("2020/10/09");
    List<String> testDomains = Lists.newArrayList("0", "1", "2");
    testPaper.setDomainIds(testDomains);
    List<String> referenceIds = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      List<String> researcherIds = new ArrayList<>();
      for (int j = 0; j < i + 1; j++) {
        researcherIds.add(i * j + "");
      }
      referenceIds.addAll(researcherIds);
    }

    testPaper.setReferenceIds(referenceIds);

    List<String> rids = Lists.newArrayList("0", "1", "2", "3");

    List<String> researcherIds = Lists.newArrayList(
      "0",
      "1",
      "2",
      "3",
      "4",
      "2",
      "2",
      "1",
      "5"
    );

    List<List<Domain>> reviewersDomains = new ArrayList<>();
    for (String s : researcherIds) {
      List<Domain> domains = new ArrayList<>();
      for (int j = 0; j < 2; j++) {
        Domain domain = new Domain();
        domain.setId((Integer.parseInt(s) + j) + "");
        domains.add(domain);
      }
      reviewersDomains.add(domains);
    }

    Mockito
      .when(
        restTemplate.postForObject(
          "/getResearchersByPaperIds",
          referenceIds,
          List.class
        )
      )
      .thenReturn(rids);

    List<String> researchersOfSameJournalPapers = Lists.newArrayList(
      "1",
      "2",
      "5"
    );
    Mockito
      .when(
        restTemplate.getForObject(
          "/paper/getPapersByJournal/EMSE/2020/10/09",
          List.class
        )
      )
      .thenReturn(researchersOfSameJournalPapers);

    List<String> domains = Lists.newArrayList("0", "1", "2");

    List<String> researcherOfSimilarDomainPapers = Lists.newArrayList("4", "2");
    Mockito
      .when(
        restTemplate.postForObject(
          "/paper/getPapersByDomain",
          domains,
          List.class
        )
      )
      .thenReturn(researcherOfSimilarDomainPapers);

    Mockito
      .when(
        restTemplate.postForObject(
          "/researcher/getDomains",
          researcherIds,
          List.class
        )
      )
      .thenReturn(reviewersDomains);

    List<String> rids2 = Lists.newArrayList("2", "1", "0", "3", "4", "5");
    List<Double> impacts = new ArrayList<>();
    for (String s : rids2) {
      impacts.add(Double.parseDouble(s));
    }

    Mockito
      .when(
        restTemplate.postForObject("/impact/researchers", rids2, List.class)
      )
      .thenReturn(impacts);

    List<String> result = Lists.newArrayList("5", "4", "3", "2", "1");
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
    List<String> rids = Lists.newArrayList("0", "1", "2");

    List<String> partners = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      partners.add(i + "" + i);
    }
    Mockito
      .when(restTemplate.postForObject("/getPartnersByRids/", rids, List.class))
      .thenReturn(partners);
    List<List<Domain>> domainsList = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      List<Domain> domains = new ArrayList<>();
      for (int j = 0; j < 3; j++) {
        Domain domain = new Domain();
        domain.setId((i + j) + "");
        domains.add(domain);
      }
      domainsList.add(domains);
    }
    HashSet<String> set = new HashSet<>(partners);
    partners.clear();
    partners.addAll(set);
    Mockito
      .when(
        restTemplate.postForObject(
          "/researcher/getDomains",
          partners,
          List.class
        )
      )
      .thenReturn(domainsList);

    List<String> result = Lists.newArrayList("44", "22", "66", "00", "88");

    assertEquals(
      taskReviewerRecommendationService
        .getNotRecommendReviewer(testPaper)
        .getBody(),
      result
    );
  }
}
