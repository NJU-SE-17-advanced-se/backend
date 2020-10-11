package org.njuse17advancedse.apigateway.interfaces.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

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
class DomainControllerTest {
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

  @Test
  void testGetDomainById_success() throws Exception {
    String domainId = "1";
    MvcResult domainRes = mockMvc
      .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + domainId))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    String domainJsonStr = domainRes.getResponse().getContentAsString();
    System.out.println(domainJsonStr);
    assertThat(domainJsonStr, is(not(emptyOrNullString())));
  }

  @Test
  void getDomainById_failure() {}
}
