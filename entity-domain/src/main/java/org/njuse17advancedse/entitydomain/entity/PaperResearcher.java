package org.njuse17advancedse.entitydomain.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "paper_researcher")
@IdClass(PaperResearcherPK.class)
public class PaperResearcher implements Serializable {
  @Id
  @Column(insertable = false, updatable = false)
  private String rid;

  @Id
  @Column(insertable = false, updatable = false)
  private String pid;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "rid")
  private JpaResearcher researcher;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "pid")
  private JpaPaper paper;
}
