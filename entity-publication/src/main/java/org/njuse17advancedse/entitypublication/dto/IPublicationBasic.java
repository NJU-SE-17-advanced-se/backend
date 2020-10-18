package org.njuse17advancedse.entitypublication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPublicationBasic {
  private String id;

  private String name;

  private String publicationDate; // 出版日期
}
