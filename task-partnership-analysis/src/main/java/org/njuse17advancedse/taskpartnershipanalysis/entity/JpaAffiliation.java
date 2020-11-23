package org.njuse17advancedse.taskpartnershipanalysis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
}
