package org.njuse17advancedse.apigateway.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DAffiliationBasic {
  private String id;

  private String name;

  private String description;
}
