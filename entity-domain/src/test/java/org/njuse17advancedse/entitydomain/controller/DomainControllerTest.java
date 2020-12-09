package org.njuse17advancedse.entitydomain.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitydomain.data.AllRepository;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;
import org.njuse17advancedse.entitydomain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class DomainControllerTest {
  @MockBean
  DomainService service;

  @Autowired
  DomainController controller;

  @MockBean
  AllRepository repository;

  IDomain p1;
  IDomainBasic pb1;

  @BeforeEach
  public void init() {
    p1 = new IDomain();
    p1.setId("sktfaker");
    p1.setName("asdasda");
    p1.setPapers(Arrays.asList("paper1", "paper2", "paper3"));
    p1.setResearchers(Arrays.asList("r1", "r2", "r3", "r4"));
    pb1 = new IDomainBasic();
    pb1.setId("sktfaker");
    pb1.setName("asdasda");
  }

  @Test
  void testGetResearchers() {
    Mockito
      .when(service.getResearchers("sktfaker"))
      .thenReturn(Arrays.asList("r1", "r2", "r3", "r4"));
    assertEquals(
      Arrays.asList("r1", "r2", "r3", "r4"),
      controller.getResearchers("sktfaker")
    );
    Mockito
      .when(service.getResearchers("not exist"))
      .thenReturn(Collections.singletonList("Not Found"));
    try {
      controller.getResearchers("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetPapers() {
    Mockito
      .when(service.getPapers("sktfaker"))
      .thenReturn(Arrays.asList("paper1", "paper2", "paper3"));
    assertEquals(
      Arrays.asList("paper1", "paper2", "paper3"),
      controller.getPapers("sktfaker")
    );
    Mockito
      .when(service.getPapers("not exist"))
      .thenReturn(Collections.singletonList("Not Found"));
    try {
      controller.getResearchers("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetDomain() {
    Mockito.when(service.getDomainById("sktfaker")).thenReturn(p1);
    assertEquals(p1, controller.getDomainById("sktfaker"));
    Mockito.when(service.getDomainById("not exist")).thenReturn(new IDomain());
    try {
      controller.getDomainById("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetDomainBasic() {
    Mockito.when(service.getDomainBasicInfoById("sktfaker")).thenReturn(pb1);
    assertEquals(pb1, controller.getDomainBasicInfoById("sktfaker"));
    Mockito
      .when(service.getDomainBasicInfoById("not exist"))
      .thenReturn(new IDomainBasic());
    try {
      controller.getDomainBasicInfoById("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetDomainsByCond() {
    IResult r = new IResult(Arrays.asList("SS", "S", "SSSS"), 5);
    Mockito.when(service.getDomainsByCond("software", 1)).thenReturn(r);
    assertEquals(r, controller.getDomainsByCond("software", 1));

    try {
      assertEquals(
        new IResult(),
        controller.getDomainsByCond("SKTelecom", -12)
      );
    } catch (Exception ignored) {}
  }
}
