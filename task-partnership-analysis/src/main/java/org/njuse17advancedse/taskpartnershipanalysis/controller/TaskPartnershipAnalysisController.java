package org.njuse17advancedse.taskpartnershipanalysis.controller;

import java.util.Map;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
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
