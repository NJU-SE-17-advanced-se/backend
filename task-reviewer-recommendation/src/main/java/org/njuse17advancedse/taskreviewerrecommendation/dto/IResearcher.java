package org.njuse17advancedse.taskreviewerrecommendation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Affiliation;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Paper;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Researcher;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IResearcher {
  private String id;

  private String name;

  private List<Affiliation> affiliations; // 作者所在的机构

  private List<Paper> papers;

  public IResearcher(Researcher researcher) {
    this.id = researcher.getId();
    this.name = researcher.getName();
    this.affiliations = researcher.getAffiliations();
    this.papers = researcher.getPapers();
  }
}
