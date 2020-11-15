package org.njuse17advancedse.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.basePackage(""))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("接口文档")
      .description(
        "南京大学2017级软件工程高级技术方向实践\n\n" +
        "组长：孙逸伦，学号：171250662\n\n" +
        "组员：勇中坚，学号：171250631\n\n" +
        "组员：张雨奇，学号：171250658\n\n" +
        "组员：殷承鉴，学号：171250661"
      )
      .version("1.0.0")
      .build();
  }
}
