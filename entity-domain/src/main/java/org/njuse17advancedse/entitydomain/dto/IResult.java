package org.njuse17advancedse.entitydomain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IResult implements Serializable {
  private List<String> ids;

  private int count;

  public IResult() {
    ids = new ArrayList<>();
    count = 0;
  }
}
