package org.njuse17advancedse.taskdomainprediction.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ResearcherAffiliationPK.class)
@Entity(name = "researcher_affiliation")
public class ResearcherAffiliation implements Serializable {
  @Id
  @Column(insertable = false, updatable = false)
  private String rid;

  @Id
  @Column(insertable = false, updatable = false)
  private String aid;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "rid")
  private JpaResearcher researcher;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "aid")
  private JpaAffiliation affiliation;

  @Id
  @Column(nullable = false, name = "year")
  private int year;
}
