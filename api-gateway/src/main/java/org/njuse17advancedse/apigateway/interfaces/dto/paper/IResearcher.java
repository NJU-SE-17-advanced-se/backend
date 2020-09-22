package org.njuse17advancedse.apigateway.interfaces.dto.paper;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.apigateway.domains.entity.Affiliation;
import org.njuse17advancedse.apigateway.domains.entity.Paper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IResearcher {
  private String id;

  private String name;

  private Affiliation affiliation;

  private List<Paper> papers;
}
