package org.njuse17advancedse.apigateway.domains.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domain {
  private String id;

  private String name;

  private List<Researcher> researchers;

  private List<Paper> papers;
}
