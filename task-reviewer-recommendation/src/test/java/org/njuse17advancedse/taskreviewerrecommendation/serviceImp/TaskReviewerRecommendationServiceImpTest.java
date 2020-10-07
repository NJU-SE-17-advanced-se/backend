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
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
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

    List<String> rids1 = Lists.newArrayList(
      "0",
      "4",
      "12",
      "16",
      "1",
      "2",
      "3",
      "6",
      "8",
      "9"
    );
    List<List<Domain>> reviewersDomains = new ArrayList<>();
    for (String s : rids1) {
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
      .thenReturn(referenceIds);

    Mockito
      .when(
        restTemplate.postForObject("/researcher/getDomains", rids1, List.class)
      )
      .thenReturn(reviewersDomains);

    List<String> rids2 = Lists.newArrayList("0", "1", "2");
    List<IImpact> impacts = new ArrayList<>();
    for (int i = 0; i < rids2.size(); i++) {
      IImpact impact = new IImpact();
      impact.setImpact(3 - i);
      impacts.add(impact);
    }

    Mockito
      .when(
        restTemplate.postForObject("/impact/researchers", rids2, List.class)
      )
      .thenReturn(impacts);

    List<Researcher> researchers = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      Researcher researcher = new Researcher();
      researcher.setId(i + "");
      researchers.add(researcher);
    }

    Mockito
      .when(restTemplate.postForObject("/getResearchers", rids2, List.class))
      .thenReturn(researchers);

    List<IResearcher> iResearchers = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      IResearcher iResearcher = new IResearcher();
      iResearcher.setId(i + "");
      iResearchers.add(iResearcher);
    }
    assertEquals(
      taskReviewerRecommendationService
        .getRecommendReviewer(testPaper)
        .getBody(),
      iResearchers
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

    List<Researcher> partners = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Researcher researcher = new Researcher();
      researcher.setId(i + "" + i);
      partners.add(researcher);
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
    List<String> pids = partners
      .stream()
      .map(Researcher::getId)
      .collect(Collectors.toList());
    HashSet<String> set = new HashSet<>(pids);
    pids.clear();
    pids.addAll(set);
    Mockito
      .when(
        restTemplate.postForObject("/researcher/getDomains", pids, List.class)
      )
      .thenReturn(domainsList);

    List<IResearcher> iResearchers = new ArrayList<>();

    List<String> result = Lists.newArrayList("44", "22", "66", "00", "88");
    for (int i = 0; i < 5; i++) {
      IResearcher iResearcher = new IResearcher();
      iResearcher.setId(result.get(i));
      iResearchers.add(iResearcher);
    }
    assertEquals(
      taskReviewerRecommendationService
        .getNotRecommendReviewer(testPaper)
        .getBody(),
      iResearchers
    );
  }
}
