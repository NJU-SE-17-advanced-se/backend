package org.njuse17advancedse.taskimpactanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskimpactanalysis.dto.IPaper;
import org.njuse17advancedse.taskimpactanalysis.dto.IResearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TaskImpactAnalysisServiceTest {
  @MockBean
  PaperService paperService;

  @MockBean
  ResearcherService researcherService;

  @Autowired
  TaskImpactAnalysisService service;

  //  @BeforeEach
  //  public void init(){
  //    paperService=mock(FakeService.class);
  //  }
  @Test
  public void testGetHIndex1() {
    IPaper p1 = mock(IPaper.class);
    p1.setId("1");
    IPaper p2 = mock(IPaper.class);
    p2.setId("2");
    IPaper p3 = mock(IPaper.class);
    p3.setId("3");
    IPaper p4 = mock(IPaper.class);
    p4.setId("4");
    IPaper p5 = mock(IPaper.class);
    p5.setId("5");
    IPaper p6 = mock(IPaper.class);
    p6.setId("6");
    IPaper p7 = mock(IPaper.class);
    p7.setId("7");
    IPaper p8 = mock(IPaper.class);
    p8.setId("8");
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Arrays.asList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Arrays.asList());
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    IResearcher r = mock(IResearcher.class);
    r.setId("rid");
    Mockito
      .when(r.getPapers())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
    Mockito.when(paperService.getPaper("1")).thenReturn(p1);
    Mockito.when(paperService.getPaper("2")).thenReturn(p2);
    Mockito.when(paperService.getPaper("3")).thenReturn(p3);
    Mockito.when(paperService.getPaper("4")).thenReturn(p4);
    Mockito.when(paperService.getPaper("5")).thenReturn(p5);
    Mockito.when(paperService.getPaper("6")).thenReturn(p6);
    Mockito.when(paperService.getPaper("7")).thenReturn(p7);
    Mockito.when(paperService.getPaper("8")).thenReturn(p8);
    Mockito.when(researcherService.getResearcherById("rid")).thenReturn(r);
    assertEquals(service.getHIndex("rid"), 4);
  }

  @Test
  public void testGetHIndex2() {
    IPaper p1 = mock(IPaper.class);
    p1.setId("1");
    IPaper p2 = mock(IPaper.class);
    p2.setId("2");
    IPaper p3 = mock(IPaper.class);
    p3.setId("3");
    IPaper p4 = mock(IPaper.class);
    p4.setId("4");
    IPaper p5 = mock(IPaper.class);
    p5.setId("5");
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getReferences()).thenReturn(Arrays.asList("1"));
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getReferences()).thenReturn(Arrays.asList());
    IResearcher r = mock(IResearcher.class);
    r.setId("rid");
    Mockito
      .when(r.getPapers())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito.when(paperService.getPaper("1")).thenReturn(p1);
    Mockito.when(paperService.getPaper("2")).thenReturn(p2);
    Mockito.when(paperService.getPaper("3")).thenReturn(p3);
    Mockito.when(paperService.getPaper("4")).thenReturn(p4);
    Mockito.when(paperService.getPaper("5")).thenReturn(p5);
    Mockito.when(researcherService.getResearcherById("rid")).thenReturn(r);
    assertEquals(service.getHIndex("rid"), 3);
  }

  @Test
  public void testGetPaperImpact() {
    IPaper p1 = mock(IPaper.class);
    p1.setId("1");
    IPaper p2 = mock(IPaper.class);
    p2.setId("2");
    IPaper p3 = mock(IPaper.class);
    p3.setId("3");
    IPaper p4 = mock(IPaper.class);
    p4.setId("4");
    IPaper p5 = mock(IPaper.class);
    p5.setId("5");
    IPaper p6 = mock(IPaper.class);
    p6.setId("6");
    IPaper p7 = mock(IPaper.class);
    p7.setId("7");
    IPaper p8 = mock(IPaper.class);
    p8.setId("8");
    Mockito.when(paperService.getPaper("1")).thenReturn(p1);
    Mockito.when(paperService.getPaper("2")).thenReturn(p2);
    Mockito.when(paperService.getPaper("3")).thenReturn(p3);
    Mockito.when(paperService.getPaper("4")).thenReturn(p4);
    Mockito.when(paperService.getPaper("5")).thenReturn(p5);
    Mockito.when(paperService.getPaper("6")).thenReturn(p6);
    Mockito.when(paperService.getPaper("7")).thenReturn(p7);
    Mockito.when(paperService.getPaper("8")).thenReturn(p8);
    Mockito
      .when(p1.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p1.getPublication()).thenReturn("");
    Mockito.when(p2.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p2.getPublication()).thenReturn("");
    Mockito.when(p3.getReferences()).thenReturn(Arrays.asList("1"));
    Mockito.when(p3.getPublication()).thenReturn("");
    Mockito
      .when(p4.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p4.getPublication()).thenReturn("");
    Mockito.when(p5.getReferences()).thenReturn(Arrays.asList());
    Mockito.when(p5.getPublication()).thenReturn("");
    Mockito.when(p6.getReferences()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p6.getPublication()).thenReturn("");
    Mockito
      .when(p7.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito.when(p7.getPublication()).thenReturn("");
    Mockito
      .when(p8.getReferences())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p8.getPublication()).thenReturn("");

    assertEquals(service.getPaperImpact("1"), 4);
    assertEquals(service.getPaperImpact("2"), 3);
    assertEquals(service.getPaperImpact("3"), 1);
    assertEquals(service.getPaperImpact("4"), 7);
    assertEquals(service.getPaperImpact("5"), 0);
    assertEquals(service.getPaperImpact("6"), 3);
    assertEquals(service.getPaperImpact("7"), 5);
    assertEquals(service.getPaperImpact("8"), 4);
  }
}
