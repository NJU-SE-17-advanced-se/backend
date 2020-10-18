package org.njuse17advancedse.entitydomain.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domain {
  private String id;

  private String name;

  private List<String> researchers;

  private List<String> papers;
}
