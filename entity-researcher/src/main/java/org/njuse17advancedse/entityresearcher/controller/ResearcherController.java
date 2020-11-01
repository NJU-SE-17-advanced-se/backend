package org.njuse17advancedse.entityresearcher.controller;

import java.util.List;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.service.ResearcherEntityService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/researchers")
@RestController
public class ResearcherController {
  private final ResearcherEntityService researcherEntityService;

  public ResearcherController(ResearcherEntityService researcherEntityService) {
    this.researcherEntityService = researcherEntityService;
  }

  @GetMapping("/{id}")
  // 根据 id 获取学者
  public IResearcher getResearcherById(@PathVariable String id) {
    return researcherEntityService.getResearcherById(id);
  }

  @GetMapping("/{id}/papers")
  // 获取某作者的论文 id
  public List<String> getResearcherPapersByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return researcherEntityService.getPapersByRid(id, start, end);
  }

  @GetMapping("/{id}/domains")
  // 获取作者所属领域 id
  public List<String> getDomainsByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return researcherEntityService.getDomainByRid(id, start, end);
  }

  @GetMapping("/{id}/affiliations")
  // 获取作者所属机构 id
  public List<String> getAffiliationsByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return researcherEntityService.getAffiliationByRid(id, start, end);
  }

  @GetMapping("/{id}/basic-info")
  // 根据学者 id 获取学者简略信息
  public IResearcherBasic getResearcherBasicInfoById(@PathVariable String id) {
    return researcherEntityService.getResearcherBasicById(id);
  }
}
