package org.njuse17advancedse.taskreviewerrecommendation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IAffiliation {
  private String id;

  private String name;

  private String description;

  private List<String> researchers;

  private List<String> papers;
}
