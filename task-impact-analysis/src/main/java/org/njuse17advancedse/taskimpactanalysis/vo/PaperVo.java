package org.njuse17advancedse.taskimpactanalysis.vo;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperVo {
  Long id;

  String publisher;
  //引用它的论文
  List<Long> quotingIds;

  //被它引用的论文
  List<Long> quotedIds;
}
