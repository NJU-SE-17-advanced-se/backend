package org.njuse17advancedse.entitypublication.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publication {
  private String id;

  private String name;

  private String publicationDate; // 出版日期

  private String impact; // 影响因子

  private List<String> papers;
}
