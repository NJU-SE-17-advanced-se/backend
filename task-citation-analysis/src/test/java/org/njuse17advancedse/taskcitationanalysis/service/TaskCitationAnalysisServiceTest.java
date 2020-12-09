package org.njuse17advancedse.taskcitationanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskcitationanalysis.data.CitationAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class TaskCitationAnalysisServiceTest {
  @Autowired
  TaskCitationAnalysisService service;

  @MockBean
  CitationAnalysisRepository repository;

  @BeforeEach
  public void init() {
    Mockito.when(repository.existsPaperById("pid")).thenReturn(true);
    Mockito.when(repository.existsResearcherById("rid")).thenReturn(true);
    Mockito.when(repository.existsPaperById("not exist")).thenReturn(false);
    Mockito
      .when(repository.existsResearcherById("not exist"))
      .thenReturn(false);
  }

  @Test
  void testGetQuotedPapers() {
    Mockito
      .when(repository.getQuotedPapersByPaperId("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      Arrays.asList("1", "test1"),
      service.getQuotedPapersByPaperId("pid")
    );
    List<String> res = service.getQuotedPapersByPaperId("not exist");
    assertEquals("Not Found", res.get(0));
    assertEquals("Unknown", res.get(1));
    assertEquals("Unknown Id", res.get(2));
  }

  @Test
  void testGetQuotingPapers() {
    Mockito
      .when(repository.getQuotingPapersByPaperId("pid"))
      .thenReturn(Arrays.asList("2", "test2"));
    assertEquals(
      Arrays.asList("2", "test2"),
      service.getQuotingPapersByPaperId("pid")
    );
    List<String> res = service.getQuotingPapersByPaperId("not exist");
    assertEquals("Not Found", res.get(0));
    assertEquals("Unknown", res.get(1));
    assertEquals("Unknown Id", res.get(2));
  }

  @Test
  void testGetQuotedResearchers() {
    Mockito
      .when(repository.getPaperQuotedResearcher("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      Arrays.asList("1", "test1"),
      service.getPaperQuotedResearcher("pid")
    );
    List<String> res = service.getPaperQuotedResearcher("not exist");
    assertEquals("Not Found", res.get(0));
    assertEquals("Unknown", res.get(1));
    assertEquals("Unknown Id", res.get(2));
  }

  @Test
  void testGetQuotingResearchers() {
    Mockito
      .when(repository.getPaperQuotingResearcher("pid"))
      .thenReturn(Arrays.asList("2", "test2"));
    assertEquals(
      Arrays.asList("2", "test2"),
      service.getPaperQuotingResearcher("pid")
    );
    List<String> res = service.getPaperQuotingResearcher("not exist");
    assertEquals("Not Found", res.get(0));
    assertEquals("Unknown", res.get(1));
    assertEquals("Unknown Id", res.get(2));
  }

  @Test
  void testGetResearcherPaperQuotedPapers() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid1", Arrays.asList("1", "test1"));
    map.put("pid2", Arrays.asList("2", "test2"));
    Mockito
      .when(repository.getResearcherPaperQuotedPapers("rid"))
      .thenReturn(map);
    assertEquals(service.getQuotedPapersByResearcherId("rid"), map);
    Map<String, List<String>> mapRes = service.getQuotedPapersByResearcherId(
      "not exist"
    );
    assertMap(mapRes);
  }

  @Test
  void testGetResearcherPaperQuotingPapers() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid3", Arrays.asList("4", "test1"));
    map.put("pid4", Arrays.asList("2", "test9"));
    map.put("pid5", Arrays.asList("sada", "faker"));
    Mockito
      .when(repository.getResearcherPaperQuotingPapers("rid"))
      .thenReturn(map);
    assertEquals(service.getQuotingPapersByResearcherId("rid"), map);
    Map<String, List<String>> mapRes = service.getQuotingPapersByResearcherId(
      "not exist"
    );
    assertMap(mapRes);
  }

  @Test
  void testGetResearcherPaperQuotedResearcher() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid1", Arrays.asList("1", "test1"));
    map.put("pid2", Arrays.asList("2", "test2"));
    Mockito
      .when(repository.getResearcherPaperQuotedResearchers("rid"))
      .thenReturn(map);
    assertEquals(service.getResearcherPaperQuotedResearcher("rid"), map);
    Map<String, List<String>> mapRes = service.getResearcherPaperQuotedResearcher(
      "not exist"
    );
    assertMap(mapRes);
  }

  @Test
  void testGetResearcherPaperQuotingResearcher() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid3", Arrays.asList("4", "test1"));
    map.put("pid4", Arrays.asList("2", "test9"));
    map.put("pid5", Arrays.asList("sada", "faker"));
    Mockito
      .when(repository.getResearcherPaperQuotingResearchers("rid"))
      .thenReturn(map);
    assertEquals(service.getResearcherPaperQuotingResearcher("rid"), map);
    Map<String, List<String>> mapRes = service.getResearcherPaperQuotingResearcher(
      "not exist"
    );
    assertMap(mapRes);
  }

  @Test
  void testGetResearcherQuotedResearcher() {
    List<String> list = Arrays.asList("1", "2", "3");
    Mockito
      .when(repository.getResearcherQuotedResearcher("rid"))
      .thenReturn(list);
    assertEquals(list, service.getResearcherQuotedResearcher("rid"));
    List<String> res = service.getResearcherQuotedResearcher("not exist");
    assertEquals("Not Found", res.get(0));
    assertEquals("Unknown", res.get(1));
    assertEquals("Unknown Id", res.get(2));
  }

  @Test
  void testGetResearcherQuotingResearcher() {
    List<String> list = Arrays.asList("1", "2", "3");
    Mockito
      .when(repository.getResearcherQuotingResearcher("rid"))
      .thenReturn(list);
    assertEquals(list, service.getResearcherQuotingResearcher("rid"));
    List<String> res = service.getResearcherQuotingResearcher("not exist");
    assertEquals("Not Found", res.get(0));
    assertEquals("Unknown", res.get(1));
    assertEquals("Unknown Id", res.get(2));
  }

  private void assertMap(Map<String, List<String>> map) {
    assertNotNull(map.get("Not Found"));
    List<String> list = map.get("Not Found");
    assertEquals("Unknown", list.get(0));
  }
}
