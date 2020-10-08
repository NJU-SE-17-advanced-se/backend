package org.njuse17advancedse.apigateway.domains.repo.entity;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.apigateway.infra.entity.Affiliation;
import org.njuse17advancedse.apigateway.infra.entity.Domain;
import org.njuse17advancedse.apigateway.infra.entity.Researcher;
import org.springframework.stereotype.Repository;

@Repository
public class ResearcherRepo {

  public Researcher getResearcherById(String id) {
    return new Researcher(
      id,
      "测试学者" + id,
      new Affiliation(),
      new ArrayList<>()
    );
  }

  public List<Affiliation> getAffiliationsByTimeRange(
    String id,
    String start,
    String end
  ) {
    List<Affiliation> res = new ArrayList<>();
    res.add(
      new Affiliation(id, start + end, new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  public List<Domain> getDomainsByTimeRange(
    String id,
    String start,
    String end
  ) {
    List<Domain> res = new ArrayList<>();
    res.add(
      new Domain(
        id,
        "测试领域1" + start + end,
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    return res;
  }
}
