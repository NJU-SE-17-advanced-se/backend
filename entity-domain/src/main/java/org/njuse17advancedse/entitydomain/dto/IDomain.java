package org.njuse17advancedse.entitydomain.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IDomain implements Serializable {
  private String id;

  private String name;

  private List<String> researchers;

  private List<String> papers;
}
