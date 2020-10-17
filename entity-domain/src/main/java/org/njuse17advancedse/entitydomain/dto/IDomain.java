package org.njuse17advancedse.entitydomain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IDomain {
  private String id;

  private String name;

  private List<String> researchers;

  private List<String> papers;
}