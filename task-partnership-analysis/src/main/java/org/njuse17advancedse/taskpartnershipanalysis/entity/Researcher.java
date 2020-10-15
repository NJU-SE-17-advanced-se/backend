package org.njuse17advancedse.taskpartnershipanalysis.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Researcher {
  private String id;

  private String name;

  private List<Affiliation> affiliations; // 作者所在的机构

  private List<Paper> papers;
}
