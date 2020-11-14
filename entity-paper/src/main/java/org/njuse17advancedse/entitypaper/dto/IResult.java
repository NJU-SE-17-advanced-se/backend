package org.njuse17advancedse.entitypaper.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IResult {
  private List<String> ids;

  private int count;
}
