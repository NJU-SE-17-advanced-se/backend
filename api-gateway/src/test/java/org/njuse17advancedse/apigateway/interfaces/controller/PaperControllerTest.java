package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles({ "test" })
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

  // 接口 2.1：查看某论文和其他论文的引用情况
  @Test
  void testGetPaperCitations_success_quoting() throws Exception {
    testGetPaperCitations_success("quoting");
  }

  @Test
  void testGetPaperCitations_success_quoted() throws Exception {
    testGetPaperCitations_success("quoted");
  }

  void testGetPaperCitations_success(String type) throws Exception {
    String paperId = "1";
    RequestBuilder paperCitationsReq = MockMvcRequestBuilders
      .get(BASE_URL + "/citations/" + paperId)
      .param("type", type);
    MvcResult referencesRes = mockMvc
      .perform(paperCitationsReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String referencesJsonStr = referencesRes.getResponse().getContentAsString();
    System.out.println(referencesJsonStr);
    assertThat(referencesJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPaperCitations_failure() {}

  // 接口 2.2：查看某论文和学者的引用情况
  @Test
  void testGetPaperCitedResearchers_success_quoting() throws Exception {
    testGetPaperCitedResearchers_success("quoting");
  }

  @Test
  void testGetPaperCitedResearchers_success_quoted() throws Exception {
    testGetPaperCitedResearchers_success("quoted");
  }

  void testGetPaperCitedResearchers_success(String type) throws Exception {
    String paperId = "1";
    RequestBuilder paperCitedResearchersReq = MockMvcRequestBuilders
      .get(BASE_URL + "/citations/" + paperId + "/researchers")
      .param("type", type);
    MvcResult citationsRes = mockMvc
      .perform(paperCitedResearchersReq)
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
    String paperReqData = objectMapper.writeValueAsString(paper);
    // construct a POST req
    RequestBuilder paperReq = MockMvcRequestBuilders
      .post(BASE_URL + "/recommend-reviewers")
      .contentType(MediaType.APPLICATION_JSON)
      .content(paperReqData);
    MvcResult recommendationRes = mockMvc
      .perform(paperReq)
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
    String paperReqData = objectMapper.writeValueAsString(paper);
    // construct a POST req
    RequestBuilder paperReq = MockMvcRequestBuilders
      .post(BASE_URL + "/not-recommend-reviewers")
      .contentType(MediaType.APPLICATION_JSON)
      .content(paperReqData);
    MvcResult nonRecommendationRes = mockMvc
      .perform(paperReq)
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
  void testGetImpact_success_custom() throws Exception {
    testGetImpact_success("custom");
  }

  void testGetImpact_success(String criteria) throws Exception {
    String paperId = "1";
    RequestBuilder getImpactReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + paperId + "/impact")
      .param("type", criteria);
    MvcResult impactRes = mockMvc
      .perform(getImpactReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String impactJsonStr = impactRes.getResponse().getContentAsString();
    System.out.println(impactJsonStr);
    assertThat(impactJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetImpact_failure() {}

  @Test
  void testGetPaperById_success() throws Exception {
    String paperId = "1";
    MvcResult paperRes = mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + paperId))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String paperJsonStr = paperRes.getResponse().getContentAsString();
    System.out.println(paperJsonStr);
    assertThat(paperJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPaperById_failure() {}
}
