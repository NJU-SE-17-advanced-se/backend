package org.njuse17advancedse.taskimpactanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskimpactanalysis.entity.Paper;
import org.njuse17advancedse.taskimpactanalysis.entity.Researcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TaskImpactAnalysisServiceTest {
  @MockBean
  FakeService fakeService;

  @Autowired
  TaskImpactAnalysisService service;

  //  @BeforeEach
  //  public void init(){
  //    fakeService=mock(FakeService.class);
  //  }
  @Test
  public void testGetHIndex1() {
    Paper p1 = mock(Paper.class);
    p1.setId("1");
    Paper p2 = mock(Paper.class);
    p2.setId("2");
    Paper p3 = mock(Paper.class);
    p3.setId("3");
    Paper p4 = mock(Paper.class);
    p4.setId("4");
    Paper p5 = mock(Paper.class);
    p5.setId("5");
    Paper p6 = mock(Paper.class);
    p6.setId("6");
    Paper p7 = mock(Paper.class);
    p7.setId("7");
    Paper p8 = mock(Paper.class);
    p8.setId("8");
    Mockito
      .when(p1.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getQuotedIds()).thenReturn(Arrays.asList("1"));
    Mockito
      .when(p4.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getQuotedIds()).thenReturn(Arrays.asList());
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
      .when(r.getPapers())
      .thenReturn(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));
    Mockito.when(fakeService.getPaperById("1")).thenReturn(p1);
    Mockito.when(fakeService.getPaperById("2")).thenReturn(p2);
    Mockito.when(fakeService.getPaperById("3")).thenReturn(p3);
    Mockito.when(fakeService.getPaperById("4")).thenReturn(p4);
    Mockito.when(fakeService.getPaperById("5")).thenReturn(p5);
    Mockito.when(fakeService.getPaperById("6")).thenReturn(p6);
    Mockito.when(fakeService.getPaperById("7")).thenReturn(p7);
    Mockito.when(fakeService.getPaperById("8")).thenReturn(p8);
    Mockito.when(fakeService.getResearcherById("rid")).thenReturn(r);
    assertEquals(service.getHIndex("rid"), 4);
  }

  @Test
  public void testGetHIndex2() {
    Paper p1 = mock(Paper.class);
    p1.setId("1");
    Paper p2 = mock(Paper.class);
    p2.setId("2");
    Paper p3 = mock(Paper.class);
    p3.setId("3");
    Paper p4 = mock(Paper.class);
    p4.setId("4");
    Paper p5 = mock(Paper.class);
    p5.setId("5");
    Mockito
      .when(p1.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p2.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p3.getQuotedIds()).thenReturn(Arrays.asList("1"));
    Mockito
      .when(p4.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p5.getQuotedIds()).thenReturn(Arrays.asList());
    Researcher r = mock(Researcher.class);
    r.setId("rid");
    Mockito.when(r.getPapers()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5));
    Mockito.when(fakeService.getPaperById("1")).thenReturn(p1);
    Mockito.when(fakeService.getPaperById("2")).thenReturn(p2);
    Mockito.when(fakeService.getPaperById("3")).thenReturn(p3);
    Mockito.when(fakeService.getPaperById("4")).thenReturn(p4);
    Mockito.when(fakeService.getPaperById("5")).thenReturn(p5);
    Mockito.when(fakeService.getResearcherById("rid")).thenReturn(r);
    assertEquals(service.getHIndex("rid"), 3);
  }

  @Test
  public void testGetPaperImpact() {
    Paper p1 = mock(Paper.class);
    p1.setId("1");
    Paper p2 = mock(Paper.class);
    p2.setId("2");
    Paper p3 = mock(Paper.class);
    p3.setId("3");
    Paper p4 = mock(Paper.class);
    p4.setId("4");
    Paper p5 = mock(Paper.class);
    p5.setId("5");
    Paper p6 = mock(Paper.class);
    p6.setId("6");
    Paper p7 = mock(Paper.class);
    p7.setId("7");
    Paper p8 = mock(Paper.class);
    p8.setId("8");
    Mockito.when(fakeService.getPaperById("1")).thenReturn(p1);
    Mockito.when(fakeService.getPaperById("2")).thenReturn(p2);
    Mockito.when(fakeService.getPaperById("3")).thenReturn(p3);
    Mockito.when(fakeService.getPaperById("4")).thenReturn(p4);
    Mockito.when(fakeService.getPaperById("5")).thenReturn(p5);
    Mockito.when(fakeService.getPaperById("6")).thenReturn(p6);
    Mockito.when(fakeService.getPaperById("7")).thenReturn(p7);
    Mockito.when(fakeService.getPaperById("8")).thenReturn(p8);
    Mockito
      .when(p1.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p1.getJournal()).thenReturn("");
    Mockito.when(p2.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p2.getJournal()).thenReturn("");
    Mockito.when(p3.getQuotedIds()).thenReturn(Arrays.asList("1"));
    Mockito.when(p3.getJournal()).thenReturn("");
    Mockito
      .when(p4.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p4.getJournal()).thenReturn("");
    Mockito.when(p5.getQuotedIds()).thenReturn(Arrays.asList());
    Mockito.when(p5.getJournal()).thenReturn("");
    Mockito.when(p6.getQuotedIds()).thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p6.getJournal()).thenReturn("");
    Mockito
      .when(p7.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito.when(p7.getJournal()).thenReturn("");
    Mockito
      .when(p8.getQuotedIds())
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p8.getJournal()).thenReturn("");

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
