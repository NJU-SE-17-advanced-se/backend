package org.njuse17advancedse.taskreviewerrecommendation.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Journal {
  private String id;

  private String name;

  private List<Paper> papers;
}