package org.njuse17advancedse.entityaffiliation.entity;

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

  private String description;

  private List<String> researchers;

  private List<String> papers;
}
