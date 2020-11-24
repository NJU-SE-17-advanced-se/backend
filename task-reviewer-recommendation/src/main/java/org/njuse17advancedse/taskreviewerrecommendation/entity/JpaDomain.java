package org.njuse17advancedse.taskreviewerrecommendation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "domain")
public class JpaDomain {
  @Id
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "url")
  private String url;
}
