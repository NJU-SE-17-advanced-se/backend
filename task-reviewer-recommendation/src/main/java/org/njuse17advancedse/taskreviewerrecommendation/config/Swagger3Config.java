package org.njuse17advancedse.taskreviewerrecommendation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class Swagger3Config {

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.OAS_30)
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("审稿人推荐").version("1.0.0").build();
  }
}
