package org.njuse17advancedse.taskimpactanalysis.vo;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScholarVo {
  Long id;

  List<PaperVo> papers;
}
