package org.njuse17advancedse.apigateway.apps.entity;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.apigateway.domains.entity.Affiliation;
import org.njuse17advancedse.apigateway.domains.entity.Domain;
import org.njuse17advancedse.apigateway.domains.entity.Researcher;
import org.springframework.stereotype.Service;

@Service
public class ResearcherService {

  public Researcher getResearcherById(String id) {
    return new Researcher(
      id,
      "测试学者" + id,
      new Affiliation(
        id,
        "测试机构" + id,
        new ArrayList<>(),
        new ArrayList<>()
      ),
      new ArrayList<>()
    );
  }

  public List<String> getAffiliationsByTimeRange(
    String id,
    String start,
    String end
  ) {
    List<String> res = new ArrayList<>();
    res.add(id);
    return res;
  }

  public List<String> getDomainsByTimeRange(
    String id,
    String start,
    String end
  ) {
    List<String> res = new ArrayList<>();
    res.add(id);
    return res;
  }

  public List<String> getFutureDomains(String id) {
    List<String> res = new ArrayList<>();
    res.add(id);
    return res;
  }
}
