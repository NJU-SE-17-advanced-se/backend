package org.njuse17advancedse.taskreviewerrecommendation.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IImpact implements Serializable {
  private double impact;

  private String criteria;
}
