package org.njuse17advancedse.taskpartnershipanalysis.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDomainPK implements Serializable {
  private String pid;
  private String did;
}
