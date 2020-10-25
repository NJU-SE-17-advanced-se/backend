package org.njuse17advancedse.taskpartnershipanalysis.controller;

import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.service.ResearcherEntityService;
import org.njuse17advancedse.taskpartnershipanalysis.service.TaskPartnershipAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/partnership")
public class TaskPartnershipAnalysisController {
  private final TaskPartnershipAnalysisService taskPartnershipAnalysisService;

  public TaskPartnershipAnalysisController(
    TaskPartnershipAnalysisService taskPartnershipAnalysisService
  ) {
    this.taskPartnershipAnalysisService = taskPartnershipAnalysisService;
  }

  //获得合作作者列表
  @RequestMapping(value = "/{id}/partners", method = RequestMethod.GET)
  private ResponseEntity<List<String>> getPartners(@PathVariable String id) {
    return taskPartnershipAnalysisService.getPartners(id);
  }

  // 合作关系分析
  @RequestMapping(value = "/partners-net/{id}", method = RequestMethod.GET)
  private ResponseEntity<IResearcherNet> getPartnership(
    @PathVariable String id,
    @RequestParam String start,
    @RequestParam String end
  ) {
    return taskPartnershipAnalysisService.getPartnership(id, start, end);
  }

  @RequestMapping(
    value = "/potential-partners/{id}",
    method = RequestMethod.GET
  )
  private ResponseEntity<Map<String, Double>> getPotentialPartners(
    @PathVariable String id
  ) {
    return taskPartnershipAnalysisService.getPotentialPartners(id);
  }
}
