package org.njuse17advancedse.entitypaper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitypaper.data.AllRepository;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class EntityPaperServiceTest {
  @Autowired
  PaperService service;

  @MockBean
  //    @Autowired
  AllRepository repository;

  IPaper p1;
  IPaperBasic pb1;

  @BeforeEach
  public void init() {
    p1 = new IPaper();
    p1.setId("test1");
    p1.setTitle("title");
    p1.setAbs("abstract");
    p1.setLink("www.4399.com");
    p1.setPublication("publication");
    p1.setPublicationDate("1234");
    p1.setResearchers(Arrays.asList("R1", "R2", "R2"));
    p1.setReferences(Arrays.asList("p2", "p3"));
    p1.setDomains(Arrays.asList("D1", "D2"));
    pb1 = new IPaperBasic();
    pb1.setAbs("abstract");
    pb1.setResearchers(Arrays.asList("R1", "R2", "R3"));
    pb1.setId("test1");
    pb1.setPublicationDate("1234");
    pb1.setPublicationDate("publication");
    pb1.setTitle("title");
  }

  @Test
  public void testGetPaper() {
    Mockito.when(repository.getIPaper("test1")).thenReturn(p1);
    assertEquals(service.getIPaper("test1"), p1);
  }

  @Test
  public void testGetDomains() {
    Mockito
      .when(repository.getDomains("test1"))
      .thenReturn(Arrays.asList("D1", "D2"));
    assertEquals(service.getDomains("test1"), Arrays.asList("D1", "D2"));
  }

  @Test
  public void testGetIPaperBasic() {
    Mockito.when(repository.getPaperBasic("test1")).thenReturn(pb1);
    assertEquals(service.getPaperBasicInfo("test1"), pb1);
  }

  @Test
  public void testGetPaperIds() {
    Mockito
      .when(repository.getAllPapers())
      .thenReturn(Arrays.asList("asd", "fgh", "jkl"));
    Mockito
      .when(repository.getPapersByResearcher("testGetPapersByResearcher"))
      .thenReturn(Arrays.asList("test1", "test2"));
    Mockito
      .when(repository.getPapersByPublication("testGetPapersByPublication"))
      .thenReturn(Arrays.asList("test2", "test3"));
    Mockito
      .when(repository.getPapersByDate("testGetPapersByDate"))
      .thenReturn(Arrays.asList("test3", "test4"));
    Mockito
      .when(repository.getPapersByResearcherAndPublication("r", "p"))
      .thenReturn(Arrays.asList("test5", "test3"));
    Mockito
      .when(repository.getPapersByResearcherAndDate("r", "d"))
      .thenReturn(Arrays.asList("test3", "Test1"));
    Mockito
      .when(repository.getPapersByPublicationAndDate("p", "d"))
      .thenReturn(Arrays.asList("test7", "Test2"));
    Mockito
      .when(
        repository.getPapersByResearcherAndPublicationAndDate("r", "p", "a")
      )
      .thenReturn(Arrays.asList("test1", "test8"));

    assertEquals(
      service.getPapers(null, null, null),
      Arrays.asList("asd", "fgh", "jkl")
    );
    assertEquals(
      service.getPapers("testGetPapersByResearcher", null, null),
      Arrays.asList("test1", "test2")
    );
    assertEquals(
      service.getPapers(null, "testGetPapersByPublication", null),
      Arrays.asList("test2", "test3")
    );
    assertEquals(
      service.getPapers(null, null, "testGetPapersByDate"),
      Arrays.asList("test3", "test4")
    );
    assertEquals(
      service.getPapers("r", "p", null),
      Arrays.asList("test5", "test3")
    );
    assertEquals(
      service.getPapers("r", null, "d"),
      Arrays.asList("test3", "Test1")
    );
    assertEquals(
      service.getPapers(null, "p", "d"),
      Arrays.asList("test7", "Test2")
    );
    assertEquals(
      service.getPapers("r", "p", "a"),
      Arrays.asList("test1", "test8")
    );
  }

  @Test
  public void testGetPaperCond() {
    IResult r1 = new IResult(Arrays.asList("SKTOTTO", "SKTFaker"), 14);
    Mockito.when(repository.getPaperByCond("skt", 4)).thenReturn(r1);
    IResult r2 = new IResult(
      Arrays.asList("LeeXianghook", "Leevlan", "Ryze", "Zed"),
      23
    );
    Mockito
      .when(repository.getPaperByCond("skt", "shit", true, 2))
      .thenReturn(r2);
    IResult r3 = new IResult(Arrays.asList("SKTOTTOOTTO", "SKTFakerBengi"), 11);
    Mockito
      .when(repository.getPaperByCond("skt", "miss", false, 3))
      .thenReturn(r3);
    IResult r4 = new IResult(Arrays.asList("OTTO", "SKT"), 9);
    Mockito.when(repository.getPaperByCond("skt", "1", "2", 4)).thenReturn(r4);

    assertEquals(service.getPapersByCond("skt", null, null, 4), r1);
    assertEquals(service.getPapersByCond("skt", "shit", null, 2), r2);
    assertEquals(service.getPapersByCond("skt", null, "miss", 3), r3);
    assertEquals(service.getPapersByCond("skt", "1", "2", 4), r4);
    assertEquals(service.getPapersByCond("SKT", null, null, 4), r1);
    assertEquals(service.getPapersByCond("sKT", "shit", null, 2), r2);
    assertEquals(service.getPapersByCond("SkT", null, "miss", 3), r3);
    assertEquals(service.getPapersByCond("SKt", "1", "2", 4), r4);

    //边界情况
    IResult emptyIResult = new IResult();
    Mockito
      .when(repository.getPaperByCond("skt", "2020", "2010", 3))
      .thenReturn(emptyIResult);

    assertEquals(
      service.getPapersByCond("skt", "2020", "2010", 3),
      emptyIResult
    );
    assertEquals(service.getPapersByCond("skt", null, null, -1), emptyIResult);
    assertEquals(service.getPapersByCond("skt", "1", "2", -1), emptyIResult);
    assertEquals(service.getPapersByCond("skt", null, "12", -1), emptyIResult);
    assertEquals(
      service.getPapersByCond("skt", "2019", null, -1),
      emptyIResult
    );
  }
  //        @Test
  //        public void testSQL(){
  //          IResult r1=service.getPapersByCond("work","2020","2010",1);
  //          IResult r2=service.getPapersByCond("work","2010",null,2);
  //          IResult r3=service.getPapersByCond("work",null,"2019",1);
  //          IResult r4=service.getPapersByCond("Work",null, null,3);
  //          System.out.println();
  //
  //        }
}
