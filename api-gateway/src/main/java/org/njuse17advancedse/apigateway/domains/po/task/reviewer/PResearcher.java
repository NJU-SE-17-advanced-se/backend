package org.njuse17advancedse.apigateway.domains.po.task.reviewer;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.apigateway.infra.entity.Affiliation;
import org.njuse17advancedse.apigateway.infra.entity.Paper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PResearcher {
  private String id;

  private String name;

  private List<Affiliation> affiliations;

  private List<Paper> papers;
}
