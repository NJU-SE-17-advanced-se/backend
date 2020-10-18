package org.njuse17advancedse.entityaffiliation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IAffiliationBasic {
  private String id;

  private String name;

  private String description;
}
