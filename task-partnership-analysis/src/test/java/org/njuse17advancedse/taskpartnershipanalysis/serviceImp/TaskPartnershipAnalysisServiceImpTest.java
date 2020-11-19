package org.njuse17advancedse.taskpartnershipanalysis.serviceImp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IPaper;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IPaperBasic;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.repository.ResearcherRepository;
import org.njuse17advancedse.taskpartnershipanalysis.service.DomainEntityService;
import org.njuse17advancedse.taskpartnershipanalysis.service.PaperEntityService;
import org.njuse17advancedse.taskpartnershipanalysis.service.ResearcherEntityService;
import org.njuse17advancedse.taskpartnershipanalysis.service.TaskImpactAnalysisService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TaskPartnershipAnalysisServiceImpTest {
  @MockBean
  private ResearcherRepository researcherRepository;

  @MockBean
  private DomainEntityService domainEntityService;

  @MockBean
  private PaperEntityService paperEntityService;

  @MockBean
  private ResearcherEntityService researcherEntityService;

  @MockBean
  private TaskImpactAnalysisService taskImpactAnalysisService;

  private TaskPartnershipAnalysisServiceImp taskPartnershipAnalysisService;

  @BeforeEach
  public void init() {
    taskPartnershipAnalysisService =
      new TaskPartnershipAnalysisServiceImp(
        researcherRepository,
        researcherEntityService,
        paperEntityService,
        domainEntityService,
        taskImpactAnalysisService
      );
  }

  @Test
  void getPartners() {
    String testResearcherId = "test";
    List<String> papers = Lists.newArrayList("1", "2", "3", "4");
    Mockito
      .when(researcherEntityService.getPaperByResearcherId("test", null, null))
      .thenReturn(papers);
    for (String pid : papers) {
      IPaperBasic iPaperBasic = new IPaperBasic();
      iPaperBasic.setResearchers(
        Lists.newArrayList(
          Integer.parseInt(pid) + 1 + "",
          Integer.parseInt(pid) + "",
          Integer.parseInt(pid) - 1 + ""
        )
      );
      Mockito
        .when(paperEntityService.getPaperBasicInfo(pid))
        .thenReturn(iPaperBasic);
    }
    assertEquals(
      taskPartnershipAnalysisService.getPartners(testResearcherId),
      Lists.newArrayList("0", "1", "2", "3", "4", "5")
    );
  }

  @Test
  void getPartnership() {
    String testRid = "171250661";
    String startDate = "2018";
    String endDate = "2020";

    List<String> papers = Lists.newArrayList("p0", "p1", "p2");

    List<IPaper> iPapers = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      IPaper iPaper = new IPaper();
      iPaper.setId(i + "");
      iPaper.setResearchers(Lists.newArrayList(i + "", i - 1 + "", i + 1 + ""));
      iPaper.setReferences(Lists.newArrayList("a" + i, "b" + i, "c" + i));
      iPapers.add(iPaper);
    }

    Mockito
      .when(
        researcherEntityService.getPaperByResearcherId(
          testRid,
          startDate,
          endDate
        )
      )
      .thenReturn(papers);

    for (int i = 0; i < 3; i++) {
      Mockito
        .when(paperEntityService.getPaper("p" + i))
        .thenReturn(iPapers.get(i));
    }

    for (int i = -1; i < 4; i++) {
      List<String> partnerPapers = new ArrayList<>();
      for (int j = 0; j < i + 2; j++) {
        partnerPapers.add(j + "");
      }
      Mockito
        .when(
          researcherEntityService.getPaperByResearcherId(
            i + "",
            startDate,
            endDate
          )
        )
        .thenReturn(partnerPapers);
    }

    for (int i = 0; i < 6; i++) {
      IPaper iPaper = new IPaper();
      if (i % 2 == 0) {
        iPaper.setReferences(Lists.newArrayList("a" + i, "b" + i));
      } else {
        iPaper.setReferences(Lists.newArrayList("b" + i, "c" + i));
      }
      Mockito.when(paperEntityService.getPaper(i + "")).thenReturn(iPaper);
    }

    IResearcherNet iResearcherNet = new IResearcherNet();
    iResearcherNet.setPartners(Lists.newArrayList("0", "1", "2", "3", "-1"));
    iResearcherNet.setWeight(
      Lists.newArrayList(
        new Double[] { 0.67, 0.6 },
        new Double[] { 1.0, 1.0 },
        new Double[] { 0.67, 1.0 },
        new Double[] { 0.33, 1.0 },
        new Double[] { 0.33, 0.2 }
      )
    );

    IResearcherNet result = taskPartnershipAnalysisService.getPartnership(
      testRid,
      startDate,
      endDate
    );
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
    List<String> testPapers = Lists.newArrayList("8", "9");
    List<String> candidate_domain = Lists.newArrayList("0", "1", "2", "3", "4");
    List<String> candidate_cooperation = Lists.newArrayList(
      "3",
      "4",
      "5",
      "6",
      "7"
    );

    Mockito
      .when(researcherEntityService.getDomainsByResearcherId(testRid))
      .thenReturn(Lists.newArrayList("d0", "d1", "d2"));
    for (int i = 0; i < 3; i++) {
      Mockito
        .when(domainEntityService.getResearcherByDomain("d" + i))
        .thenReturn(Lists.newArrayList(i + "", i + 1 + "", i + 2 + ""));
    }

    Mockito
      .when(researcherEntityService.getPaperByResearcherId(testRid, null, null))
      .thenReturn(testPapers);

    for (String pid : testPapers) {
      IPaperBasic iPaperBasic = new IPaperBasic();
      iPaperBasic.setResearchers(
        Lists.newArrayList(
          Integer.parseInt(pid) - 1 + "",
          Integer.parseInt(pid) + "",
          Integer.parseInt(pid) - 2 + ""
        )
      );
      Mockito
        .when(paperEntityService.getPaperBasicInfo(pid))
        .thenReturn(iPaperBasic);
    }

    List<String> candidate = Lists.newArrayList(
      "0",
      "1",
      "2",
      "3",
      "4",
      "6",
      "7",
      "8",
      "9"
    );
    for (String rid : candidate) {
      Mockito
        .when(taskImpactAnalysisService.getImpactByResearcherId(rid))
        .thenReturn(Double.parseDouble(rid));
      Mockito
        .when(researcherEntityService.getDomainsByResearcherId(rid))
        .thenReturn(
          Lists.newArrayList(
            "d" + rid,
            "d" + Integer.parseInt(rid) + 1,
            "d" + Integer.parseInt(rid) + 2
          )
        );
      IPaperBasic iPaperBasic = new IPaperBasic();
      iPaperBasic.setPublicationDate(2010 + Integer.parseInt(rid) + "");
      iPaperBasic.setId(rid + "");
      Mockito
        .when(paperEntityService.getPaperBasicInfo(rid))
        .thenReturn(iPaperBasic);
    }

    HashMap<String, Double> result = new HashMap<>();
    List<Double> values = Lists.newArrayList(0.17, 1.17, 2.17, 3.0, 4.0);
    for (int i = 0; i < 5; i++) {
      result.put(i + "", values.get(i));
    }
    assertEquals(
      taskPartnershipAnalysisService.getPotentialPartners(testRid),
      result
    );
  }
}
