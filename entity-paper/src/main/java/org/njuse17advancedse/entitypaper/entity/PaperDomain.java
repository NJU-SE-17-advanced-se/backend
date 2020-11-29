package org.njuse17advancedse.entitypaper.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PaperDomainPK.class)
@Entity(name = "paper_domain")
public class PaperDomain implements Serializable {
  @Id
  @Column(insertable = false, updatable = false)
  private String pid;

  @Id
  @Column(insertable = false, updatable = false)
  private String did;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "did")
  private JpaDomain domain;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "pid")
  private JpaPaper paper;
}
