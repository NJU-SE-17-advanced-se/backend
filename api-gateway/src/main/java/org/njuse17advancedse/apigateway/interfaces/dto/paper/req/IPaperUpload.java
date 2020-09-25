package org.njuse17advancedse.apigateway.interfaces.dto.paper.req;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.apigateway.domains.entity.Domain;
import org.njuse17advancedse.apigateway.domains.entity.Paper;
import org.njuse17advancedse.apigateway.domains.entity.Researcher;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPaperUpload {
  private String id;

  private String title;

  private String abs;

  private String link;

  private List<String> researcherIds;

  private List<String> domainIds;

  private List<String> referenceIds;
}
