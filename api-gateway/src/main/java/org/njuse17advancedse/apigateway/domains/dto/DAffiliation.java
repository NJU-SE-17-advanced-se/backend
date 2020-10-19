package org.njuse17advancedse.apigateway.domains.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DAffiliation {
  private String id;

  private String name;

  private String description;

  private List<String> researchers;

  private List<String> papers;

  private List<String> domains;
}
