package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyOrNullString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
class ResearchersControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private final String BASE_URL = "/researchers";

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        //                .alwaysDo(MockMvcResultHandlers.print())
        .build();
  }

  // 接口 1.1.2：查看某些学者某一时间段所在机构
  @Test
  void testGetAffiliationsByTimeRange_success() throws Exception {
    List<String> researcherIds = new ArrayList<>(Arrays.asList("1", "2"));
    // construct a GET req
    RequestBuilder getAffiliationsReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherIds + "/affiliations")
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

  // 接口 1.2.2：查看某些学者某一时间段的研究方向
  @Test
  void testGetDomainsByTimeRange_success() throws Exception {
    List<String> researcherIds = new ArrayList<>(Arrays.asList("1", "2"));
    // construct a GET req
    RequestBuilder getDomainsReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherIds + "/domains")
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

  // 接口 1.3.2：预测某些学者未来的研究方向
  @Test
  void testGetFutureDomains_success() throws Exception {
    List<String> researcherIds = new ArrayList<>(Arrays.asList("1", "2"));
    MvcResult domainsRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + researcherIds + "/future/domains"
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

  // 接口 1.4.2：查看某些学者某一时间段的合作关系
  @Test
  void testGetPartnershipByTimeRange_success() throws Exception {
    List<String> researcherIds = new ArrayList<>(Arrays.asList("1", "2"));
    // construct a GET req
    RequestBuilder getPartnershipReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherIds + "/partnership")
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

  // 接口 1.5.2：预测某些学者未来的合作关系
  @Test
  void testGetFuturePartnership_success() throws Exception {
    List<String> researcherIds = new ArrayList<>(Arrays.asList("1", "2"));
    // construct a GET req
    RequestBuilder getFuturePartnershipReq = MockMvcRequestBuilders
      .get(BASE_URL + "/" + researcherIds + "/future/partnership")
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

  // 接口 1.6.2：查看某些学者的论文引用情况
  @Test
  void testGetReferences_success() throws Exception {
    List<String> researcherIds = new ArrayList<>(Arrays.asList("1", "2"));
    MvcResult referencesRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + researcherIds + "/references"
        )
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String referencesJsonStr = referencesRes.getResponse().getContentAsString();
    System.out.println(referencesJsonStr);
    assertThat(referencesJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetReferences_failure() {}

  // 接口 1.7.2：查看某些学者的论文被引情况
  @Test
  void testGetCitations_success() throws Exception {
    List<String> researcherIds = new ArrayList<>(Arrays.asList("1", "2"));
    MvcResult citationsRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + researcherIds + "/citations"
        )
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String citationsJsonStr = citationsRes.getResponse().getContentAsString();
    System.out.println(citationsJsonStr);
    assertThat(citationsJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetCitations_failure() {}

  // 接口 1.8.2：查看某些学者的影响力
  @Test
  void testGetImpact_success() throws Exception {
    List<String> researcherIds = new ArrayList<>(Arrays.asList("1", "2"));
    MvcResult impactRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + researcherIds + "/impact")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String impactJsonStr = impactRes.getResponse().getContentAsString();
    System.out.println(impactJsonStr);
    assertThat(impactJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetImpact_failure() {}
}
