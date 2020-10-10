package org.njuse17advancedse.apigateway.interfaces.dto.paper.req;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
