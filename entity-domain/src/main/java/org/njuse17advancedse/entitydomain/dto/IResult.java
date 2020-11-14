package org.njuse17advancedse.entitydomain.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class IResult {
  private List<String> ids;

  private int count;

  public IResult() {
    ids = new ArrayList<>();
    count = 0;
  }
}
