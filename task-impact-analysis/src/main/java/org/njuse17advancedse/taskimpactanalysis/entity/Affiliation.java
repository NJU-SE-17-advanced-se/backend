package org.njuse17advancedse.taskimpactanalysis.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Affiliation {
  private String id;

  private String name;

  private List<Researcher> researchers;

  private List<Paper> papers;
}
