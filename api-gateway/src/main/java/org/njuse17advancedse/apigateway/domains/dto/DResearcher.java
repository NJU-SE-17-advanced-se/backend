package org.njuse17advancedse.apigateway.domains.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DResearcher {
  private String id;

  private String name;

  private List<String> affiliation; // 可能同时身在多个机构

  private List<String> papers;

  private List<String> domains;
}