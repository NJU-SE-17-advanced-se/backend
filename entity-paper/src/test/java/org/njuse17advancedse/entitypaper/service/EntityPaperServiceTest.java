package org.njuse17advancedse.entitypaper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitypaper.data.AllRepository;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class EntityPaperServiceTest {
  @Autowired
  PaperService service;

  @MockBean
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
    Mockito.when(repository.existsById("test1")).thenReturn(true);
    Mockito.when(repository.existsById("not exist")).thenReturn(false);
  }

  @Test
  void testGetPaper() {
    Mockito.when(repository.getIPaper("test1")).thenReturn(p1);
    assertEquals(p1, service.getIPaper("test1"));
    assertNull(service.getIPaper("not exist").getId());
  }

  @Test
  void testGetDomains() {
    Mockito
      .when(repository.getDomains("test1"))
      .thenReturn(Arrays.asList("D1", "D2"));
    assertEquals(Arrays.asList("D1", "D2"), service.getDomains("test1"));
    assertEquals(
      Collections.singletonList("Not Found"),
      service.getDomains("not exist")
    );
  }

  @Test
  void testGetCitations() {
    Mockito
      .when(repository.getCitations("test1"))
      .thenReturn(Arrays.asList("1", "2"));
    assertEquals(Arrays.asList("1", "2"), service.getCitations("test1"));
    assertEquals(
      Collections.singletonList("Not Found"),
      service.getCitations("not exist")
    );
  }

  @Test
  void testGetIPaperBasic() {
    Mockito.when(repository.getPaperBasic("test1")).thenReturn(pb1);
    assertEquals(pb1, service.getPaperBasicInfo("test1"));
    assertNull(service.getPaperBasicInfo("not exist").getId());
  }

  @Test
  void testGetPaperCond() {
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

    assertEquals(r1, service.getPapersByCond("skt", null, null, 4));
    assertEquals(r2, service.getPapersByCond("skt", "shit", null, 2));
    assertEquals(r3, service.getPapersByCond("skt", null, "miss", 3));
    assertEquals(r4, service.getPapersByCond("skt", "1", "2", 4));
    assertEquals(r1, service.getPapersByCond("SKT", null, null, 4));
    assertEquals(r2, service.getPapersByCond("sKT", "shit", null, 2));
    assertEquals(r3, service.getPapersByCond("SkT", null, "miss", 3));
    assertEquals(r4, service.getPapersByCond("SKt", "1", "2", 4));

    //边界情况
    IResult emptyIResult = new IResult();
    Mockito
      .when(repository.getPaperByCond("skt", "2020", "2010", 3))
      .thenReturn(emptyIResult);

    assertEquals(
      service.getPapersByCond("skt", "2020", "2010", 3),
      emptyIResult
    );
    assertEquals(emptyIResult, service.getPapersByCond("skt", null, null, -1));
    assertEquals(emptyIResult, service.getPapersByCond("skt", "1", "2", -1));
    assertEquals(emptyIResult, service.getPapersByCond("skt", null, "12", -1));
    assertEquals(
      service.getPapersByCond("skt", "2019", null, -1),
      emptyIResult
    );
  }
}
