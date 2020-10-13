package org.njuse17advancedse.apigateway.apps.entity;

import java.util.ArrayList;
import org.njuse17advancedse.apigateway.domains.entity.Domain;
import org.springframework.stereotype.Service;

@Service
public class DomainService {

  public Domain getDomainById(String id) {
    return new Domain(
      id,
      "测试领域" + id,
      new ArrayList<>(),
      new ArrayList<>()
    );
  }
}
