package org.njuse17advancedse.taskimpactanalysis.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperResearcherPK implements Serializable {
  private String rid;
  private String pid;
}
