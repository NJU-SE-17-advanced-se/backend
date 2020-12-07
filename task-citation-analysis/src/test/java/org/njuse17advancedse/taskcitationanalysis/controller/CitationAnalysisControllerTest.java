package org.njuse17advancedse.taskcitationanalysis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskcitationanalysis.data.CitationAnalysisRepository;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class CitationAnalysisControllerTest {
  @MockBean
  TaskCitationAnalysisService service;

  @MockBean
  CitationAnalysisRepository repository;

  @Autowired
  TaskCitationAnalysisController controller;

  @Autowired
  PaperController paperController;

  @Autowired
  ResearcherController researcherController;

  private String quoted = "quoted";
  private String quoting = "quoting";

  @Test
  void testGetQuotedPapers() {
    Mockito
      .when(service.getQuotedPapersByPaperId("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      Arrays.asList("1", "test1"),
      controller.getPaperCitations("pid", quoted)
    );
    assertEquals(
      Arrays.asList("1", "test1"),
      paperController.getPaperCitations("pid", quoted)
    );
    Mockito
      .when(service.getQuotedPapersByPaperId("not exist"))
      .thenReturn(getProblemList());
    try {
      controller.getPaperCitations("not exist", quoted);
    } catch (Exception ignored) {}
    try {
      paperController.getPaperCitations("not exist", quoted);
    } catch (Exception ignored) {}
  }

  @Test
  void testGetQuotingPapers() {
    Mockito
      .when(service.getQuotingPapersByPaperId("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      Arrays.asList("1", "test1"),
      controller.getPaperCitations("pid", quoting)
    );
    assertEquals(
      Arrays.asList("1", "test1"),
      paperController.getPaperCitations("pid", quoting)
    );
    Mockito
      .when(service.getQuotingPapersByPaperId("not exist"))
      .thenReturn(getProblemList());
    try {
      controller.getPaperCitations("not exist", quoting);
    } catch (Exception ignored) {}
    try {
      paperController.getPaperCitations("not exist", quoting);
    } catch (Exception ignored) {}
  }

  @Test
  void testGetQuotedResearchers() {
    Mockito
      .when(service.getPaperQuotedResearcher("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      Arrays.asList("1", "test1"),
      controller.getPaperCitedResearchers("pid", quoted)
    );
    assertEquals(
      Arrays.asList("1", "test1"),
      paperController.getPaperCitedResearchers("pid", quoted)
    );
    Mockito
      .when(service.getPaperQuotedResearcher("not exist"))
      .thenReturn(getProblemList());
    try {
      controller.getPaperCitedResearchers("not exist", quoted);
    } catch (Exception ignored) {}
    try {
      paperController.getPaperCitedResearchers("not exist", quoted);
    } catch (Exception ignored) {}
  }

  @Test
  void testGetQuotingResearchers() {
    Mockito
      .when(service.getPaperQuotingResearcher("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      Arrays.asList("1", "test1"),
      controller.getPaperCitedResearchers("pid", quoting)
    );
    assertEquals(
      Arrays.asList("1", "test1"),
      paperController.getPaperCitedResearchers("pid", quoting)
    );
    Mockito
      .when(service.getPaperQuotingResearcher("not exist"))
      .thenReturn(getProblemList());
    try {
      controller.getPaperCitedResearchers("not exist", quoting);
    } catch (Exception ignored) {}
    try {
      paperController.getPaperCitedResearchers("not exist", quoting);
    } catch (Exception ignored) {}
  }

  @Test
  void testGetResearcherPaperQuotedPapers() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid1", Arrays.asList("1", "test1"));
    map.put("pid2", Arrays.asList("2", "test2"));
    Mockito.when(service.getQuotedPapersByResearcherId("rid")).thenReturn(map);
    assertEquals(map, controller.getResearcherPapersCitations("rid", quoted));
    assertEquals(
      map,
      researcherController.getResearcherPapersCitations("rid", quoted)
    );
    Mockito
      .when(service.getQuotedPapersByResearcherId("not exist"))
      .thenReturn(getProblemMap());
    try {
      controller.getResearcherPapersCitations("not exist", quoted);
    } catch (Exception ignored) {}
    try {
      researcherController.getResearcherPapersCitations("not exist", quoted);
    } catch (Exception ignored) {}
  }

  @Test
  void testGetResearcherPaperQuotingPapers() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid3", Arrays.asList("4", "test1"));
    map.put("pid4", Arrays.asList("2", "test9"));
    map.put("pid5", Arrays.asList("sada", "faker"));
    Mockito.when(service.getQuotingPapersByResearcherId("rid")).thenReturn(map);
    assertEquals(map, controller.getResearcherPapersCitations("rid", quoting));
    assertEquals(
      map,
      researcherController.getResearcherPapersCitations("rid", quoting)
    );
    Mockito
      .when(service.getQuotingPapersByResearcherId("not exist"))
      .thenReturn(getProblemMap());
    try {
      controller.getResearcherPapersCitations("not exist", quoting);
    } catch (Exception ignored) {}
    try {
      researcherController.getResearcherPapersCitations("not exist", quoting);
    } catch (Exception ignored) {}
  }

  @Test
  void testGetResearcherPaperQuotedResearcher() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid1", Arrays.asList("1", "test1"));
    map.put("pid2", Arrays.asList("2", "test2"));
    Mockito
      .when(service.getResearcherPaperQuotedResearcher("rid"))
      .thenReturn(map);
    assertEquals(
      map,
      controller.getResearcherPapersCitedResearchers("rid", quoted)
    );
    assertEquals(
      map,
      researcherController.getResearcherPapersCitedResearchers("rid", quoted)
    );
    Mockito
      .when(service.getResearcherPaperQuotedResearcher("not exist"))
      .thenReturn(getProblemMap());
    try {
      controller.getResearcherPapersCitedResearchers("not exist", quoted);
    } catch (Exception ignored) {}
    try {
      researcherController.getResearcherPapersCitedResearchers(
        "not exist",
        quoted
      );
    } catch (Exception ignored) {}
  }

  @Test
  void testGetResearcherPaperQuotingResearcher() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid3", Arrays.asList("4", "test1"));
    map.put("pid4", Arrays.asList("2", "test9"));
    map.put("pid5", Arrays.asList("sada", "faker"));
    Mockito
      .when(service.getResearcherPaperQuotingResearcher("rid"))
      .thenReturn(map);
    assertEquals(
      map,
      controller.getResearcherPapersCitedResearchers("rid", quoting)
    );
    assertEquals(
      map,
      researcherController.getResearcherPapersCitedResearchers("rid", quoting)
    );
    Mockito
      .when(service.getResearcherPaperQuotingResearcher("not exist"))
      .thenReturn(getProblemMap());
    try {
      controller.getResearcherPapersCitedResearchers("not exist", quoting);
    } catch (Exception ignored) {}
    try {
      researcherController.getResearcherPapersCitedResearchers(
        "not exist",
        quoting
      );
    } catch (Exception ignored) {}
  }

  @Test
  void testGetResearcherQuotedResearcher() {
    Mockito
      .when(service.getResearcherQuotedResearcher("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      Arrays.asList("1", "test1"),
      controller.getResearcherCitations("pid", quoted)
    );
    assertEquals(
      Arrays.asList("1", "test1"),
      researcherController.getResearcherCitations("pid", quoted)
    );
    Mockito
      .when(service.getResearcherQuotedResearcher("not exist"))
      .thenReturn(getProblemList());
    try {
      controller.getResearcherCitations("not exist", quoted);
    } catch (Exception ignored) {}
    try {
      researcherController.getResearcherCitations("not exist", quoted);
    } catch (Exception ignored) {}
  }

  @Test
  void testGetResearcherQuotingResearcher() {
    Mockito
      .when(service.getResearcherQuotingResearcher("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      Arrays.asList("1", "test1"),
      controller.getResearcherCitations("pid", quoting)
    );
    assertEquals(
      Arrays.asList("1", "test1"),
      researcherController.getResearcherCitations("pid", quoting)
    );
    Mockito
      .when(service.getResearcherQuotingResearcher("not exist"))
      .thenReturn(getProblemList());
    try {
      controller.getResearcherCitations("not exist", quoting);
    } catch (Exception ignored) {}
    try {
      researcherController.getResearcherCitations("not exist", quoting);
    } catch (Exception ignored) {}
  }

  private void assertMap(Map<String, List<String>> map) {
    assertNotNull(map.get("Not Found"));
    List<String> list = map.get("Not Found");
    assertEquals("Unknown", list.get(0));
  }

  private Map<String, List<String>> getProblemMap() {
    Map<String, List<String>> res = new HashMap<>();
    List<String> parms = new ArrayList<>();
    parms.add("Unknown");
    res.put("Not Found", parms);
    return res;
  }

  private List<String> getProblemList() {
    List<String> res = new ArrayList<>();
    res.add("Not Found");
    res.add("Unknown");
    res.add("Unknown Id");
    return res;
  }
}
