package org.njuse17advancedse.taskimpactanalysis.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPaper implements Serializable {
  private String id;

  private String title;

  private String abs;

  private String publication; // 发表位置

  private String publicationDate; // 发表日期，形如'2020'（目前暂定为年）

  private String link;
  private int citations; // 引用数

  private List<String> researchers;

  private List<String> domains;

  private List<String> references;
}
