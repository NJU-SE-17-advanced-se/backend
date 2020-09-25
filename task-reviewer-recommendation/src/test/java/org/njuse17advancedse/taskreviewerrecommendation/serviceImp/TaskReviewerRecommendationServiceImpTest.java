package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.org.glassfish.gmbal.Impact;
import java.util.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IImpact;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Domain;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Paper;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Researcher;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class TaskReviewerRecommendationServiceImpTest {
  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private TaskReviewerRecommendationServiceImp taskReviewerRecommendationService;

  @Test
  void getRecommendReviewer() {
    Paper testPaper = new Paper();
    testPaper.setTitle("testPaper");
    testPaper.setId("test");
    List<Domain> testDomains = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      Domain domain = new Domain();
      domain.setId(i + "");
      testDomains.add(domain);
    }
    testPaper.setDomains(testDomains);
    List<Paper> references = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      Paper paper = new Paper();
      List<Researcher> researchers = new ArrayList<>();
      for (int j = 0; j < i + 1; j++) {
        Researcher researcher = new Researcher();
        researcher.setId(i * j + "");
        researchers.add(researcher);
      }
      paper.setResearchers(researchers);
      references.add(paper);
    }

    testPaper.setReferences(references);

    Mockito
      .when(restTemplate.getForObject("/paper/getNewPaper/test", Paper.class))
      .thenReturn(testPaper);

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

    List<IResearcher> iResearchers = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      IResearcher iResearcher = new IResearcher();
      iResearcher.setId(i + "");
      iResearchers.add(iResearcher);
    }
    assertEquals(
      taskReviewerRecommendationService.getRecommendReviewer("test").getBody(),
      iResearchers
    );
  }

  @Test
  void getNotRecommendReviewer() {}
}
