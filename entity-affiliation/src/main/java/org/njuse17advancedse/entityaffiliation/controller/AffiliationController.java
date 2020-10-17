package org.njuse17advancedse.entityaffiliation.controller;

import java.util.ArrayList;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/affiliation")
@RestController
public class AffiliationController {

  @GetMapping("/{id}")
  public IAffiliation getAffiliationById(String id) {
    return new IAffiliation(
      "1",
      "测试机构1",
      "这是一个测试机构",
      new ArrayList<>(),
      new ArrayList<>()
    );
  }

  @GetMapping("/{id}/basic-info")
  public IAffiliationBasic getAffiliationBasicInfoById(String id) {
    return new IAffiliationBasic("2", "测试机构2", "这是一个测试机构");
  }
}
