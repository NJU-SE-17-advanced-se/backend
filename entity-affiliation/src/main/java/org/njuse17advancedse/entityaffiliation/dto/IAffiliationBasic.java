package org.njuse17advancedse.entityaffiliation.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IAffiliationBasic implements Serializable {
  private String id;

  private String name;

  private String description;
}
