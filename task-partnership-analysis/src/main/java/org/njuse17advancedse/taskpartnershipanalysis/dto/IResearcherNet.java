package org.njuse17advancedse.taskpartnershipanalysis.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IResearcherNet implements Serializable {
  private List<String> partners; //合作作者id列表

  private List<Double[]> weight; //边的权值
}
