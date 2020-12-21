package org.njuse17advancedse.taskimpactanalysis.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ICitation implements Serializable {
  String paperId;
  int citation;
}
