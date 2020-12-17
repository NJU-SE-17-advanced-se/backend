package org.njuse17advancedse.entitypublication.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;
import org.njuse17advancedse.entitypublication.repository.PublicationRepository;
import org.njuse17advancedse.entitypublication.service.PublicationEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class PublicationControllerTest {
  @Autowired
  PublicationController publicationController;

  @MockBean
  PublicationEntityService publicationEntityService;

  @MockBean
  PublicationRepository publicationRepository;

  @Test
  void getPublicationsByCond() {
    ISearchResult iSearchResult = new ISearchResult();
    iSearchResult.setCount(3);
    iSearchResult.setResult(Lists.newArrayList("1", "2", "3"));
    Mockito
      .when(publicationEntityService.searchByCond("yang", 2010, 2020, 2))
      .thenReturn(iSearchResult);
    assertNull(
      publicationController.getPublicationsByCond("yang", "2010", "2020", 2)
    );
  }

  @Test
  void getPublicationById() {
    IPublication iPublication = new IPublication();
    iPublication.setId("a");
    iPublication.setName("test");
    iPublication.setPapers(Lists.newArrayList("test1", "test2"));
    Mockito
      .when(publicationEntityService.containPublication("a"))
      .thenReturn(true);
    Mockito
      .when(publicationEntityService.getPublicationById("a"))
      .thenReturn(iPublication);
    assertEquals(iPublication, publicationController.getPublicationById("a"));
  }

  @Test
  void getPublicationBasicInfoById() {
    IPublicationBasic iPublicationBasic = new IPublicationBasic();
    iPublicationBasic.setId("a");
    iPublicationBasic.setName("test3");
    Mockito
      .when(publicationEntityService.containPublication("a"))
      .thenReturn(true);
    Mockito
      .when(publicationEntityService.getIPublicationBasic("a"))
      .thenReturn(iPublicationBasic);
    assertEquals(
      iPublicationBasic,
      publicationController.getPublicationBasicInfoById("a")
    );
  }

  @Test
  void getPapersByIdOrTimeRange() {
    List<String> result = Lists.newArrayList("a", "b", "c");
    Mockito
      .when(publicationEntityService.containPublication("test"))
      .thenReturn(true);
    Mockito
      .when(
        publicationEntityService.getPapersByIdOrTimeRange("test", 2019, 2022)
      )
      .thenReturn(result);
    assertEquals(
      0,
      publicationController
        .getPapersByIdOrTimeRange("test", "2019", "2020")
        .size()
    );
  }
}
