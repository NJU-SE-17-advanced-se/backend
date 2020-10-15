package org.njuse17advancedse.taskpartnershipanalysis.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R_ScoreData {
  HashMap<Integer, Integer> mapOfYearAndCoNumber;
  HashMap<Integer, Integer> mapOfYearAndSum1;
  HashMap<Integer, Integer> mapOfYearAndSum2;
}
