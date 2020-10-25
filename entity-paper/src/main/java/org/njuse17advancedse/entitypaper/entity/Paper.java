package org.njuse17advancedse.entitypaper.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.entitypaper.dto.IPaper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
  private String id;

  private String title;

  private String abs;

  private String publication; // 发表位置

  private String publicationDate; // 发表日期，形如'2020'（目前暂定为年）

  private String link;

  private List<String> researchers;

  private List<String> domains;

  private List<String> references;

  public Paper(IPaper iPaper) {
    this.id = iPaper.getId();
    this.title = iPaper.getTitle();
    this.abs = iPaper.getAbs();
    this.publication = iPaper.getPublication();
    this.publicationDate = iPaper.getPublicationDate();
    this.link = iPaper.getLink();
    this.references = iPaper.getResearchers();
    this.domains = iPaper.getDomains();
    this.references = iPaper.getResearchers();
  }
}
