package org.njuse17advancedse.apigateway.interfaces.dto.paper;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.apigateway.infra.entity.Affiliation;
import org.njuse17advancedse.apigateway.infra.entity.Paper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IResearcher {
  private String id;

  private String name;

  private List<Affiliation> affiliations; // 可能同时身在多个机构

  private List<Paper> papers;
}
