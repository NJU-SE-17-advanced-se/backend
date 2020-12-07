package org.njuse17advancedse.entityaffiliation.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class AffiliationServiceTest {
  @Autowired
  AffiliationService service;

  @MockBean
  //  @Autowired
  AllRepository repository;

  IAffiliation a1;
  IAffiliationBasic ab1;

  @BeforeEach
  void init() {
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
    Mockito.when(repository.existsById("id0")).thenReturn(true);
    Mockito.when(repository.existsById("not exist")).thenReturn(false);
  }

  @Test
  void testGetAffiliation() {
    Mockito.when(repository.getAffiliationById("id0")).thenReturn(a1);
    assertEquals(a1, service.getAffiliationById("id0"));
    assertNull(service.getAffiliationById("not exist").getId());
  }

  @Test
  void testGetAffiliationBasic() {
    Mockito.when(repository.getAffiliationBasicInfoById("id0")).thenReturn(ab1);
    assertEquals(ab1, service.getAffiliationBasicInfoById("id0"));
    assertNull(service.getAffiliationBasicInfoById("not exist").getId());
  }

  @Test
  void testGetResearchers() {
    Mockito
      .when(repository.getAffiliationResearchersById("id0"))
      .thenReturn(Arrays.asList("r1", "r2", "r3", "r4"));
    assertEquals(
      Arrays.asList("r1", "r2", "r3", "r4"),
      service.getAffiliationResearchersById("id0")
    );
    assertEquals(
      Collections.singletonList("Not Found"),
      service.getAffiliationResearchersById("not exist")
    );
  }

  @Test
  void testGetPapers() {
    Mockito
      .when(repository.getAffiliationPapersById("id0"))
      .thenReturn(Arrays.asList("p1", "p2", "p3"));
    assertEquals(
      Arrays.asList("p1", "p2", "p3"),
      service.getAffiliationPapersById("id0")
    );
    assertEquals(
      Collections.singletonList("Not Found"),
      service.getAffiliationPapersById("not exist")
    );
  }

  @Test
  void testGetDomains() {
    Mockito
      .when(repository.getAffiliationDomainsById("id0"))
      .thenReturn(Arrays.asList("d1", "d2"));
    assertEquals(
      Arrays.asList("d1", "d2"),
      service.getAffiliationDomainsById("id0")
    );
    assertEquals(
      Collections.singletonList("Not Found"),
      service.getAffiliationDomainsById("not exist")
    );
  }

  @Test
  void testGetAffiliationByCond() {
    IResult r = new IResult(Arrays.asList("ASD", "JKL"), 4);
    Mockito.when(repository.getAffiliationsByCond("s", 2)).thenReturn(r);
    assertEquals(r, service.getAffiliationsByCond("s", 2));
  }
}
