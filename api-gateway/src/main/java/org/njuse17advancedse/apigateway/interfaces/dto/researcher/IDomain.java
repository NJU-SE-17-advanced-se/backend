package org.njuse17advancedse.apigateway.interfaces.dto.researcher;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.apigateway.domains.entity.Paper;
import org.njuse17advancedse.apigateway.domains.entity.Researcher;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IDomain {
  private String id;

  private String name;

  private List<Researcher> researchers;

  private List<Paper> papers;
}
