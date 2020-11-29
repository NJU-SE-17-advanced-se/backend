package org.njuse17advancedse.entityaffiliation.entity;

import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "affiliation")
public class JpaAffiliation {
  @Id
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToMany
  @JoinTable(
    name = "researcher_affiliation",
    joinColumns = @JoinColumn(name = "aid"),
    inverseJoinColumns = @JoinColumn(name = "rid")
  )
  private List<JpaResearcher> researchers;
}
