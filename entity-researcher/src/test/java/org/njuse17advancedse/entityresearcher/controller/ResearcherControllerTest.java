package org.njuse17advancedse.entityresearcher.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.repository.ResearcherRepository;
import org.njuse17advancedse.entityresearcher.service.ResearcherEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class ResearcherControllerTest {
  @Autowired
  ResearcherController researcherController;

  @MockBean
  ResearcherRepository researcherRepository;

  @MockBean
  ResearcherEntityService researcherEntityService;

  @Test
  void getResearcherById() {
    IResearcher iResearcher = new IResearcher();
    iResearcher.setId("a");
    iResearcher.setPapers(Lists.newArrayList("test"));
    iResearcher.setName("ycj");
    iResearcher.setId("IEEE_37085391626");
    Mockito
      .when(researcherEntityService.containResearcher("a"))
      .thenReturn(true);
    Mockito
      .when(researcherEntityService.getResearcherById("a"))
      .thenReturn(iResearcher);
    assertEquals(
      iResearcher.getName(),
      researcherController.getResearcherById("a").getName()
    );
  }

  @Test
  void getResearcherBasicInfoById() {
    IResearcherBasic iResearcherBasic = new IResearcherBasic();
    iResearcherBasic.setId("a");
    iResearcherBasic.setPapers(Lists.newArrayList("test"));
    iResearcherBasic.setName("ycj");
    iResearcherBasic.setId("IEEE_37085391626");
    Mockito
      .when(researcherEntityService.containResearcher("a"))
      .thenReturn(true);
    Mockito
      .when(researcherEntityService.getResearcherBasicById("a"))
      .thenReturn(iResearcherBasic);
    assertEquals(
      iResearcherBasic,
      researcherController.getResearcherBasicInfoById("a")
    );
  }

  @Test
  void getResearcherPapersByTimeRange() {
    List<String> result = Lists.newArrayList("1", "2", "3");
    Mockito
      .when(researcherEntityService.containResearcher("test"))
      .thenReturn(true);
    Mockito
      .when(researcherEntityService.getPapersByRid("test", 2017, 2019))
      .thenReturn(result);
    assertEquals(
      new ArrayList<>(),
      researcherController.getResearcherPapersByTimeRange(
        "test",
        "2017",
        "2019"
      )
    );
  }

  @Test
  void getDomainsByTimeRange() {
    List<String> result = Lists.newArrayList("1", "2", "3");
    Mockito
      .when(researcherEntityService.containResearcher("test"))
      .thenReturn(true);
    Mockito
      .when(researcherEntityService.getDomainByRid("test", 2017, 2019))
      .thenReturn(result);
    assertEquals(
      new ArrayList<>(),
      researcherController.getDomainsByTimeRange("test", "2017", "2019")
    );
  }

  @Test
  void getAffiliationsByTimeRange() {
    List<String> result = Lists.newArrayList("1", "2", "3");
    Mockito
      .when(researcherEntityService.containResearcher("test"))
      .thenReturn(true);
    Mockito
      .when(researcherEntityService.getAffiliationByRid("test", 2017, 2019))
      .thenReturn(result);
    assertEquals(
      new ArrayList<>(),
      researcherController.getAffiliationsByTimeRange("test", "2017", "2019")
    );
  }
}
