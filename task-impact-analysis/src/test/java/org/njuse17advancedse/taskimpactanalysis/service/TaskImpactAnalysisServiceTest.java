package org.njuse17advancedse.taskimpactanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.netflix.client.IResponse;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskimpactanalysis.data.AllRepository;
import org.njuse17advancedse.taskimpactanalysis.dto.ICitation;
import org.njuse17advancedse.taskimpactanalysis.dto.IPaper;
import org.njuse17advancedse.taskimpactanalysis.dto.IResearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class TaskImpactAnalysisServiceTest {
  @MockBean
  PaperService paperService;

  @Autowired
  TaskImpactAnalysisService service;

  @MockBean
  AllRepository repository;

  //  @BeforeEach
  //  public void init(){
  //    paperService=mock(FakeService.class);
  //  }
  @Test
  public void testGetHIndex1() {
    Mockito.when(repository.existsResearcherById("skt")).thenReturn(true);
    List<Integer> citations = Arrays.asList(4, 3, 1, 7, 0, 4, 5, 4);
    //    citations.add(new ICitation("1", 4));
    //    citations.add(new ICitation("2", 3));
    //    citations.add(new ICitation("3", 1));
    //    citations.add(new ICitation("4", 7));
    //    citations.add(new ICitation("5", 0));
    //    citations.add(new ICitation("6", 3));
    //    citations.add(new ICitation("7", 5));
    //    citations.add(new ICitation("8", 4));
    Mockito.when(repository.getPaperQuotingTimes("skt")).thenReturn(citations);
    try {
      assertEquals(service.getHIndex("skt"), 4);
    } catch (Exception e) {
      assertEquals(1, 2);
    }
  }

  @Test
  public void testGetHIndex2() {
    Mockito.when(repository.existsResearcherById("skt")).thenReturn(true);
    List<Integer> citations = Arrays.asList(4, 3, 1, 7, 0);
    //    List<ICitation> citations = new ArrayList<>();
    //    citations.add(new ICitation("1", 4));
    //    citations.add(new ICitation("2", 3));
    //    citations.add(new ICitation("3", 1));
    //    citations.add(new ICitation("4", 7));
    //    citations.add(new ICitation("5", 0));
    Mockito.when(repository.getPaperQuotingTimes("skt")).thenReturn(citations);
    try {
      assertEquals(service.getHIndex("skt"), 3);
    } catch (Exception e) {
      assertEquals(1, 2);
    }
  }

  @Test
  public void testGetPaperImpact() {
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
    Mockito.when(paperService.getPaper("1")).thenReturn(p1);
    Mockito.when(paperService.getPaper("2")).thenReturn(p2);
    Mockito.when(paperService.getPaper("3")).thenReturn(p3);
    Mockito.when(paperService.getPaper("4")).thenReturn(p4);
    Mockito.when(paperService.getPaper("5")).thenReturn(p5);
    Mockito.when(paperService.getPaper("6")).thenReturn(p6);
    Mockito.when(paperService.getPaper("7")).thenReturn(p7);
    Mockito.when(paperService.getPaper("8")).thenReturn(p8);
    Mockito
      .when(paperService.getCitations("1"))
      .thenReturn(Arrays.asList("1", "2", "3", "4"));
    Mockito.when(p1.getPublication()).thenReturn("");
    Mockito
      .when(paperService.getCitations("2"))
      .thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p2.getPublication()).thenReturn("");
    Mockito.when(paperService.getCitations("3")).thenReturn(Arrays.asList("1"));
    Mockito.when(p3.getPublication()).thenReturn("");
    Mockito
      .when(paperService.getCitations("4"))
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    Mockito.when(p4.getPublication()).thenReturn("");
    Mockito.when(paperService.getCitations("5")).thenReturn(Arrays.asList());
    Mockito.when(p5.getPublication()).thenReturn("");
    Mockito
      .when(paperService.getCitations("6"))
      .thenReturn(Arrays.asList("1", "2", "3"));
    Mockito.when(p6.getPublication()).thenReturn("");
    Mockito
      .when(paperService.getCitations("7"))
      .thenReturn(Arrays.asList("1", "2", "3", "4", "5"));
    Mockito.when(p7.getPublication()).thenReturn("");
    Mockito
      .when(paperService.getCitations("8"))
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
  //    @Autowired
  //    AllRepository repository;
  //    @Test
  //    public void testSQL(){
  //      for(int i=0;i<10;i++){
  //      Date date=new Date();
  //      System.out.println(service.getHIndex("37598376400"));
  //      Date date2=new Date();
  //      System.out.println(date2.getTime()-date.getTime());}
  //    }
}
