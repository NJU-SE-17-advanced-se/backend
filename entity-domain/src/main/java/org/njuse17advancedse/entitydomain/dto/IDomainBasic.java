package org.njuse17advancedse.entitydomain.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IDomainBasic implements Serializable {
  private String id;

  private String name;
}
