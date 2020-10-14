package org.njuse17advancedse.taskcitationanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskcitationanalysis.entity.Paper;
import org.njuse17advancedse.taskcitationanalysis.entity.Researcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TaskCitationAnalysisServiceTest {
  @MockBean
  FakeService fakeService;

  @Autowired
  TaskCitationAnalysisService service;

  @BeforeEach
  public void init() {
    Paper p1 = mock(Paper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    Paper p2 = mock(Paper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    Paper p3 = mock(Paper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    Paper p4 = mock(Paper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    Paper p5 = mock(Paper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    Paper p6 = mock(Paper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    Paper p7 = mock(Paper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    Paper p8 = mock(Paper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito
      .when(p1.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getQuotedIds()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getQuotedIds()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Researcher r = mock(Researcher.class);
    r.setId("rid");
    Mockito
      .when(fakeService.getAllPapers())
      .thenReturn(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));
    service.init();
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
    Researcher r1 = mock(Researcher.class);
    r1.setId("R1");
    Mockito.when(fakeService.getResearcherById("R1")).thenReturn(r1);
    Paper p1 = mock(Paper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    Paper p2 = mock(Paper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    Paper p3 = mock(Paper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    Paper p4 = mock(Paper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    Paper p5 = mock(Paper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    Paper p6 = mock(Paper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    Paper p7 = mock(Paper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    Paper p8 = mock(Paper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito
      .when(p1.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getQuotedIds()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getQuotedIds()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(r1.getPapers()).thenReturn(Arrays.asList(p1, p2, p3));
    Map<String, List<String>> map = new HashMap<>();
    map.put("1", Arrays.asList("1", "2", "3", "4"));
    map.put("2", Arrays.asList("1", "2", "3"));
    map.put("3", Collections.singletonList("1"));
    assertEquals(service.getQuotedPapersByResearcherId("R1"), map);
  }

  @Test
  public void testGetResearcherQuoting() {
    Researcher r1 = mock(Researcher.class);
    r1.setId("R1");
    Mockito.when(fakeService.getResearcherById("R1")).thenReturn(r1);
    Paper p1 = mock(Paper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    Paper p2 = mock(Paper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    Paper p3 = mock(Paper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    Paper p4 = mock(Paper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    Paper p5 = mock(Paper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    Paper p6 = mock(Paper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    Paper p7 = mock(Paper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    Paper p8 = mock(Paper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito
      .when(p1.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getQuotedIds()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getQuotedIds()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(r1.getPapers()).thenReturn(Arrays.asList(p1, p2, p3));
    Map<String, List<String>> map = new HashMap<>();
    map.put("1", Arrays.asList("1", "2", "3", "4", "6", "7", "8"));
    map.put("2", Arrays.asList("1", "2", "4", "6", "7", "8"));
    map.put("3", Arrays.asList("1", "2", "4", "6", "7", "8"));
    assertEquals(service.getQuotingPapersByResearcherId("R1"), map);
  }

  @Test
  public void testGetResearcherQuotedResearcherNum() {
    Researcher r1 = mock(Researcher.class);
    Mockito.when(r1.getId()).thenReturn("R1");
    Mockito.when(fakeService.getResearcherById("R1")).thenReturn(r1);
    Researcher r2 = mock(Researcher.class);
    Mockito.when(r2.getId()).thenReturn("R2");
    Mockito.when(fakeService.getResearcherById("R2")).thenReturn(r2);
    Researcher r3 = mock(Researcher.class);
    Mockito.when(r3.getId()).thenReturn("R3");
    Mockito.when(fakeService.getResearcherById("R3")).thenReturn(r3);
    Paper p1 = mock(Paper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    Mockito.when(fakeService.getPaperById("1")).thenReturn(p1);
    Paper p2 = mock(Paper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    Mockito.when(fakeService.getPaperById("2")).thenReturn(p2);
    Paper p3 = mock(Paper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    Mockito.when(fakeService.getPaperById("3")).thenReturn(p3);
    Paper p4 = mock(Paper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    Mockito.when(fakeService.getPaperById("4")).thenReturn(p4);
    Paper p5 = mock(Paper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    Mockito.when(fakeService.getPaperById("5")).thenReturn(p5);
    Paper p6 = mock(Paper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    Mockito.when(fakeService.getPaperById("6")).thenReturn(p6);
    Paper p7 = mock(Paper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    Mockito.when(fakeService.getPaperById("7")).thenReturn(p7);
    Paper p8 = mock(Paper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito.when(fakeService.getPaperById("8")).thenReturn(p8);
    Mockito.when(p1.getResearchers()).thenReturn(Collections.singletonList(r1));
    Mockito.when(p2.getResearchers()).thenReturn(Collections.singletonList(r1));
    Mockito.when(p3.getResearchers()).thenReturn(Collections.singletonList(r2));
    Mockito.when(p4.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p5.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getResearchers()).thenReturn(Collections.singletonList(r3));
    Mockito.when(p7.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p8.getResearchers()).thenReturn(Collections.emptyList());
    Mockito
      .when(p1.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getQuotedIds()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getQuotedIds()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(r1.getPapers()).thenReturn(Arrays.asList(p1, p2));
    Mockito.when(r2.getPapers()).thenReturn(Collections.singletonList(p3));
    Mockito.when(r3.getPapers()).thenReturn(Collections.singletonList(p6));
    Map<String, Integer> map1 = new HashMap<>();
    map1.put("R1", 4);
    map1.put("R2", 2);
    Map<String, Integer> map2 = new HashMap<>();
    map2.put("R1", 1);
    Map<String, Integer> map3 = new HashMap<>();
    map3.put("R1", 2);
    map3.put("R2", 1);
    assertEquals(map1, service.getResearcherQuotedResearcherNums("R1"));
    assertEquals(map2, service.getResearcherQuotedResearcherNums("R2"));
    assertEquals(map3, service.getResearcherQuotedResearcherNums("R3"));
  }

  @Test
  public void testGetResearcherQuotingResearcherNum() {
    Researcher r1 = mock(Researcher.class);
    Mockito.when(r1.getId()).thenReturn("R1");
    Mockito.when(fakeService.getResearcherById("R1")).thenReturn(r1);
    Researcher r2 = mock(Researcher.class);
    Mockito.when(r2.getId()).thenReturn("R2");
    Mockito.when(fakeService.getResearcherById("R2")).thenReturn(r2);
    Researcher r3 = mock(Researcher.class);
    Mockito.when(r3.getId()).thenReturn("R3");
    Mockito.when(fakeService.getResearcherById("R3")).thenReturn(r3);
    Paper p1 = mock(Paper.class);
    p1.setId("1");
    Mockito.when(p1.getId()).thenReturn("1");
    Mockito.when(fakeService.getPaperById("1")).thenReturn(p1);
    Paper p2 = mock(Paper.class);
    p2.setId("2");
    Mockito.when(p2.getId()).thenReturn("2");
    Mockito.when(fakeService.getPaperById("2")).thenReturn(p2);
    Paper p3 = mock(Paper.class);
    p3.setId("3");
    Mockito.when(p3.getId()).thenReturn("3");
    Mockito.when(fakeService.getPaperById("3")).thenReturn(p3);
    Paper p4 = mock(Paper.class);
    p4.setId("4");
    Mockito.when(p4.getId()).thenReturn("4");
    Mockito.when(fakeService.getPaperById("4")).thenReturn(p4);
    Paper p5 = mock(Paper.class);
    p5.setId("5");
    Mockito.when(p5.getId()).thenReturn("5");
    Mockito.when(fakeService.getPaperById("5")).thenReturn(p5);
    Paper p6 = mock(Paper.class);
    p6.setId("6");
    Mockito.when(p6.getId()).thenReturn("6");
    Mockito.when(fakeService.getPaperById("6")).thenReturn(p6);
    Paper p7 = mock(Paper.class);
    p7.setId("7");
    Mockito.when(p7.getId()).thenReturn("7");
    Mockito.when(fakeService.getPaperById("7")).thenReturn(p7);
    Paper p8 = mock(Paper.class);
    p8.setId("8");
    Mockito.when(p8.getId()).thenReturn("8");
    Mockito.when(fakeService.getPaperById("8")).thenReturn(p8);
    Mockito.when(p1.getResearchers()).thenReturn(Collections.singletonList(r1));
    Mockito.when(p2.getResearchers()).thenReturn(Collections.singletonList(r1));
    Mockito.when(p3.getResearchers()).thenReturn(Collections.singletonList(r2));
    Mockito.when(p4.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p5.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getResearchers()).thenReturn(Collections.singletonList(r3));
    Mockito.when(p7.getResearchers()).thenReturn(Collections.emptyList());
    Mockito.when(p8.getResearchers()).thenReturn(Collections.emptyList());
    Mockito
      .when(p1.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getQuotedIds()).thenReturn(Collections.singletonList("1"));
    Mockito
      .when(p4.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getQuotedIds()).thenReturn(Collections.emptyList());
    Mockito.when(p6.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(r1.getPapers()).thenReturn(Arrays.asList(p1, p2));
    Mockito.when(r2.getPapers()).thenReturn(Collections.singletonList(p3));
    Mockito.when(r3.getPapers()).thenReturn(Collections.singletonList(p6));
    Map<String, Integer> map1 = new HashMap<>();
    map1.put("R1", 4);
    map1.put("R2", 1);
    map1.put("R3", 2);
    Map<String, Integer> map2 = new HashMap<>();
    map2.put("R1", 2);
    map2.put("R3", 1);
    Map<String, Integer> map3 = new HashMap<>();
    assertEquals(map1, service.getResearcherQuotingResearcherNums("R1"));
    assertEquals(map2, service.getResearcherQuotingResearcherNums("R2"));
    assertEquals(map3, service.getResearcherQuotingResearcherNums("R3"));
  }
}
