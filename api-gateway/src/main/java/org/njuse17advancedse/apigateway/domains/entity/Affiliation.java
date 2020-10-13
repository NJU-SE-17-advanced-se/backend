package org.njuse17advancedse.apigateway.domains.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Affiliation {
  private String id;

  private String name;

  private List<Researcher> researchers;

  private List<Paper> papers;
}
