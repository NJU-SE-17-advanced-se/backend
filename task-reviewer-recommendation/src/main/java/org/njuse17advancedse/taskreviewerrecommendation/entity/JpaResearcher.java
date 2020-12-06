package org.njuse17advancedse.taskreviewerrecommendation.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "researcher")
public class JpaResearcher implements Serializable {
  @Id
  private String id;

  @Column(name = "name")
  private String name;

  @ManyToMany
  @JoinTable(
    name = "researcher_affiliation",
    joinColumns = @JoinColumn(name = "rid"),
    inverseJoinColumns = @JoinColumn(name = "aid")
  )
  private List<JpaAffiliation> affiliations;

  @ManyToMany
  @JoinTable(
    name = "paper_researcher",
    joinColumns = @JoinColumn(name = "rid"),
    inverseJoinColumns = @JoinColumn(name = "pid")
  )
  private List<JpaPaper> papers;
}
