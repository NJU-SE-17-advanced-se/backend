package org.njuse17advancedse.entityaffiliation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entityaffiliation.data.AllRepository;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.dto.IResult;
import org.njuse17advancedse.entityaffiliation.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class AffiliationControllerTest {
  @Autowired
  AffiliationController controller;

  @MockBean
  AllRepository repository;

  @MockBean
  AffiliationService service;

  IAffiliation a1;
  IAffiliationBasic ab1;

  @BeforeEach
  public void init() {
    a1 = new IAffiliation();
    a1.setId("id0");
    a1.setName("testAffiliation");
    a1.setDescription("des");
    a1.setPapers(Arrays.asList("p1", "p2", "p3"));
    a1.setResearchers(Arrays.asList("r1", "r2", "r3", "r4"));
    a1.setDomains(Arrays.asList("d1", "d2"));

    ab1 = new IAffiliationBasic();
    ab1.setId("id0");
    ab1.setName("testAffiliation");
    ab1.setDescription("des");
  }

  @Test
  void testGetAffiliation() {
    Mockito.when(service.getAffiliationById("id0")).thenReturn(a1);
    assertEquals(a1, controller.getAffiliationById("id0"));
    Mockito
      .when(service.getAffiliationById("not exist"))
      .thenReturn(new IAffiliation());
    try {
      controller.getAffiliationById("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetAffiliationBasic() {
    Mockito.when(service.getAffiliationBasicInfoById("id0")).thenReturn(ab1);
    assertEquals(ab1, controller.getAffiliationBasicInfoById("id0"));
    Mockito
      .when(service.getAffiliationBasicInfoById("not exist"))
      .thenReturn(new IAffiliationBasic());
    try {
      controller.getAffiliationBasicInfoById("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetResearchers() {
    Mockito
      .when(service.getAffiliationResearchersById("id0"))
      .thenReturn(Arrays.asList("r1", "r2", "r3", "r4"));
    assertEquals(
      Arrays.asList("r1", "r2", "r3", "r4"),
      controller.getAffiliationResearchersById("id0")
    );
    Mockito
      .when(service.getAffiliationResearchersById("not exist"))
      .thenReturn(Collections.singletonList("Not Found"));
    try {
      controller.getAffiliationResearchersById("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetPapers() {
    Mockito
      .when(service.getAffiliationPapersById("id0"))
      .thenReturn(Arrays.asList("p1", "p2", "p3"));
    assertEquals(
      Arrays.asList("p1", "p2", "p3"),
      controller.getAffiliationPapersById("id0")
    );
    Mockito
      .when(service.getAffiliationPapersById("not exist"))
      .thenReturn(Collections.singletonList("Not Found"));
    try {
      controller.getAffiliationPapersById("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetDomains() {
    Mockito
      .when(service.getAffiliationDomainsById("id0"))
      .thenReturn(Arrays.asList("d1", "d2"));
    assertEquals(
      Arrays.asList("d1", "d2"),
      controller.getAffiliationDomainsById("id0")
    );
    Mockito
      .when(service.getAffiliationDomainsById("not exist"))
      .thenReturn(Collections.singletonList("Not Found"));
    try {
      controller.getAffiliationDomainsById("not exist");
    } catch (Exception ignored) {}
  }

  @Test
  void testGetAffiliationByCond() {
    IResult r = new IResult(Arrays.asList("ASD", "JKL"), 4);
    Mockito.when(service.getAffiliationsByCond("s", 2)).thenReturn(r);
    assertEquals(r, controller.getAffiliationsByCond("s", 2));
    try {
      controller.getAffiliationsByCond("sad", -1);
    } catch (Exception ignored) {}
  }
}
