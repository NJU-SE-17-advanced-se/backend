package org.njuse17advancedse.entityresearcher.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "researcher_affiliation")
public class ResearcherAffiliation {
  @Id
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
