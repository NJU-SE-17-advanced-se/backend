package org.njuse17advancedse.taskpartnershipanalysis.implement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.entity.JpaPaper;
import org.njuse17advancedse.taskpartnershipanalysis.repository.ResearcherRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class TaskPartnershipAnalysisServiceImpTest {
  @MockBean
  private ResearcherRepository researcherRepository;

  private TaskPartnershipAnalysisServiceImp taskPartnershipAnalysisService;

  @BeforeEach
  public void init() {
    taskPartnershipAnalysisService =
      new TaskPartnershipAnalysisServiceImp(researcherRepository);
  }

  @Test
  void getPartners() {
    Mockito
      .when(researcherRepository.getPartnersByRid("test"))
      .thenReturn(Lists.newArrayList("a", "b", "c"));
    assertEquals(taskPartnershipAnalysisService.getPartners("test").size(), 3);
    Mockito
      .when(researcherRepository.getPartnersByRid("a"))
      .thenThrow(Problem.valueOf(Status.NOT_FOUND, "Researcher a not found"));
    try {
      researcherRepository.getPartnersByRid("a");
    } catch (ThrowableProblem t) {
      assertEquals(t.getMessage(), "Not Found: Researcher a not found");
    }
  }

  @Test
  void getPartnership() {
    String testRid = "171250661";
    int startDate = 2018;
    int endDate = 2020;

    List<String> papers = Lists.newArrayList("a", "b", "c");
    Mockito
      .when(researcherRepository.getPapersByRid(testRid, startDate, endDate))
      .thenReturn(papers);

    List<String> references = Lists.newArrayList("d", "e", "f");
    Mockito
      .when(researcherRepository.getReferencesByPapers(papers))
      .thenReturn(references);

    HashMap<String, Integer> coAuthorMap = new HashMap<>();
    HashMap<String, Integer> coCitationMao = new HashMap<>();
    for (int i = 0; i < 4; i++) {
      coAuthorMap.put(i + "", i + 1);
      coCitationMao.put(i + "", 4 - i);
    }
    Mockito
      .when(researcherRepository.getCoAuthorMap(testRid, papers))
      .thenReturn(coAuthorMap);

    Mockito
      .when(researcherRepository.getCitationMap(testRid, references))
      .thenReturn(coCitationMao);

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

    IResearcherNet result = taskPartnershipAnalysisService.getPartnership(
      testRid,
      startDate,
      endDate
    );

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

    List<String> domains = Lists.newArrayList("a", "b", "c");
    Mockito
      .when(researcherRepository.getDomainsByResearcherId(testRid))
      .thenReturn(domains);

    List<String> candidate_cooperation = Lists.newArrayList("0", "1", "2", "3");
    Mockito
      .when(researcherRepository.getNearPartnersByRid(testRid))
      .thenReturn(candidate_cooperation);

    List<String> candidate_domain = Lists.newArrayList("4", "5", "6", "7");
    Mockito
      .when(
        researcherRepository.getResearchersOfSimilarDomain(
          testRid,
          domains,
          candidate_cooperation
        )
      )
      .thenReturn(candidate_domain);

    List<JpaPaper> papers1 = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      JpaPaper jpaPaper = new JpaPaper(i + "", 2000 + i);
      papers1.add(jpaPaper);
    }
    Mockito
      .when(researcherRepository.getPaperDateById(testRid))
      .thenReturn(papers1);

    for (int i = 0; i < 8; i++) {
      List<JpaPaper> papers2 = new ArrayList<>();
      for (int j = 1; j <= i + 1; j++) {
        JpaPaper jpaPaper = new JpaPaper(j + "", 2000 + i + j);
        papers2.add(jpaPaper);
      }
      Mockito
        .when(researcherRepository.getPaperDateById(i + ""))
        .thenReturn(papers2);
    }

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
    assertEquals(
      taskPartnershipAnalysisService.getPotentialPartners(testRid),
      result
    );
  }
}
