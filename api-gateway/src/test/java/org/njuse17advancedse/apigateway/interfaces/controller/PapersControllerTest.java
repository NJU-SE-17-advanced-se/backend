package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
class PapersControllerTest {
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

  // 接口 2.1.2：查看某些论文引用情况
  @Test
  void testGetReferences_success() throws Exception {
    List<String> paperIds = new ArrayList<>(Arrays.asList("1", "2"));
    String paperIdsStr = paperIds.toString();
    String paperIdsReqStr = paperIdsStr.substring(1, paperIdsStr.length() - 1);
    MvcResult referencesRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + paperIdsReqStr + "/references"
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

  // 接口 2.2.2：查看某些论文被引情况
  @Test
  void testGetCitations_success() throws Exception {
    List<String> paperIds = new ArrayList<>(Arrays.asList("1", "2"));
    String paperIdsStr = paperIds.toString();
    String paperIdsReqStr = paperIdsStr.substring(1, paperIdsStr.length() - 1);
    MvcResult citationsRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(
          BASE_URL + "/" + paperIdsReqStr + "/citations"
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

  // 接口 2.5.2：查看某些论文的影响力
  @Test
  void testGetImpact_success() throws Exception {
    List<String> paperIds = new ArrayList<>(Arrays.asList("1", "2"));
    MvcResult impactRes = mockMvc
      .perform(
        MockMvcRequestBuilders.get(BASE_URL + "/" + paperIds + "/impact")
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
