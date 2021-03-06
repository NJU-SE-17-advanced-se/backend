package org.njuse17advancedse.entitydomain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitydomain.data.AllRepository;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class DomainServiceTest {
  @Autowired
  DomainService service;

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
    Mockito.when(repository.existsById("sktfaker")).thenReturn(true);
    Mockito.when(repository.existsById("not exist")).thenReturn(false);
  }

  @Test
  void testGetResearchers() {
    Mockito
      .when(repository.getResearchers("sktfaker"))
      .thenReturn(Arrays.asList("r1", "r2", "r3", "r4"));
    assertEquals(
      Arrays.asList("r1", "r2", "r3", "r4"),
      service.getResearchers("sktfaker")
    );
    assertEquals(
      Collections.singletonList("Not Found"),
      service.getResearchers("not exist")
    );
  }

  @Test
  void testGetPapers() {
    Mockito
      .when(repository.getPapers("sktfaker"))
      .thenReturn(Arrays.asList("paper1", "paper2", "paper3"));
    assertEquals(
      Arrays.asList("paper1", "paper2", "paper3"),
      service.getPapers("sktfaker")
    );
    assertEquals(
      Collections.singletonList("Not Found"),
      service.getPapers("not exist")
    );
  }

  @Test
  void testGetDomain() {
    Mockito.when(repository.getDomain("sktfaker")).thenReturn(p1);
    assertEquals(p1, service.getDomainById("sktfaker"));
    assertNull(service.getDomainById("not exist").getId());
  }

  @Test
  void testGetDomainBasic() {
    Mockito.when(repository.getDomainBasic("sktfaker")).thenReturn(pb1);
    assertEquals(pb1, service.getDomainBasicInfoById("sktfaker"));
    assertNull(service.getDomainBasicInfoById("not exist").getId());
  }

  @Test
  void testGetDomainsByCond() {
    IResult r = new IResult(Arrays.asList("SS", "S", "SSSS"), 5);
    Mockito.when(repository.getDomainsByCond("software", 1)).thenReturn(r);
    assertEquals(r, service.getDomainsByCond("software", 1));
    assertEquals(new IResult(), service.getDomainsByCond("SKTelecom", -12));
  }
}
