package org.njuse17advancedse.entityresearcher.controller;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/researchers")
@RestController
public class ResearcherController {

  @GetMapping("/{id}")
  // 根据ID获取学者
  public IResearcher getResearcherById(@PathVariable String id) {
    return new IResearcher(
      id,
      "测试学者" + id,
      new ArrayList<>(),
      new ArrayList<>(),
      new ArrayList<>()
    );
  }

  @GetMapping("/{id}/papers")
  // 获取某作者的论文
  public List<String> getResearcherPapersByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/domains")
  // 获取作者所属领域
  public List<String> getDomainsByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/affiliations")
  // 获取作者所属机构
  public List<String> getAffiliationsByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/basic-info")
  // 根据ID获取学者
  public IResearcherBasic getResearcherBasicInfoById(@PathVariable String id) {
    return new IResearcherBasic(
      id,
      "测试学者" + id,
      new ArrayList<>(),
      new ArrayList<>(),
      new ArrayList<>()
    );
  }
}
