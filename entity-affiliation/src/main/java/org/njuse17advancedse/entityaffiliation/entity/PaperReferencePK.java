package org.njuse17advancedse.entityaffiliation.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperReferencePK implements Serializable {
  private String pid;
  private String rid;
}
