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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
class AffiliationControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private final String BASE_URL = "/affiliations";

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        //                .alwaysDo(MockMvcResultHandlers.print())
        .build();
  }

  // 根据机构 id 获取机构详细信息
  @Test
  void testGetAffiliationById_success() throws Exception {
    // TODO: 目前只能处理存在的机构 id
    String affiliationId = "00cf42618d285c0db8a8fd5364c57899";
    MvcResult affiliationRes = mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + affiliationId))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String affiliationJsonStr = affiliationRes
      .getResponse()
      .getContentAsString();
    System.out.println(affiliationJsonStr);
    assertThat(affiliationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetAffiliationById_failure() {}

  // 根据机构 id 获取机构简略信息
  @Test
  void testGetAffiliationBasicInfoById_success() throws Exception {
    String affiliationId = "00cf42618d285c0db8a8fd5364c57899";
    MvcResult affiliationRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + affiliationId + "/basic-info"
        )
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String affiliationJsonStr = affiliationRes
      .getResponse()
      .getContentAsString();
    System.out.println(affiliationJsonStr);
    assertThat(affiliationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetAffiliationBasicInfoById_failure() {}

  // 根据机构 id 获取该机构的学者 id
  @Test
  void testGetAffiliationResearchersById_success() throws Exception {
    String affiliationId = "00cf42618d285c0db8a8fd5364c57899";
    MvcResult affiliationRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + affiliationId + "/researchers"
        )
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String affiliationJsonStr = affiliationRes
      .getResponse()
      .getContentAsString();
    System.out.println(affiliationJsonStr);
    assertThat(affiliationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetAffiliationResearchersById_failure() {}

  // 根据机构 id 获取该机构发表的论文 id
  @Test
  void testGetAffiliationPapersById_success() throws Exception {
    String affiliationId = "00cf42618d285c0db8a8fd5364c57899";
    MvcResult affiliationRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + affiliationId + "/papers")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String affiliationJsonStr = affiliationRes
      .getResponse()
      .getContentAsString();
    System.out.println(affiliationJsonStr);
    assertThat(affiliationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetAffiliationPapersById_failure() {}

  // 根据机构 id 获取该机构的研究领域 id
  @Test
  void testGetAffiliationDomainsById_success() throws Exception {
    String affiliationId = "00cf42618d285c0db8a8fd5364c57899";
    MvcResult affiliationRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + affiliationId + "/domains")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String affiliationJsonStr = affiliationRes
      .getResponse()
      .getContentAsString();
    System.out.println(affiliationJsonStr);
    assertThat(affiliationJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetAffiliationDomainsById_failure() {}
}
