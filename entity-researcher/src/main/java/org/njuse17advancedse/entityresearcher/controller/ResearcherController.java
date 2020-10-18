package org.njuse17advancedse.entityresearcher.controller;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
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
  // 获取某段时间某作者的论文
  public List<String> getResearcherPapersByTimeRange(
    @PathVariable String id,
    @RequestParam String start,
    @RequestParam String end
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/domains")
  // 获取作者所属领域
  public List<String> getDomains(@PathVariable String id) {
    return new ArrayList<>();
  }
}
