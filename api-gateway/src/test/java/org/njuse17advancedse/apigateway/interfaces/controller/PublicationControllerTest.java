package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
class PublicationControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private final String BASE_URL = "/publications";

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        //                .alwaysDo(MockMvcResultHandlers.print())
        .build();
  }

  // 根据 id 获取出版物详细信息
  @Test
  void testGetPublicationById_success() throws Exception {
    String publicationId = "1";
    // construct a GET req
    RequestBuilder getPublicationReq = MockMvcRequestBuilders.get(
      BASE_URL + "/" + publicationId
    );
    MvcResult publicationRes = mockMvc
      .perform(getPublicationReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String publicationJsonStr = publicationRes
      .getResponse()
      .getContentAsString();
    System.out.println(publicationJsonStr);
    assertThat(publicationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPublicationById_failure() {}

  // 根据其他查询条件获取出版物 id
  @Test
  void testGetPublicationsByTimeRange_success() throws Exception {
    // construct a GET req
    RequestBuilder getPublicationsReq = MockMvcRequestBuilders.get(BASE_URL);
    MvcResult publicationsRes = mockMvc
      .perform(getPublicationsReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String publicationsJsonStr = publicationsRes
      .getResponse()
      .getContentAsString();
    System.out.println(publicationsJsonStr);
    assertThat(publicationsJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPublicationsByTimeRange_failure() {}

  // 根据 id （和时间范围）获取出版物包含的论文 id
  @Test
  void testGetPapersByIdOrTimeRange_success() throws Exception {
    String publicationId = "1";
    // construct a GET req
    RequestBuilder getPublicationPapersReq = MockMvcRequestBuilders.get(
      BASE_URL + "/" + publicationId + "/papers"
    );
    MvcResult publicationPapersRes = mockMvc
      .perform(getPublicationPapersReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String publicationPapersJsonStr = publicationPapersRes
      .getResponse()
      .getContentAsString();
    System.out.println(publicationPapersJsonStr);
    assertThat(publicationPapersJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPapersByIdOrTimeRange_failure() {}

  // 根据 id 获取出版物基本信息
  @Test
  void testGetPublicationBasicInfoById_success() throws Exception {
    String publicationId = "1";
    // construct a GET req
    RequestBuilder getPublicationReq = MockMvcRequestBuilders.get(
      BASE_URL + "/" + publicationId
    );
    MvcResult publicationRes = mockMvc
      .perform(getPublicationReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String publicationJsonStr = publicationRes
      .getResponse()
      .getContentAsString();
    System.out.println(publicationJsonStr);
    assertThat(publicationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPublicationBasicInfoById_failure() {}
}
