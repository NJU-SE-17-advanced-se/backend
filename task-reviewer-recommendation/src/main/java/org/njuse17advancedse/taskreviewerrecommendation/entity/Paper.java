package org.njuse17advancedse.taskreviewerrecommendation.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
  private String id; // 论文标识id

  private String title; // 论文标题

  private String journal; // 论文来源刊物

  private String date; // 论文发表时间

  private String abs; // 与关键字冲突

  private String link; // 论文链接

  private List<Researcher> researchers; // 论文作者

  private List<Domain> domains; // 论文研究领域

  private List<Paper> references; // 论文引用
}
