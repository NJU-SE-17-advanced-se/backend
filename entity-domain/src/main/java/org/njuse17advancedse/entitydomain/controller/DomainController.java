package org.njuse17advancedse.entitydomain.controller;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/domains")
@RestController
public class DomainController {
  @Autowired
  DomainService service;

  @GetMapping("/{id}")
  // 根据 id 获取某领域信息
  public IDomain getDomainById(@PathVariable String id) {
    return service.getDomainById(id);
  }

  @GetMapping("/{id}/basic-info")
  // 根据 id 获取某领域简略信息
  public IDomainBasic getDomainBasicInfoById(@PathVariable String id) {
    return service.getDomainBasicInfoById(id);
  }

  @GetMapping("/{id}/papers")
  // 根据领域 id 获取某领域下的论文 id
  public List<String> getPapers(@PathVariable String id) {
    return service.getPapers(id);
  }

  @GetMapping("/{id}/researchers")
  // 根据领域 id 获取某领域下的学者 id
  public List<String> getResearchers(@PathVariable String id) {
    return service.getResearchers(id);
  }
}
