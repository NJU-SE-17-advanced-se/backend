package org.njuse17advancedse.apigateway.interfaces.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.apigateway.domains.entity.Paper;
import org.njuse17advancedse.apigateway.domains.entity.Researcher;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IAffiliation {
  private String id;

  private String name;

  private List<Researcher> researchers;

  private List<Paper> papers;
}