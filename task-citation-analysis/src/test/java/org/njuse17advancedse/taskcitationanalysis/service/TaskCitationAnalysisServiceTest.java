package org.njuse17advancedse.taskcitationanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.*;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskcitationanalysis.dto.IPaper;
import org.njuse17advancedse.taskcitationanalysis.dto.IResearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TaskCitationAnalysisServiceTest {
  @MockBean
  PaperService paperService;

  @MockBean
  ResearcherService researcherService;

  @Autowired
  TaskCitationAnalysisService service;

  @BeforeEach
  public void init() {
    IPaper p1 = mock(IPaper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    IPaper p2 = mock(IPaper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    IPaper p3 = mock(IPaper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    IPaper p4 = mock(IPaper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    IPaper p5 = mock(IPaper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    IPaper p6 = mock(IPaper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    IPaper p7 = mock(IPaper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    IPaper p8 = mock(IPaper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito
      .when(paperService.getCitations("1"))
      .thenReturn(Arrays.asList("1", "2", "3", "4", "6", "7", "8"));
    Mockito
      .when(paperService.getCitations("2"))
      .thenReturn(Arrays.asList("1", "2", "4", "6", "7", "8"));
    Mockito
      .when(paperService.getCitations("3"))
      .thenReturn(Arrays.asList("1", "2", "4", "6", "7", "8"));
    Mockito
      .when(paperService.getCitations("4"))
      .thenReturn(Arrays.asList("1", "4", "7", "8"));
    Mockito
      .when(paperService.getCitations("5"))
      .thenReturn(Arrays.asList("4", "7"));
    Mockito
      .when(paperService.getCitations("6"))
      .thenReturn(Collections.singletonList("4"));
    Mockito
      .when(paperService.getCitations("7"))
      .thenReturn(Collections.singletonList("4"));
    Mockito
      .when(paperService.getCitations("8"))
      .thenReturn(Collections.emptyList());
    IResearcher r = mock(IResearcher.class);
    r.setId("rid");
    Mockito
      .when(paperService.getPapers(null, null, null))
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
    Mockito.when(paperService.getPaper("1")).thenReturn(p1);
    Mockito.when(paperService.getPaper("2")).thenReturn(p2);
    Mockito.when(paperService.getPaper("3")).thenReturn(p3);
    Mockito.when(paperService.getPaper("4")).thenReturn(p4);
    Mockito.when(paperService.getPaper("5")).thenReturn(p5);
    Mockito.when(paperService.getPaper("6")).thenReturn(p6);
    Mockito.when(paperService.getPaper("7")).thenReturn(p7);
    Mockito.when(paperService.getPaper("8")).thenReturn(p8);
  }

  @Test
  public void testGetQuotedPapers() {
    assertEquals(
      service.getQuotedPapersByPaperId("1"),
      Arrays.asList("1", "2", "3", "4")
    );
    assertEquals(
      service.getQuotedPapersByPaperId("2"),
      Arrays.asList("1", "2", "3")
    );
    assertEquals(
      service.getQuotedPapersByPaperId("3"),
      Collections.singletonList("1")
    );
    assertEquals(
      service.getQuotedPapersByPaperId("4"),
      Arrays.asList("1", "2", "3", "4", "5", "6", "7")
    );
    assertEquals(
      service.getQuotedPapersByPaperId("5"),
      Collections.emptyList()
    );
    assertEquals(
      service.getQuotedPapersByPaperId("6"),
      Arrays.asList("1", "2", "3")
    );
    assertEquals(
      service.getQuotedPapersByPaperId("7"),
      Arrays.asList("1", "2", "3", "4", "5")
    );
    assertEquals(
      service.getQuotedPapersByPaperId("8"),
      Arrays.asList("1", "2", "3", "4")
    );
  }

  @Test
  public void testGetQuotingPapers() {
    assertEquals(
      service.getQuotingPapersByPaperId("1"),
      Arrays.asList("1", "2", "3", "4", "6", "7", "8")
    );
    assertEquals(
      service.getQuotingPapersByPaperId("2"),
      Arrays.asList("1", "2", "4", "6", "7", "8")
    );
    assertEquals(
      service.getQuotingPapersByPaperId("3"),
      Arrays.asList("1", "2", "4", "6", "7", "8")
    );
    assertEquals(
      service.getQuotingPapersByPaperId("4"),
      Arrays.asList("1", "4", "7", "8")
    );
    assertEquals(
      service.getQuotingPapersByPaperId("5"),
      Arrays.asList("4", "7")
    );
    assertEquals(
      service.getQuotingPapersByPaperId("6"),
      Collections.singletonList("4")
    );
    assertEquals(
      service.getQuotingPapersByPaperId("7"),
      Collections.singletonList("4")
    );
    assertEquals(
      service.getQuotingPapersByPaperId("8"),
      Collections.emptyList()
    );
  }

  @Test
  public void testGetResearcherQuoted() {
    IPaper p1 = mock(IPaper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    IPaper p2 = mock(IPaper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    IPaper p3 = mock(IPaper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    IPaper p4 = mock(IPaper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    IPaper p5 = mock(IPaper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    IPaper p6 = mock(IPaper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    IPaper p7 = mock(IPaper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    IPaper p8 = mock(IPaper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    IResearcher r1 = mock(IResearcher.class);
    r1.setId("rid");
    Mockito
      .when(paperService.getPapers(null, null, null))
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
    Mockito.when(r1.getPapers()).thenReturn(Arrays.asList("1", "2", "3"));
    Map<String, List<String>> map = new HashMap<>();
    map.put("1", Arrays.asList("1", "2", "3", "4"));
    map.put("2", Arrays.asList("1", "2", "3"));
    map.put("3", Collections.singletonList("1"));
    Mockito.when(r1.getId()).thenReturn("R1");
    Mockito.when(researcherService.getResearcherById("R1")).thenReturn(r1);
    assertEquals(service.getQuotedPapersByResearcherId("R1"), map);
  }

  @Test
  public void testGetResearcherQuoting() {
    IPaper p1 = mock(IPaper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    IPaper p2 = mock(IPaper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    IPaper p3 = mock(IPaper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    IPaper p4 = mock(IPaper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    IPaper p5 = mock(IPaper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    IPaper p6 = mock(IPaper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    IPaper p7 = mock(IPaper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    IPaper p8 = mock(IPaper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    IResearcher r1 = mock(IResearcher.class);
    r1.setId("rid");
    Mockito
      .when(paperService.getPapers(null, null, null))
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
    Mockito.when(r1.getPapers()).thenReturn(Arrays.asList("1", "2", "3"));
    Map<String, List<String>> map = new HashMap<>();
    map.put("1", Arrays.asList("1", "2", "3", "4", "6", "7", "8"));
    map.put("2", Arrays.asList("1", "2", "4", "6", "7", "8"));
    map.put("3", Arrays.asList("1", "2", "4", "6", "7", "8"));

    Mockito.when(r1.getId()).thenReturn("R1");
    Mockito.when(researcherService.getResearcherById("R1")).thenReturn(r1);
    assertEquals(service.getQuotingPapersByResearcherId("R1"), map);
  }

  @Test
  public void testGetResearcherQuotedResearcherNum() {
    IResearcher r1 = mock(IResearcher.class);
    Mockito.when(r1.getId()).thenReturn("R1");
    Mockito.when(researcherService.getResearcherById("R1")).thenReturn(r1);
    IResearcher r2 = mock(IResearcher.class);
    Mockito.when(r2.getId()).thenReturn("R2");
    Mockito.when(researcherService.getResearcherById("R2")).thenReturn(r2);
    IResearcher r3 = mock(IResearcher.class);
    Mockito.when(r3.getId()).thenReturn("R3");
    Mockito.when(researcherService.getResearcherById("R3")).thenReturn(r3);
    IPaper p1 = mock(IPaper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    IPaper p2 = mock(IPaper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    IPaper p3 = mock(IPaper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    IPaper p4 = mock(IPaper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    IPaper p5 = mock(IPaper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    IPaper p6 = mock(IPaper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    IPaper p7 = mock(IPaper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    IPaper p8 = mock(IPaper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));

    Mockito
      .when(paperService.getPapers(null, null, null))
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
    Mockito
      .when(p1.getResearchers())
      .thenReturn(Collections.singletonList("R1"));
    Mockito
      .when(p2.getResearchers())
      .thenReturn(Collections.singletonList("R1"));
    Mockito
      .when(p3.getResearchers())
      .thenReturn(Collections.singletonList("R2"));
    Mockito.when(p4.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p5.getResearchers()).thenReturn(Collections.emptyList());
    Mockito
      .when(p6.getResearchers())
      .thenReturn(Collections.singletonList("R3"));
    Mockito.when(p7.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p8.getResearchers()).thenReturn(Collections.emptyList());
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(r1.getPapers()).thenReturn(Arrays.asList("1", "2"));
    Mockito.when(r2.getPapers()).thenReturn(Collections.singletonList("3"));
    Mockito.when(r3.getPapers()).thenReturn(Collections.singletonList("6"));

    Mockito.when(paperService.getPaper("1")).thenReturn(p1);
    Mockito.when(paperService.getPaper("2")).thenReturn(p2);
    Mockito.when(paperService.getPaper("3")).thenReturn(p3);
    Mockito.when(paperService.getPaper("4")).thenReturn(p4);
    Mockito.when(paperService.getPaper("5")).thenReturn(p5);
    Mockito.when(paperService.getPaper("6")).thenReturn(p6);
    Mockito.when(paperService.getPaper("7")).thenReturn(p7);
    Mockito.when(paperService.getPaper("8")).thenReturn(p8);
    Map<String, List<String>> map1 = new HashMap<>();
    map1.put("1", Arrays.asList("R1", "R2"));
    map1.put("2", Arrays.asList("R1", "R2"));
    Map<String, List<String>> map2 = new HashMap<>();
    map2.put("3", Arrays.asList("R1"));
    Map<String, List<String>> map3 = new HashMap<>();
    map3.put("6", Arrays.asList("R1", "R2"));
    Mockito.when(researcherService.getResearcherById("R1")).thenReturn(r1);
    Mockito.when(researcherService.getResearcherById("R2")).thenReturn(r2);
    Mockito.when(researcherService.getResearcherById("R3")).thenReturn(r3);
    assertEquals(
      true,
      sameMap(map1, service.getResearcherPaperQuotedResearcher("R1"))
    );
    assertEquals(
      true,
      sameMap(map2, service.getResearcherPaperQuotedResearcher("R2"))
    );
    assertEquals(
      true,
      sameMap(map3, service.getResearcherPaperQuotedResearcher("R3"))
    );
  }

  @Test
  public void testGetResearcherQuotingResearcherNum() {
    IResearcher r1 = mock(IResearcher.class);
    Mockito.when(r1.getId()).thenReturn("R1");
    Mockito.when(researcherService.getResearcherById("R1")).thenReturn(r1);
    IResearcher r2 = mock(IResearcher.class);
    Mockito.when(r2.getId()).thenReturn("R2");
    Mockito.when(researcherService.getResearcherById("R2")).thenReturn(r2);
    IResearcher r3 = mock(IResearcher.class);
    Mockito.when(r3.getId()).thenReturn("R3");
    Mockito.when(researcherService.getResearcherById("R3")).thenReturn(r3);
    IPaper p1 = mock(IPaper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    IPaper p2 = mock(IPaper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    IPaper p3 = mock(IPaper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    IPaper p4 = mock(IPaper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    IPaper p5 = mock(IPaper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    IPaper p6 = mock(IPaper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    IPaper p7 = mock(IPaper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    IPaper p8 = mock(IPaper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));

    Mockito
      .when(paperService.getPapers(null, null, null))
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
    Mockito
      .when(p1.getResearchers())
      .thenReturn(Collections.singletonList("R1"));
    Mockito
      .when(p2.getResearchers())
      .thenReturn(Collections.singletonList("R1"));
    Mockito
      .when(p3.getResearchers())
      .thenReturn(Collections.singletonList("R2"));
    Mockito.when(p4.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p5.getResearchers()).thenReturn(Collections.emptyList());
    Mockito
      .when(p6.getResearchers())
      .thenReturn(Collections.singletonList("R3"));
    Mockito.when(p7.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p8.getResearchers()).thenReturn(Collections.emptyList());
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(r1.getPapers()).thenReturn(Arrays.asList("1", "2"));
    Mockito.when(r2.getPapers()).thenReturn(Collections.singletonList("3"));
    Mockito.when(r3.getPapers()).thenReturn(Collections.singletonList("6"));
    Map<String, List<String>> map1 = new HashMap<>();
    map1.put("1", Arrays.asList("R1", "R2", "R3"));
    map1.put("2", Arrays.asList("R1", "R3"));
    Map<String, List<String>> map2 = new HashMap<>();
    map2.put("3", Arrays.asList("R1", "R3"));
    Map<String, List<String>> map3 = new HashMap<>();
    map3.put("6", Collections.emptyList());

    Mockito.when(paperService.getPaper("1")).thenReturn(p1);
    Mockito.when(paperService.getPaper("2")).thenReturn(p2);
    Mockito.when(paperService.getPaper("3")).thenReturn(p3);
    Mockito.when(paperService.getPaper("4")).thenReturn(p4);
    Mockito.when(paperService.getPaper("5")).thenReturn(p5);
    Mockito.when(paperService.getPaper("6")).thenReturn(p6);
    Mockito.when(paperService.getPaper("7")).thenReturn(p7);
    Mockito.when(paperService.getPaper("8")).thenReturn(p8);
    assertEquals(
      true,
      sameMap(map1, service.getResearcherPaperQuotingResearcher("R1"))
    );
    assertEquals(
      true,
      sameMap(map2, service.getResearcherPaperQuotingResearcher("R2"))
    );
    assertEquals(
      true,
      sameMap(map3, service.getResearcherPaperQuotingResearcher("R3"))
    );
  }

  boolean sameMap(
    Map<String, List<String>> map1,
    Map<String, List<String>> map2
  ) {
    if (map1.size() != map2.size()) {
      return false;
    }
    for (String s : map1.keySet()) {
      var l1 = map1.get(s);
      var l2 = map2.get(s);
      if (l1 == null || l2 == null) return false;
      Collections.sort(l1);
      Collections.sort(l2);
      if (!l1.equals(l2)) {
        return false;
      }
    }
    return true;
  }
}
