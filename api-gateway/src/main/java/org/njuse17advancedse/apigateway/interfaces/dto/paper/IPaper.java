package org.njuse17advancedse.apigateway.interfaces.dto.paper;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.apigateway.infra.entity.Domain;
import org.njuse17advancedse.apigateway.infra.entity.Paper;
import org.njuse17advancedse.apigateway.infra.entity.Researcher;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPaper {
  private String id;

  private String title;

  private String abs;

  private String link;

  private List<Researcher> researchers;

  private List<Domain> domains;

  private List<Paper> references;
}
