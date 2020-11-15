package org.njuse17advancedse.entityaffiliation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.internal.asm.$ClassReader;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entityaffiliation.data.AllRepository;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.dto.IResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AffiliationServiceTest {
  @Autowired
  AffiliationService service;

  @MockBean
  //  @Autowired
  AllRepository repository;

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
  public void testGetAffiliation() {
    Mockito.when(repository.getAffiliationById("id0")).thenReturn(a1);
    assertEquals(service.getAffiliationById("id0"), a1);
  }

  @Test
  public void testGetAffiliationBasic() {
    Mockito.when(repository.getAffiliationBasicInfoById("id0")).thenReturn(ab1);
    assertEquals(service.getAffiliationBasicInfoById("id0"), ab1);
  }

  @Test
  public void testGetResearchers() {
    Mockito
      .when(repository.getAffiliationResearchersById("id0"))
      .thenReturn(Arrays.asList("r1", "r2", "r3", "r4"));
    assertEquals(
      service.getAffiliationResearchersById("id0"),
      Arrays.asList("r1", "r2", "r3", "r4")
    );
  }

  @Test
  public void testGetPapers() {
    Mockito
      .when(repository.getAffiliationPapersById("id0"))
      .thenReturn(Arrays.asList("p1", "p2", "p3"));
    assertEquals(
      service.getAffiliationPapersById("id0"),
      Arrays.asList("p1", "p2", "p3")
    );
  }

  @Test
  public void testGetDomains() {
    Mockito
      .when(repository.getAffiliationPapersById("id0"))
      .thenReturn(Arrays.asList("d1", "d2"));
    assertEquals(
      service.getAffiliationPapersById("id0"),
      Arrays.asList("d1", "d2")
    );
  }

  @Test
  public void testGetAffiliationByCond() {
    IResult r = new IResult(Arrays.asList("ASD", "JKL"), 4);
    Mockito.when(repository.getAffiliationsByCond("s", 2)).thenReturn(r);
    assertEquals(r, service.getAffiliationsByCond("s", 2));

    assertEquals(service.getAffiliationsByCond("testAf", -12), new IResult());
  }
  //        @Test
  //        public void testSQL(){
  //            IAffiliation ia=service.getAffiliationById("1a10e4a1fa06c9bfaf40da95026163a0");
  //            IAffiliationBasic ib=service.getAffiliationBasicInfoById("1a10e4a1fa06c9bfaf40da95026163a0");
  //            List<String> rs=service.getAffiliationResearchersById("1a10e4a1fa06c9bfaf40da95026163a0");
  //            List<String> ps=service.getAffiliationPapersById("1a10e4a1fa06c9bfaf40da95026163a0");
  //            List<String> ds=service.getAffiliationDomainsById("1a10e4a1fa06c9bfaf40da95026163a0");
  //            IResult r=service.getAffiliationsByCond("China",3);
  //            IResult r1=service.getAffiliationsByCond("china",3);
  //            System.out.println();
  //        }

}
