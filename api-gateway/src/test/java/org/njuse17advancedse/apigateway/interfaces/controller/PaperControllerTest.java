package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaperUpload;
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

@SpringBootTest
@WebAppConfiguration
class PaperControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private final String BASE_URL = "/papers";

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
      .get(BASE_URL + "/" + paperId + "/citations")
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
    String paperId = "0836f610195c9866185ea9a3fda5a8bb";
    RequestBuilder paperCitedResearchersReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + paperId + "/citations/researchers")
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
    IPaperUpload paper = new IPaperUpload(
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
    IPaperUpload paper = new IPaperUpload(
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
    String paperId = "0836f610195c9866185ea9a3fda5a8bb";
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

  // 根据 id 获取论文信息
  @Test
  void testGetPaperById_success() throws Exception {
    // TODO: 目前只能处理存在的论文 id
    String paperId = "0083fbed16238ff8193a2aa091d55bc6";
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

  // 根据其他查询条件获取论文 id
  @Test
  void testGetPapers_success() throws Exception {
    // TODO: 目前只能处理存在的论文 id
    String paperId = "0083fbed16238ff8193a2aa091d55bc6";
    MvcResult paperRes = mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String paperJsonStr = paperRes.getResponse().getContentAsString();
    System.out.println(paperJsonStr);
    assertThat(paperJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPapers_failure() {}

  // 根据 id 获取论文简略信息
  @Test
  void testGetPaperBasicInfo_success() throws Exception {
    // TODO: 目前只能处理存在的论文 id
    String paperId = "0083fbed16238ff8193a2aa091d55bc6";
    MvcResult paperRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + paperId + "/basic-info")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String paperJsonStr = paperRes.getResponse().getContentAsString();
    System.out.println(paperJsonStr);
    assertThat(paperJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPaperBasicInfo_failure() {}

  // 获取论文所属领域 id
  @Test
  void testGetDomains_success() throws Exception {
    // TODO: 目前只能处理存在的论文 id
    String paperId = "0083fbed16238ff8193a2aa091d55bc6";
    MvcResult paperRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + paperId + "/domains")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String paperJsonStr = paperRes.getResponse().getContentAsString();
    System.out.println(paperJsonStr);
    assertThat(paperJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetDomains_failure() {}
}
