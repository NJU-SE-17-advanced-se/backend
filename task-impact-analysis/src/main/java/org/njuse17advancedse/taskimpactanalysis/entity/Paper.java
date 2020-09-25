package org.njuse17advancedse.taskimpactanalysis.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
  private String id;

  private String title;

  private String abs; // 与关键字冲突

  private String link;

  private List<Researcher> researchers;

  private List<Domain> domains;

  private List<Paper> references;

  public List<String> getQuotedIds() {
    return new ArrayList<>();
  }

  public String getJournal() {
    return "";
  }
}