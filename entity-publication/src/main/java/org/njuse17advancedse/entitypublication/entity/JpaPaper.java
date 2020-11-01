package org.njuse17advancedse.entitypublication.entity;

import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "paper")
public class JpaPaper {
  @Id
  private String id;

  @Column(name = "title")
  private String title;

  @Column(name = "abs")
  private String abs;

  @ManyToOne(
    cascade = { CascadeType.MERGE, CascadeType.REFRESH },
    optional = false
  )
  @JoinColumn(name = "publication_id")
  private JpaPublication publication;

  @Column(name = "publication_date")
  private int publicationDate;

  @Column(name = "link")
  private String link;

  @Column(name = "citation")
  private int citation;

  @ManyToMany
  @JoinTable(
    name = "paper_domain",
    joinColumns = @JoinColumn(name = "pid"),
    inverseJoinColumns = @JoinColumn(name = "did")
  )
  private List<JpaDomain> domains;
}
