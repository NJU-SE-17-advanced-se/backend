package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

@ActiveProfiles({ "test" })
@SpringBootTest
@WebAppConfiguration
class ResearcherControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private final String BASE_URL = "/researcher";

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        //                .alwaysDo(MockMvcResultHandlers.print())
        .build();
  }

  // 接口 1.1：查看某学者某一时间段所在机构
  @Test
  void testGetAffiliationsByTimeRange_success() throws Exception {
    String researcherId = "1";
    // construct a GET req
    RequestBuilder getAffiliationsReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherId + "/affiliations")
      .param("start", "2020-09-24")
      .param("end", "2020-09-24");
    MvcResult affiliationsRes = mockMvc
      .perform(getAffiliationsReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String affiliationsJsonStr = affiliationsRes
      .getResponse()
      .getContentAsString();
    System.out.println(affiliationsJsonStr);
    assertThat(affiliationsJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetAffiliationsByTimeRange_failure() {}

  // 接口 1.2：查看某学者某一时间段的研究方向
  @Test
  void testGetDomainsByTimeRange_success() throws Exception {
    String researcherId = "1";
    // construct a GET req
    RequestBuilder getDomainsReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherId + "/domains")
      .param("start", "2020-09-24")
      .param("end", "2020-09-24");
    MvcResult domainsRes = mockMvc
      .perform(getDomainsReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String domainsJsonStr = domainsRes.getResponse().getContentAsString();
    System.out.println(domainsJsonStr);
    assertThat(domainsJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetDomainsByTimeRange_failure() {}

  // 接口 1.3：预测某学者未来的研究方向
  @Test
  void testGetFutureDomains_success() throws Exception {
    String researcherId = "1";
    MvcResult domainsRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + researcherId + "/future/domains"
        )
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String domainsJsonStr = domainsRes.getResponse().getContentAsString();
    System.out.println(domainsJsonStr);
    assertThat(domainsJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetFutureDomains_failure() {}

  // 接口 1.4：查看某学者某一时间段的合作关系
  @Test
  void testGetPartnershipByTimeRange_success() throws Exception {
    String researcherId = "1";
    // construct a GET req
    RequestBuilder getPartnershipReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherId + "/partnership")
      .param("start", "2020-09-24")
      .param("end", "2020-09-24");
    MvcResult partnershipRes = mockMvc
      .perform(getPartnershipReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String partnershipJsonStr = partnershipRes
      .getResponse()
      .getContentAsString();
    System.out.println(partnershipJsonStr);
    assertThat(partnershipJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPartnershipByTimeRange_failure() {}

  // 接口 1.5：预测某学者未来的合作关系
  @Test
  void testGetFuturePartnership_success() throws Exception {
    String researcherId = "1";
    // construct a GET req
    RequestBuilder getFuturePartnershipReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherId + "/partnership/prediction")
      .param("start", "2020-09-24")
      .param("end", "2020-09-24");
    MvcResult partnershipRes = mockMvc
      .perform(getFuturePartnershipReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String partnershipJsonStr = partnershipRes
      .getResponse()
      .getContentAsString();
    System.out.println(partnershipJsonStr);
    assertThat(partnershipJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetFuturePartnership_failure() {}

  // 接口 1.6：查看某学者的引用和被引情况
  @Test
  void testGetResearcherCitations_success_quoting() throws Exception {
    testGetResearcherCitations_success("quoting");
  }

  @Test
  void testGetResearcherCitations_success_quoted() throws Exception {
    testGetResearcherCitations_success("quoted");
  }

  void testGetResearcherCitations_success(String type) throws Exception {
    String researcherId = "1";
    RequestBuilder getResearcherCitationsReq = MockMvcRequestBuilders
      .get(BASE_URL + "/citations/" + researcherId)
      .param("type", type);
    MvcResult referencesRes = mockMvc
      .perform(getResearcherCitationsReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String referencesJsonStr = referencesRes.getResponse().getContentAsString();
    System.out.println(referencesJsonStr);
    assertThat(referencesJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetResearcherCitations_failure() {}

  // 接口 1.7：查看某学者的论文引用和被引情况
  @Test
  void testGetResearcherPapersCitations_success_quoting() throws Exception {
    testGetResearcherPapersCitations_success("quoting");
  }

  @Test
  void testGetResearcherPapersCitations_success_quoted() throws Exception {
    testGetResearcherPapersCitations_success("quoted");
  }

  void testGetResearcherPapersCitations_success(String type) throws Exception {
    String researcherId = "1";
    RequestBuilder getResearcherPapersCitationsReq = MockMvcRequestBuilders
      .get(BASE_URL + "/citations/" + researcherId + "/papers")
      .param("type", type);
    MvcResult citationsRes = mockMvc
      .perform(getResearcherPapersCitationsReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String citationsJsonStr = citationsRes.getResponse().getContentAsString();
    System.out.println(citationsJsonStr);
    assertThat(citationsJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetResearcherPapersCitations_failure() {}

  // 接口 1.8：查看某学者的论文引用其他学者和被其他学者引用情况
  @Test
  void testGetResearcherPapersCitedResearchers_success_quoting()
    throws Exception {
    testGetResearcherPapersCitedResearchers_success("quoting");
  }

  @Test
  void testGetResearcherPapersCitedResearchers_success_quoted()
    throws Exception {
    testGetResearcherPapersCitedResearchers_success("quoted");
  }

  void testGetResearcherPapersCitedResearchers_success(String type)
    throws Exception {
    String researcherId = "1";
    RequestBuilder getResearcherPapersCitedResearchersReq = MockMvcRequestBuilders
      .get(BASE_URL + "/citations/" + researcherId + "/papers/researchers")
      .param("type", type);
    MvcResult citationsRes = mockMvc
      .perform(getResearcherPapersCitedResearchersReq)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String citationsJsonStr = citationsRes.getResponse().getContentAsString();
    System.out.println(citationsJsonStr);
    assertThat(citationsJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetResearcherPapersCitedResearchers_failure() {}

  // 接口 1.9：查看某学者的影响力
  @Test
  void testGetImpact_success_custom() throws Exception {
    testGetImpact_success("custom");
  }

  void testGetImpact_success(String criteria) throws Exception {
    String researcherId = "1";
    RequestBuilder getImpactReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherId + "/impact")
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

  // 根据学者的id获取学者详细信息
  @Test
  void testGetResearcherById_success() throws Exception {
    String researcherId = "1";
    MvcResult researcherRes = mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + researcherId))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String researcherJsonStr = researcherRes.getResponse().getContentAsString();
    System.out.println(researcherJsonStr);
    assertThat(researcherJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetResearcherById_failure() {}
}
