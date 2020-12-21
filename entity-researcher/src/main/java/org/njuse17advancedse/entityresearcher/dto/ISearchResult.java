package org.njuse17advancedse.entityresearcher.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ISearchResult implements Serializable {
  private int count;

  private List<String> result;
}
