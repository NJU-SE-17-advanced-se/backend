package org.njuse17advancedse.entitydomain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitydomain.data.AllRepository;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DomainServiceTest {
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
  }

  @Test
  public void testGetResearchers() {
    Mockito
      .when(repository.getResearchers("sktfaker"))
      .thenReturn(Arrays.asList("r1", "r2", "r3", "r4"));
    assertEquals(
      service.getResearchers("sktfaker"),
      Arrays.asList("r1", "r2", "r3", "r4")
    );
  }

  @Test
  public void testGetPapers() {
    Mockito
      .when(repository.getPapers("sktfaker"))
      .thenReturn(Arrays.asList("paper1", "paper2", "paper3"));
    assertEquals(
      service.getPapers("sktfaker"),
      Arrays.asList("paper1", "paper2", "paper3")
    );
  }

  @Test
  public void testGetDomain() {
    Mockito.when(repository.getDomain("sktfaker")).thenReturn(p1);
    assertEquals(p1, service.getDomainById("sktfaker"));
  }

  @Test
  public void testGetDomainBasic() {
    Mockito.when(repository.getDomainBasic("sktfaker")).thenReturn(pb1);
    assertEquals(pb1, service.getDomainBasicInfoById("sktfaker"));
  }
}
