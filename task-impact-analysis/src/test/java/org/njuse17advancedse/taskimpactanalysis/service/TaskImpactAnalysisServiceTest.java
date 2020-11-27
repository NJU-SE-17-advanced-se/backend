package org.njuse17advancedse.taskimpactanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskimpactanalysis.data.AllRepository;
import org.njuse17advancedse.taskimpactanalysis.dto.IPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class TaskImpactAnalysisServiceTest {
  @Autowired
  TaskImpactAnalysisService service;

  @MockBean
  AllRepository repository;

  @BeforeEach
  public void init() {
    Mockito.when(repository.existsPaperById("SKT")).thenReturn(true);
  }

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
    Mockito.when(repository.getSinglePaperQuotingTimes("SKT")).thenReturn(12);
    assertEquals(service.getPaperImpact("SKT"), 12d);
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
