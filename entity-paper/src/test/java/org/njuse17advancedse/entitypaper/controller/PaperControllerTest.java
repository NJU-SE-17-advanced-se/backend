package org.njuse17advancedse.entitypaper.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitypaper.data.PaperRepository;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.njuse17advancedse.entitypaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class PaperControllerTest {
  @MockBean
  PaperService service;

  @MockBean
  PaperRepository repository;

  @Autowired
  PaperController controller;

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
  void testGetPaper() {
    Mockito.when(service.getIPaper("test1")).thenReturn(p1);
    assertEquals(p1, controller.getPaper("test1"));
    Mockito.when(service.getIPaper("not exist")).thenReturn(new IPaper());
    try {
      controller.getPaper("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetDomains() {
    Mockito
      .when(service.getDomains("test1"))
      .thenReturn(Arrays.asList("D1", "D2"));
    assertEquals(Arrays.asList("D1", "D2"), controller.getDomains("test1"));
    Mockito
      .when(service.getDomains("not exist"))
      .thenReturn(Collections.singletonList("Not Found"));
    try {
      controller.getDomains("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetCitations() {
    Mockito
      .when(service.getCitations("test1"))
      .thenReturn(Arrays.asList("D1", "D2"));
    assertEquals(Arrays.asList("D1", "D2"), controller.getCitations("test1"));
    Mockito
      .when(service.getCitations("not exist"))
      .thenReturn(Collections.singletonList("Not Found"));
    try {
      controller.getCitations("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetIPaperBasic() {
    Mockito.when(service.getPaperBasicInfo("test1")).thenReturn(pb1);
    assertEquals(pb1, service.getPaperBasicInfo("test1"));
    Mockito
      .when(service.getPaperBasicInfo("not exist"))
      .thenReturn(new IPaperBasic());
    try {
      controller.getPaperBasicInfo("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetPaperCond() {
    IResult r1 = new IResult(Arrays.asList("SKTOTTO", "SKTFaker"), 14);
    Mockito.when(service.getPapersByCond("skt", null, null, 4)).thenReturn(r1);
    IResult r2 = new IResult(
      Arrays.asList("LeeXianghook", "Leevlan", "Ryze", "Zed"),
      23
    );
    Mockito.when(service.getPapersByCond("skt", "123", null, 2)).thenReturn(r2);
    IResult r3 = new IResult(Arrays.asList("SKTOTTOOTTO", "SKTFakerBengi"), 11);
    Mockito.when(service.getPapersByCond("skt", null, "234", 3)).thenReturn(r3);
    IResult r4 = new IResult(Arrays.asList("OTTO", "SKT"), 9);
    Mockito.when(service.getPapersByCond("skt", "1", "2", 4)).thenReturn(r4);

    assertEquals(r1, controller.getPapersByCond("skt", null, null, 4));
    assertEquals(r2, controller.getPapersByCond("skt", "123", null, 2));
    assertEquals(r3, controller.getPapersByCond("skt", null, "234", 3));
    assertEquals(r4, controller.getPapersByCond("skt", "1", "2", 4));
  }
}
