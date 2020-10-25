package org.njuse17advancedse.apigateway.domains.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPublication {
  private String id;

  private String name;

  private String publicationDate; // 出版日期

  private double impact; // 影响因子

  private List<String> papers;
}