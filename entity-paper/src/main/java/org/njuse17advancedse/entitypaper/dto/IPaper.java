package org.njuse17advancedse.entitypaper.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.entitypaper.entity.Paper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IPaper {
  private String id;

  private String title;

  private String abs;

  private String publication; // 发表位置

  private String publicationDate; // 发表日期，形如'2020'（目前暂定为年）

  private String link;

  private List<String> researchers;

  private List<String> domains;

  private List<String> references;

  public IPaper(Paper paper) {
    this.id = paper.getId();
    this.title = paper.getTitle();
    this.abs = paper.getAbs();
    this.publication = paper.getPublication();
    this.publicationDate = paper.getPublicationDate();
    this.link = paper.getLink();
    this.references = paper.getResearchers();
    this.domains = paper.getDomains();
    this.references = paper.getResearchers();
  }
}
