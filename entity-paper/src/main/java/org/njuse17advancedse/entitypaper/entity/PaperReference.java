package org.njuse17advancedse.entitypaper.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "paper_reference")
@IdClass(PaperReferencePK.class)
public class PaperReference implements Serializable {
  @Id
  @Column(insertable = false, updatable = false)
  private String rid;

  @Id
  @Column(insertable = false, updatable = false)
  private String pid;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "rid")
  private JpaPaper reference;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "pid")
  private JpaPaper paper;
}
