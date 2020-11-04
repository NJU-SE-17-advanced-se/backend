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
class DomainControllerTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private final String BASE_URL = "/domains";

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        //                .alwaysDo(MockMvcResultHandlers.print())
        .build();
  }

  // 根据 id 获取某领域信息
  @Test
  void testGetDomainById_success() throws Exception {
    // TODO: 目前只能处理存在的领域 id
    String domainId = "017581fee9f3d4ee22bca168678a857e";
    MvcResult domainRes = mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + domainId))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String domainJsonStr = domainRes.getResponse().getContentAsString();
    System.out.println(domainJsonStr);
    assertThat(domainJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetDomainById_failure() {}

  // 根据 id 获取某领域简略信息
  @Test
  void testGetDomainBasicInfoById_success() throws Exception {
    // TODO: 目前只能处理存在的领域 id
    String domainId = "017581fee9f3d4ee22bca168678a857e";
    MvcResult domainRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + domainId + "/basic-info")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String domainJsonStr = domainRes.getResponse().getContentAsString();
    System.out.println(domainJsonStr);
    assertThat(domainJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetDomainBasicInfoById_failure() {}

  // 根据领域 id 获取某领域下的论文 id
  @Test
  void testGetPapers_success() throws Exception {
    // TODO: 目前只能处理存在的领域 id
    String domainId = "017581fee9f3d4ee22bca168678a857e";
    MvcResult domainRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + domainId + "/papers")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String domainJsonStr = domainRes.getResponse().getContentAsString();
    System.out.println(domainJsonStr);
    assertThat(domainJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetPapers_failure() {}

  // 根据领域 id 获取某领域下的学者 id
  @Test
  void testGetResearchers_success() throws Exception {
    // TODO: 目前只能处理存在的领域 id
    String domainId = "017581fee9f3d4ee22bca168678a857e";
    MvcResult domainRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + domainId + "/researchers")
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String domainJsonStr = domainRes.getResponse().getContentAsString();
    System.out.println(domainJsonStr);
    assertThat(domainJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void testGetResearchers_failure() {}
}
