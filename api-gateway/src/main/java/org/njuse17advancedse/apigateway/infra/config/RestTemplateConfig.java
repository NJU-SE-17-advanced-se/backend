package org.njuse17advancedse.apigateway.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(getClientHttpRequestFactory());
  }

  private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
    // 连接超时 10s
    clientHttpRequestFactory.setConnectTimeout(10_000);
    // 读取超时 10s
    clientHttpRequestFactory.setReadTimeout(10_000);
    return clientHttpRequestFactory;
  }
}
