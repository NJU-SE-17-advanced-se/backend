package org.njuse17advancedse.entityresearcher.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.njuse17advancedse.entityresearcher.entity.JpaDomain;
import org.njuse17advancedse.entityresearcher.entity.JpaPaper;
import org.njuse17advancedse.entityresearcher.entity.JpaResearcher;
import org.njuse17advancedse.entityresearcher.repository.ResearcherRepository;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResearcherEntityServiceTest {
  @Mock
  private ResearcherRepository researcherRepository;

  private ResearcherEntityService researcherEntityService;

  @BeforeEach
  public void init() {
    researcherEntityService = new ResearcherEntityService(researcherRepository);
  }

  @Test
  void getResearcherById() {
    JpaResearcher jpaResearcher = new JpaResearcher();
    JpaPaper jpaPaper = new JpaPaper();
    jpaPaper.setId("test");
    JpaDomain jpaDomain = new JpaDomain();
    jpaDomain.setName("domain1");
    jpaPaper.setDomains(Lists.newArrayList(jpaDomain));
    jpaResearcher.setPapers(Lists.newArrayList(jpaPaper));
    jpaResearcher.setName("ycj");
    jpaResearcher.setId("IEEE_37085391626");
    Mockito
      .when(researcherRepository.findResearcherById("test"))
      .thenReturn(jpaResearcher);
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
    jpaDomain1.setName("domain1");
    jpaPaper1.setDomains(Lists.newArrayList(jpaDomain1));
    jpaPaper1.setPublicationDate("2018");

    JpaPaper jpaPaper2 = new JpaPaper();
    jpaPaper2.setId("test1");
    JpaDomain jpaDomain2 = new JpaDomain();
    jpaDomain2.setName("domain2");
    jpaPaper2.setDomains(Lists.newArrayList(jpaDomain2));
    jpaPaper2.setPublicationDate("2020");

    jpaResearcher.setPapers(Lists.newArrayList(jpaPaper1, jpaPaper2));
    jpaResearcher.setName("ycj");
    jpaResearcher.setId("IEEE_37085391626");
    Mockito
      .when(researcherRepository.findResearcherById("test"))
      .thenReturn(jpaResearcher);
    assertEquals(
      researcherEntityService.getPapersByRid("test", "2017", "2019"),
      Lists.newArrayList("test1")
    );
  }

  @Test
  void getDomainByRid() {
    JpaResearcher jpaResearcher = new JpaResearcher();
    JpaPaper jpaPaper1 = new JpaPaper();
    jpaPaper1.setId("test1");
    JpaDomain jpaDomain1 = new JpaDomain();
    jpaDomain1.setName("domain1");
    jpaPaper1.setDomains(Lists.newArrayList(jpaDomain1));
    jpaPaper1.setPublicationDate("2018");

    JpaPaper jpaPaper2 = new JpaPaper();
    jpaPaper2.setId("test1");
    JpaDomain jpaDomain2 = new JpaDomain();
    jpaDomain2.setName("domain2");
    jpaPaper2.setDomains(Lists.newArrayList(jpaDomain2));
    jpaPaper2.setPublicationDate("2020");

    jpaResearcher.setPapers(Lists.newArrayList(jpaPaper1, jpaPaper2));
    Mockito
      .when(researcherRepository.findResearcherById("test"))
      .thenReturn(jpaResearcher);
    assertEquals(
      researcherEntityService.getDomainByRid("test", "2017", null),
      Lists.newArrayList("domain2", "domain1")
    );
  }

  @Test
  void getAffiliationByRid() {
    //TODO 等待方法完成后补充
  }

  @Test
  void getResearcherBasicById() {
    //TODO 等待方法完成后补充
  }
}
