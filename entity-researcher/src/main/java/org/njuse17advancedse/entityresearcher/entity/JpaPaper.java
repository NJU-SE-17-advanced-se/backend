package org.njuse17advancedse.entityresearcher.entity;

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

  @Column(name = "publication_id")
  private String publicationId;

  @Column(name = "publication_date")
  private String publicationDate;

  @Column(name = "link")
  private String link;

  @ManyToMany
  @JoinTable(
    name = "paper_domain",
    joinColumns = @JoinColumn(name = "pid"),
    inverseJoinColumns = @JoinColumn(name = "dname")
  )
  private List<JpaDomain> domains;
}
