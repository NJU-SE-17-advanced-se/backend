package org.njuse17advancedse.taskcitationanalysis.entity;

import java.io.Serializable;
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
public class JpaDomain implements Serializable {
  @Id
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "url")
  private String url;
}
