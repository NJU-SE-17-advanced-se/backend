package org.njuse17advancedse.entitypublication.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ISearchResult {
  private int count;
  private List<String> result;
}
