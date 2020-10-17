package org.njuse17advancedse.entityresearcher.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IResearcherBasic {
  private String id;

  private String name;

  private List<String> affiliation; // 可能同时身在多个机构

  private List<String> papers; // 五篇代表作

  private List<String> domains;
}
