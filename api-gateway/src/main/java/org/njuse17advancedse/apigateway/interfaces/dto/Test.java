package org.njuse17advancedse.apigateway.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("测试实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {
  @ApiModelProperty("测试实体的 Id")
  private String id;

  private String a;

  private String b;
}
