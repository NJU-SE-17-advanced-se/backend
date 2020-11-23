package org.njuse17advancedse.taskreviewerrecommendation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPaperUpload {
  private String title;

  private String abs;

  private String publication;

  private String date;

  private List<String> researcherIds;

  private List<String> domainIds;

  private List<String> referenceIds;
}
