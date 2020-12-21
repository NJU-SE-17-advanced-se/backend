package org.njuse17advancedse.entitypublication.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPublication implements Serializable {
  private String id;

  private String name;

  private String publicationDate; // 出版日期

  private double impact; // 影响因子

  private List<String> papers;
}
