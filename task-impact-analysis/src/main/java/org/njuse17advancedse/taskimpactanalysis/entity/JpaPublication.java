package org.njuse17advancedse.taskimpactanalysis.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "publication")
public class JpaPublication implements Serializable {
  @Id
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "publication_date")
  private String publicationDate;

  @Column(name = "impact")
  private double impact;

  @OneToMany(
    mappedBy = "publication",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  private List<JpaPaper> papers;
}
