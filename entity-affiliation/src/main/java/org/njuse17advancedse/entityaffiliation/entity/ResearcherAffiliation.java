package org.njuse17advancedse.entityaffiliation.entity;

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

  @JoinColumn
  @Column(nullable = true, name = "year")
  private int year;
}
