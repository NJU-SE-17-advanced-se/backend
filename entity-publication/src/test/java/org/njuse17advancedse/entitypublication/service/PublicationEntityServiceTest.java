package org.njuse17advancedse.entitypublication.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;
import org.njuse17advancedse.entitypublication.entity.JpaDomain;
import org.njuse17advancedse.entitypublication.entity.JpaPaper;
import org.njuse17advancedse.entitypublication.repository.PublicationRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class PublicationEntityServiceTest {
  @MockBean
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
      "test",
      publicationEntityService.getPublicationById("test").getName()
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
      .when(publicationRepository.getPapers("test", 2019, 2022))
      .thenReturn(Lists.newArrayList("test2"));
    assertEquals(
      publicationEntityService.getPapersByIdOrTimeRange("test", 2019, 2022),
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
      "test3",
      publicationEntityService.getIPublicationBasic("test").getName()
    );
  }

  @Test
  void searchByCond() {
    ISearchResult iSearchResult = new ISearchResult();
    iSearchResult.setCount(5);
    iSearchResult.setResult(Lists.newArrayList("1", "2", "3"));
    Mockito
      .when(publicationRepository.searchByCond("yang", 2010, 2020, 2))
      .thenReturn(iSearchResult);
    assertEquals(
      5,
      publicationEntityService.searchByCond("yang", 2010, 2020, 2).getCount()
    );
  }

  @Test
  void containPublication() {
    Mockito
      .when(publicationRepository.containPublication("test"))
      .thenReturn(false);
    assertFalse(publicationEntityService.containPublication("test"));
  }
}
