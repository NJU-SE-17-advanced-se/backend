package org.njuse17advancedse.entityresearcher.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.dto.ISearchResult;
import org.njuse17advancedse.entityresearcher.entity.JpaDomain;
import org.njuse17advancedse.entityresearcher.entity.JpaPaper;
import org.njuse17advancedse.entityresearcher.entity.JpaResearcher;
import org.njuse17advancedse.entityresearcher.repository.ResearcherRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class ResearcherEntityServiceTest {
  @MockBean
  private ResearcherRepository researcherRepository;

  private ResearcherEntityService researcherEntityService;

  @BeforeEach
  public void init() {
    researcherEntityService = new ResearcherEntityService(researcherRepository);
  }

  @Test
  void getResearcherById() {
    IResearcher iResearcher = new IResearcher();
    JpaPaper jpaPaper = new JpaPaper();
    jpaPaper.setId("test");
    JpaDomain jpaDomain = new JpaDomain();
    jpaDomain.setId("domain1");
    jpaPaper.setDomains(Lists.newArrayList(jpaDomain));
    iResearcher.setPapers(Lists.newArrayList("test"));
    iResearcher.setName("ycj");
    iResearcher.setId("IEEE_37085391626");
    Mockito
      .when(researcherRepository.getResearcherById("test"))
      .thenReturn(iResearcher);
    assertEquals(
      researcherEntityService.getResearcherById("test").getName(),
      "ycj"
    );
  }

  @Test
  void getPapersByRid() {
    JpaResearcher jpaResearcher = new JpaResearcher();
    JpaPaper jpaPaper1 = new JpaPaper();
    jpaPaper1.setId("test1");
    JpaDomain jpaDomain1 = new JpaDomain();
    jpaDomain1.setId("domain1");
    jpaPaper1.setDomains(Lists.newArrayList(jpaDomain1));
    jpaPaper1.setPublicationDate(2018);

    JpaPaper jpaPaper2 = new JpaPaper();
    jpaPaper2.setId("test1");
    JpaDomain jpaDomain2 = new JpaDomain();
    jpaDomain2.setId("domain2");
    jpaPaper2.setDomains(Lists.newArrayList(jpaDomain2));
    jpaPaper2.setPublicationDate(2020);

    jpaResearcher.setPapers(Lists.newArrayList(jpaPaper1, jpaPaper2));
    jpaResearcher.setName("ycj");
    jpaResearcher.setId("IEEE_37085391626");
    Mockito
      .when(researcherRepository.findPapers("test", 2017, 2019))
      .thenReturn(Lists.newArrayList("test1"));
    assertEquals(
      researcherEntityService.getPapersByRid("test", 2017, 2019),
      Lists.newArrayList("test1")
    );
  }

  @Test
  void getDomainByRid() {
    JpaResearcher jpaResearcher = new JpaResearcher();
    JpaPaper jpaPaper1 = new JpaPaper();
    jpaPaper1.setId("test1");
    JpaDomain jpaDomain1 = new JpaDomain();
    jpaDomain1.setId("domain1");
    jpaPaper1.setDomains(Lists.newArrayList(jpaDomain1));
    jpaPaper1.setPublicationDate(2018);

    JpaPaper jpaPaper2 = new JpaPaper();
    jpaPaper2.setId("test1");
    JpaDomain jpaDomain2 = new JpaDomain();
    jpaDomain2.setId("domain2");
    jpaPaper2.setDomains(Lists.newArrayList(jpaDomain2));
    jpaPaper2.setPublicationDate(2020);

    jpaResearcher.setPapers(Lists.newArrayList(jpaPaper1, jpaPaper2));
    Mockito
      .when(researcherRepository.findDomains("test", 2017, 9999))
      .thenReturn(Lists.newArrayList("domain2", "domain1"));
    assertEquals(
      researcherEntityService.getDomainByRid("test", 2017, 9999),
      Lists.newArrayList("domain2", "domain1")
    );
  }

  @Test
  void getAffiliationByRid() {
    Mockito
      .when(researcherRepository.findAffiliations("test", 2015, 2018))
      .thenReturn(Lists.newArrayList("a1"));
    assertEquals(
      researcherEntityService.getAffiliationByRid("test", 2015, 2018),
      Lists.newArrayList("a1")
    );
  }

  @Test
  void getResearcherBasicById() {
    IResearcherBasic iResearcherBasic = new IResearcherBasic();
    JpaPaper jpaPaper = new JpaPaper();
    jpaPaper.setId("test");
    JpaDomain jpaDomain = new JpaDomain();
    jpaDomain.setId("domain1");
    jpaPaper.setDomains(Lists.newArrayList(jpaDomain));
    jpaPaper.setCitation(15);
    iResearcherBasic.setPapers(Lists.newArrayList("test"));
    iResearcherBasic.setName("ycj");
    iResearcherBasic.setId("IEEE_37085391626");
    Mockito
      .when(researcherRepository.getResearcherBasic("test"))
      .thenReturn(iResearcherBasic);
    assertEquals(
      researcherEntityService.getResearcherBasicById("test").getName(),
      "ycj"
    );
  }

  @Test
  void searcherByCond() {
    ISearchResult iSearchResult = new ISearchResult();
    iSearchResult.setCount(5);
    iSearchResult.setResult(Lists.newArrayList("1", "2", "3"));
    Mockito
      .when(researcherRepository.searchByCond("yang", 2))
      .thenReturn(iSearchResult);
    assertEquals(5, researcherEntityService.searchByCond("yang", 2).getCount());
  }
}
