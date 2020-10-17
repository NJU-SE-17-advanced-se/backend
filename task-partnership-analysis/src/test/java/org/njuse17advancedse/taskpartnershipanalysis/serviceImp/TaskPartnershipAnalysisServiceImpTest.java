package org.njuse17advancedse.taskpartnershipanalysis.serviceImp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.dto.RScoreData;
import org.njuse17advancedse.taskpartnershipanalysis.entity.Paper;
import org.njuse17advancedse.taskpartnershipanalysis.entity.Researcher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class TaskPartnershipAnalysisServiceImpTest {
  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private TaskPartnershipAnalysisServiceImp taskPartnershipAnalysisService;

  @Test
  void getPartnership() {
    String testRid = "171250661";
    String startDate = "2018";
    String endDate = "2020";

    List<Paper> testPapers1 = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      Paper paper = new Paper();
      paper.setId("test" + i);
      List<Researcher> researchers = new ArrayList<>();
      for (int j = 0; j < i + 1; j++) {
        Researcher researcher = new Researcher();
        researcher.setId("17125066" + (j + 2));
        researchers.add(researcher);
      }
      paper.setResearchers(researchers);
      List<Paper> refs = new ArrayList<>();
      for (int j = 0; j < 5; j++) {
        Paper ref = new Paper();
        ref.setId(j + "");
        refs.add(ref);
      }
      paper.setReferences(refs);
      testPapers1.add(paper);
    }

    List<Paper> testPapers2 = new ArrayList<>();
    Paper paper2 = new Paper();
    List<Paper> refs2 = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      Paper paper = new Paper();
      paper.setId(i + "");
      refs2.add(paper);
    }
    paper2.setReferences(refs2);
    testPapers2.add(paper2);

    List<Paper> testPapers3 = new ArrayList<>();
    Paper paper3 = new Paper();
    List<Paper> refs3 = new ArrayList<>();
    for (int i = 0; i < 1; i++) {
      Paper paper = new Paper();
      paper.setId(i + "");
      refs3.add(paper);
    }
    paper3.setReferences(refs3);
    testPapers3.add(paper3);

    List<Paper> testPapers4 = new ArrayList<>();
    Paper paper4 = new Paper();
    List<Paper> refs4 = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      Paper paper = new Paper();
      paper.setId(i + "");
      refs4.add(paper);
    }
    paper4.setReferences(refs4);
    testPapers4.add(paper4);

    Mockito
      .when(
        restTemplate.getForObject("/getPapers/171250661/2018/2020", List.class)
      )
      .thenReturn(testPapers1);
    Mockito
      .when(
        restTemplate.getForObject("/getPapers/171250662/2018/2020", List.class)
      )
      .thenReturn(testPapers2);
    Mockito
      .when(
        restTemplate.getForObject("/getPapers/171250663/2018/2020", List.class)
      )
      .thenReturn(testPapers3);
    Mockito
      .when(
        restTemplate.getForObject("/getPapers/171250664/2018/2020", List.class)
      )
      .thenReturn(testPapers4);

    IResearcherNet iResearcherNet = new IResearcherNet();
    iResearcherNet.setPartners(
      Lists.newArrayList("171250664", "171250663", "171250662")
    );
    iResearcherNet.setWeight(
      Lists.newArrayList(
        new Double[] { 0.33, 1.00 },
        new Double[] { 0.67, 0.25 },
        new Double[] { 1.00, 0.50 }
      )
    );

    IResearcherNet result = taskPartnershipAnalysisService
      .getPartnership(testRid, startDate, endDate)
      .getBody();
    assert result != null;
    assertEquals(result.getPartners(), iResearcherNet.getPartners());
    for (int i = 0; i < result.getWeight().size(); i++) {
      assertEquals(
        result.getWeight().get(i)[0],
        iResearcherNet.getWeight().get(i)[0]
      );
      assertEquals(
        result.getWeight().get(i)[1],
        iResearcherNet.getWeight().get(i)[1]
      );
    }
  }

  @Test
  void getPotentialPartners() {
    String testRid = "171250661";
    List<String> candidate_domain = Lists.newArrayList(
      "0",
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7"
    );
    List<String> candidate_cooperation = Lists.newArrayList(
      "6",
      "7",
      "8",
      "9",
      "10",
      "11",
      "12"
    );
    Mockito
      .when(
        restTemplate.getForObject(
          "/researchers-similar-domain/171250661",
          List.class
        )
      )
      .thenReturn(candidate_domain);
    Mockito
      .when(restTemplate.getForObject("/past-partners/171250661", List.class))
      .thenReturn(candidate_cooperation);

    for (int i = 0; i < 13; i++) {
      Mockito
        .when(restTemplate.getForObject("/impact/" + i, Double.class))
        .thenReturn((double) i);
      Mockito
        .when(
          restTemplate.getForObject(
            "/domain-coverage/171250661/" + i,
            Double.class
          )
        )
        .thenReturn((double) i);
      RScoreData r_scoreData = new RScoreData();
      HashMap<Integer, Integer> coNumber = new HashMap<>();
      HashMap<Integer, Integer> sum1 = new HashMap<>();
      HashMap<Integer, Integer> sum2 = new HashMap<>();
      for (int j = 2018; j < 2021; j++) {
        coNumber.put(j, i + 1);
        sum1.put(j, i + 5);
        sum2.put(j, i + 4);
      }
      r_scoreData.setMapOfYearAndCoNumber(coNumber);
      r_scoreData.setMapOfYearAndSum1(sum1);
      r_scoreData.setMapOfYearAndSum2(sum2);
      Mockito
        .when(
          restTemplate.getForObject("/R-score/171250661/" + i, RScoreData.class)
        )
        .thenReturn(r_scoreData);
    }

    HashMap<String, Double> result = new HashMap<>();
    List<Double> values = Lists.newArrayList(
      26.27,
      24.23,
      22.19,
      20.13,
      18.07,
      16.0,
      13.92,
      11.82,
      9.69,
      7.54
    );
    for (int i = 0; i < 10; i++) {
      result.put((12 - i) + "", values.get(i));
    }
    assertEquals(
      taskPartnershipAnalysisService.getPotentialPartners(testRid).getBody(),
      result
    );
  }
}
