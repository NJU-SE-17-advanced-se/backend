package org.njuse17advancedse.apigateway.interfaces.dto.researcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IImpact {
  private double impact;

  private String criteria; // 评价标准
}
