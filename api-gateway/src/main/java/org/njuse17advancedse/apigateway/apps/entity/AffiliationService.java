package org.njuse17advancedse.apigateway.apps.entity;

import java.util.ArrayList;
import org.njuse17advancedse.apigateway.domains.entity.Affiliation;
import org.springframework.stereotype.Service;

@Service
public class AffiliationService {

  public Affiliation getAffiliationById(String id) {
    return new Affiliation(
      id,
      "测试机构" + id,
      new ArrayList<>(),
      new ArrayList<>()
    );
  }
}
