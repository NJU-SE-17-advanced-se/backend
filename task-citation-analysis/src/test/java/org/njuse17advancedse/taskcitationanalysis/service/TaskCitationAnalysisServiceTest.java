package org.njuse17advancedse.taskcitationanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.*;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskcitationanalysis.data.CitationAnalysisRepository;
import org.njuse17advancedse.taskcitationanalysis.dto.IPaper;
import org.njuse17advancedse.taskcitationanalysis.dto.IResearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class TaskCitationAnalysisServiceTest {
  @Autowired
  TaskCitationAnalysisService service;

  @MockBean
  CitationAnalysisRepository repository;

  @BeforeEach
  public void init() {
    Mockito.when(repository.existsPaperById("pid")).thenReturn(true);
    Mockito.when(repository.existsResearcherById("rid")).thenReturn(true);
  }

  @Test
  public void testGetQuotedPapers() {
    Mockito
      .when(repository.getQuotedPapersByPaperId("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      service.getQuotedPapersByPaperId("pid"),
      Arrays.asList("1", "test1")
    );
  }

  @Test
  public void testGetQuotingPapers() {
    Mockito
      .when(repository.getQuotingPapersByPaperId("pid"))
      .thenReturn(Arrays.asList("2", "test2"));
    assertEquals(
      service.getQuotingPapersByPaperId("pid"),
      Arrays.asList("2", "test2")
    );
  }

  @Test
  public void testGetQuotedResearchers() {
    Mockito
      .when(repository.getPaperQuotedResearcher("pid"))
      .thenReturn(Arrays.asList("1", "test1"));
    assertEquals(
      service.getPaperQuotedResearcher("pid"),
      Arrays.asList("1", "test1")
    );
  }

  @Test
  public void testGetQuotingResearchers() {
    Mockito
      .when(repository.getPaperQuotingResearcher("pid"))
      .thenReturn(Arrays.asList("2", "test2"));
    assertEquals(
      service.getPaperQuotingResearcher("pid"),
      Arrays.asList("2", "test2")
    );
  }

  @Test
  public void testGetResearcherPaperQuotedPapers() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid1", Arrays.asList("1", "test1"));
    map.put("pid2", Arrays.asList("2", "test2"));
    Mockito
      .when(repository.getResearcherPaperQuotedPapers("rid"))
      .thenReturn(map);
    assertEquals(service.getQuotedPapersByResearcherId("rid"), map);
  }

  @Test
  public void testGetResearcherPaperQuotingPapers() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid3", Arrays.asList("4", "test1"));
    map.put("pid4", Arrays.asList("2", "test9"));
    map.put("pid5", Arrays.asList("sada", "faker"));
    Mockito
      .when(repository.getResearcherPaperQuotingPapers("rid"))
      .thenReturn(map);
    assertEquals(service.getQuotingPapersByResearcherId("rid"), map);
  }

  @Test
  public void testGetResearcherPaperQuotedResearcher() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid1", Arrays.asList("1", "test1"));
    map.put("pid2", Arrays.asList("2", "test2"));
    Mockito
      .when(repository.getResearcherPaperQuotedResearchers("rid"))
      .thenReturn(map);
    assertEquals(service.getResearcherPaperQuotedResearcher("rid"), map);
  }

  @Test
  public void testGetResearcherPaperQuotingResearcher() {
    HashMap<String, List<String>> map = new HashMap<>();
    map.put("pid3", Arrays.asList("4", "test1"));
    map.put("pid4", Arrays.asList("2", "test9"));
    map.put("pid5", Arrays.asList("sada", "faker"));
    Mockito
      .when(repository.getResearcherPaperQuotingResearchers("rid"))
      .thenReturn(map);
    assertEquals(service.getResearcherPaperQuotingResearcher("rid"), map);
  }

  @Test
  public void testGetResearcherQuotedResearcher() {
    List<String> list = Arrays.asList("1", "2", "3");
    Mockito
      .when(repository.getResearcherQuotedResearcher("rid"))
      .thenReturn(list);
    assertEquals(list, service.getResearcherQuotedResearcher("rid"));
  }

  @Test
  public void testGetResearcherQuotingResearcher() {
    List<String> list = Arrays.asList("1", "2", "3");
    Mockito
      .when(repository.getResearcherQuotingResearcher("rid"))
      .thenReturn(list);
    assertEquals(list, service.getResearcherQuotingResearcher("rid"));
  }
  //  @Autowired
  //  CitationAnalysisRepository repository;
  //  @Test
  //  public void testSQL(){
  //    for(int i=0;i<3;i++){
  //    Date date=new Date();
  //    List<String> papers=repository.getPapersByResearcherId("37598376400");
  //    Date date1=new Date();
  //    System.out.println(date1.getTime()-date.getTime());
  //    Map<String,List<String>> pqr=new HashMap<>();
  //    for(String pid:papers){
  //      pqr.put(pid,repository.getQuotedPapersByPaperId(pid));
  //    }
  //    for(String key:papers){
  //      if(!pqr.containsKey(key)){
  //        pqr.put(key,new ArrayList<>());
  //      }}
  //    Date date2=new Date();
  //    System.out.println(date2.getTime()-date1.getTime());
  //
  //    var rs=repository.getResearcherPaperQuotedPapers("37598376400");
  //    Date date3=new Date();
  //    System.out.println(date3.getTime()-date2.getTime());
  //    assertEquals(true,sameMap(rs,pqr));
  //    System.out.println("-*-*-*-*-");}
  //    System.out.println();
  //
  //    for(int i=0;i<5;i++) {
  //      System.out.println("asdasdasdasdasd");
  //      Date d1 = new Date();
  //      List<String> res = repository.getResearcherQuotedResearcher("37598376400");
  //      Date d2 = new Date();
  //      System.out.println("time1: " + (d2.getTime() - d1.getTime()));
  //      List<String> res1 = repository.getResearcherQuotingResearcher("37598376400");
  //      Date d3 = new Date();
  //      System.out.println("time2: " + (d3.getTime() - d1.getTime()));
  //    }
  //    System.out.println();
  //  }
  //
  //  @Test
  //  public void testGetResearcherItemsWithDB(){
  //    List<String> papers=repository.getPapersByResearcherId("37598376400");
  //    Map<String,List<String>> pqr=new HashMap<>();
  //    for(String pid:papers){
  //      pqr.put(pid,repository.getQuotedPapersByPaperId(pid));
  //    }
  //    for(String key:papers){
  //      if(!pqr.containsKey(key)){
  //        pqr.put(key,new ArrayList<>());
  //      }
  //    }
  //
  //    var rs1=repository.getResearcherPaperQuotedPapers("37598376400");
  //    assertEquals(true,sameMap(pqr,rs1));
  //
  //    pqr=new HashMap<>();
  //    for(String pid:papers){
  //      pqr.put(pid,repository.getQuotingPapersByPaperId(pid));
  //    }
  //    for(String key:papers){
  //      if(!pqr.containsKey(key)){
  //        pqr.put(key,new ArrayList<>());
  //      }
  //    }
  //
  //    var rs2=repository.getResearcherPaperQuotingPapers("37598376400");
  //    assertEquals(true,sameMap(pqr,rs2));
  //
  //    pqr=new HashMap<>();
  //    for(String pid:papers){
  //      pqr.put(pid,repository.getPaperQuotingResearcher(pid));
  //    }
  //    for(String key:papers){
  //      if(!pqr.containsKey(key)){
  //        pqr.put(key,new ArrayList<>());
  //      }
  //    }
  //    var rs3=repository.getResearcherPaperQuotingResearchers("37598376400");
  //    assertEquals(true,sameMap(pqr,rs3));
  //
  //    pqr=new HashMap<>();
  //    for(String pid:papers){
  //      pqr.put(pid,repository.getPaperQuotedResearcher(pid));
  //    }
  //    for(String key:papers){
  //      if(!pqr.containsKey(key)){
  //        pqr.put(key,new ArrayList<>());
  //      }
  //    }
  //    var rs4=repository.getResearcherPaperQuotedResearchers("37598376400");
  //    assertEquals(true,sameMap(pqr,rs4));
  //
  //
  //
  //  }
  //
  //
  //
  //  boolean sameMap(
  //    Map<String, List<String>> map1,
  //    Map<String, List<String>> map2
  //  ) {
  //    if (map1.size() != map2.size()) {
  //      return false;
  //    }
  //    for (String s : map1.keySet()) {
  //      List<String> l1 = map1.get(s);
  //      List<String> l2 = map2.get(s);
  //      if (l1 == null || l2 == null) return false;
  //      Collections.sort(l1);
  //      Collections.sort(l2);
  //      if (!l1.equals(l2)) {
  //        return false;
  //      }
  //    }
  //    return true;
  //  }
}
