package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class PaperControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private final String BASE_URL = "/paper";

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        //                .alwaysDo(MockMvcResultHandlers.print())
        .build();
  }

  // 接口 2.1：查看某论文引用情况
  @Test
  void testGetReferences_success() throws Exception {
    String paperId = "1";
    MvcResult referencesRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + paperId + "/references")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String referencesJsonStr = referencesRes.getResponse().getContentAsString();
    System.out.println(referencesJsonStr);
    assertThat(referencesJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetReferences_failure() {}

  // 接口 2.2：查看某论文被引情况
  @Test
  void testGetCitations_success() throws Exception {
    String paperId = "1";
    MvcResult citationsRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + paperId + "/citations")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String citationsJsonStr = citationsRes.getResponse().getContentAsString();
    System.out.println(citationsJsonStr);
    assertThat(citationsJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetCitations_failure() {}

  // 接口 2.3：查看某论文推荐的审稿人
  @Test
  void testGetRecommendedReviewers_success() throws Exception {
    String paperId = "1";
    MvcResult recommendationRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + paperId + "/recommendation/reviewers"
        )
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String recommendationJsonStr = recommendationRes
      .getResponse()
      .getContentAsString();
    System.out.println(recommendationJsonStr);
    assertThat(recommendationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetRecommendedReviewers_failure() {}

  // 接口 2.4：查看某论文不推荐的审稿人
  @Test
  void testGetNotRecommendedReviewers_success() throws Exception {
    String paperId = "1";
    MvcResult nonRecommendationRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + paperId + "/non-recommendation/reviewers"
        )
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String nonRecommendationJsonStr = nonRecommendationRes
      .getResponse()
      .getContentAsString();
    System.out.println(nonRecommendationJsonStr);
    assertThat(nonRecommendationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetNotRecommendedReviewers_failure() {}

  // 接口 2.5：查看某论文的影响力
  @Test
  void testGetImpact_success() throws Exception {
    String paperId = "1";
    MvcResult impactRes = mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + paperId + "/impact"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String impactJsonStr = impactRes.getResponse().getContentAsString();
    System.out.println(impactJsonStr);
    assertThat(impactJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetImpact_failure() {}

  // 上传新论文
  @Test
  void testSavePaper_success() throws Exception {
    IPaper paper = new IPaper(
      "9",
      "测试论文9",
      "测试论文9的摘要",
      "google.com",
      new ArrayList<>(),
      new ArrayList<>(),
      new ArrayList<>()
    );
    // convert java obj to JSON by jackson
    ObjectMapper objectMapper = new ObjectMapper();
    String savePaperReqData = objectMapper.writeValueAsString(paper);
    // construct a POST req
    RequestBuilder savePaperReq = MockMvcRequestBuilders
      .post(BASE_URL + "/")
      .contentType(MediaType.APPLICATION_JSON)
      .content(savePaperReqData);
    mockMvc
      .perform(savePaperReq)
      .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void testSavePaper_failure() {}
}
