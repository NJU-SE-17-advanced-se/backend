package org.njuse17advancedse.entitypublication.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.entity.JpaDomain;
import org.njuse17advancedse.entitypublication.entity.JpaPaper;
import org.njuse17advancedse.entitypublication.entity.JpaPublication;
import org.njuse17advancedse.entitypublication.repository.PublicationRepository;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PublicationEntityServiceTest {
  @Mock
  private PublicationRepository publicationRepository;

  private PublicationEntityService publicationEntityService;

  @BeforeEach
  public void init() {
    publicationEntityService =
      new PublicationEntityService(publicationRepository);
  }

  @Test
  void getPublicationById() {
    IPublication iPublication = new IPublication();
    iPublication.setName("test");
    JpaPaper jpaPaper1 = new JpaPaper();
    jpaPaper1.setId("test1");
    JpaDomain jpaDomain1 = new JpaDomain();
    jpaDomain1.setName("domain1");
    jpaPaper1.setDomains(Lists.newArrayList(jpaDomain1));
    jpaPaper1.setPublicationDate(2018);

    JpaPaper jpaPaper2 = new JpaPaper();
    jpaPaper2.setId("test2");
    JpaDomain jpaDomain2 = new JpaDomain();
    jpaDomain2.setName("domain2");
    jpaPaper2.setDomains(Lists.newArrayList(jpaDomain2));
    jpaPaper2.setPublicationDate(2020);
    iPublication.setPapers(Lists.newArrayList("test1", "test2"));

    Mockito
      .when(publicationRepository.findPublication("test"))
      .thenReturn(iPublication);
    assertEquals(
      publicationEntityService.getPublicationById("test").getName(),
      "test"
    );
  }

  @Test
  void getPublications() {
    JpaPublication jpaPublication1 = new JpaPublication();
    jpaPublication1.setName("test1");
    jpaPublication1.setPublicationDate(2008);
    JpaPublication jpaPublication2 = new JpaPublication();
    jpaPublication2.setName("test2");
    jpaPublication2.setPublicationDate(2018);
    Mockito
      .when(publicationRepository.findPublications("test", "2015", "2020"))
      .thenReturn(Lists.newArrayList("test1", "test2"));
    assertEquals(
      publicationEntityService.getPublications("test", "2015", "2020"),
      Lists.newArrayList("test1", "test2")
    );
  }

  @Test
  void getPapersByIdOrTimeRange() {
    IPublication iPublication = new IPublication();
    iPublication.setName("test");
    JpaPaper jpaPaper1 = new JpaPaper();
    jpaPaper1.setId("test1");
    JpaDomain jpaDomain1 = new JpaDomain();
    jpaDomain1.setName("domain1");
    jpaPaper1.setDomains(Lists.newArrayList(jpaDomain1));
    jpaPaper1.setPublicationDate(2018);

    JpaPaper jpaPaper2 = new JpaPaper();
    jpaPaper2.setId("test2");
    JpaDomain jpaDomain2 = new JpaDomain();
    jpaDomain2.setName("domain2");
    jpaPaper2.setDomains(Lists.newArrayList(jpaDomain2));
    jpaPaper2.setPublicationDate(2020);
    iPublication.setPapers(Lists.newArrayList("test1", "test2"));
    Mockito
      .when(publicationRepository.getPapers("test", "2019", "2022"))
      .thenReturn(Lists.newArrayList("test2"));
    assertEquals(
      publicationEntityService.getPapersByIdOrTimeRange("test", "2019", "2022"),
      Lists.newArrayList("test2")
    );
  }

  @Test
  void getIPublicationBasic() {
    IPublicationBasic iPublicationBasic = new IPublicationBasic();
    iPublicationBasic.setName("test3");
    Mockito
      .when(publicationRepository.findPublicationBasic("test"))
      .thenReturn(iPublicationBasic);
    assertEquals(
      publicationEntityService.getIPublicationBasic("test").getName(),
      "test3"
    );
  }
}
