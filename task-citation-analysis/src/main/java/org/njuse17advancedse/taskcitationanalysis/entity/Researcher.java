package org.njuse17advancedse.taskcitationanalysis.entity;

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

  private Affiliation affiliation;

  private List<Paper> papers;
}
