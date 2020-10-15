package org.njuse17advancedse.taskpartnershipanalysis.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R_ScoreData {
  HashMap<Integer, Integer> mapOfYearAndCoNumber; //年份-作者1与2合作发表论文次数map
  HashMap<Integer, Integer> mapOfYearAndSum1; //年份-作者1的发表论文总数
  HashMap<Integer, Integer> mapOfYearAndSum2; //年份-作者2的发表论文总数
}
