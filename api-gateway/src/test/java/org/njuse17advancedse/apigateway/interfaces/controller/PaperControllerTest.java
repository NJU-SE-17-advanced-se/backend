package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
class PaperControllerTest {
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  void setUp() {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        //                .alwaysDo(MockMvcResultHandlers.print())
        .build();
  }

  @Test
  void testGetReferences_success() throws Exception {
    String paperId = "1";
    MvcResult referencesRes = mockMvc
      .perform(MockMvcRequestBuilders.get("/paper/" + paperId + "/references"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String referencesJsonStr = referencesRes.getResponse().getContentAsString();
    assertThat(referencesJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void getCitations() {}

  @Test
  void getRecommendedReviewers() {}

  @Test
  void getNotRecommendedReviewers() {}

  @Test
  void getImpact() {}

  @Test
  void savePaper() {}
}
