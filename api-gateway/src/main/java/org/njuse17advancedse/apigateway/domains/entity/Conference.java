package org.njuse17advancedse.apigateway.domains.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Conference {
  private String id;

  private String name;

  private List<Paper> papers;
}
