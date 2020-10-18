package org.njuse17advancedse.entitydomain.controller;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/domains")
@RestController
public class DomainController {

  @GetMapping("/{id}")
  // 根据ID获取某领域信息
  public IDomain getDomainById(@PathVariable String id) {
    return new IDomain(
      id,
      "测试领域" + id,
      new ArrayList<>(),
      new ArrayList<>()
    );
  }

  @GetMapping("/{id}/basic-info")
  // 根据ID获取某领域简略信息
  public IDomainBasic getDomainBasicInfoById(@PathVariable String id) {
    return new IDomainBasic(id, "测试领域" + id);
  }

  @GetMapping("/{id}/papers")
  // 某领域下的论文
  public List<String> getPapers(@PathVariable String id) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/researchers")
  // 某领域下的学者
  public List<String> getResearchers(@PathVariable String id) {
    return new ArrayList<>();
  }
}
