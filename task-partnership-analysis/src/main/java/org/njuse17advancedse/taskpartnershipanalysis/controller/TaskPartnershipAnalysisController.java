package org.njuse17advancedse.taskpartnershipanalysis.controller;

import java.util.List;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.service.TaskPartnershipAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskPartnershipAnalysisController {
  private final TaskPartnershipAnalysisService taskPartnershipAnalysisService;

  public TaskPartnershipAnalysisController(
    TaskPartnershipAnalysisService taskPartnershipAnalysisService
  ) {
    this.taskPartnershipAnalysisService = taskPartnershipAnalysisService;
  }

  @RequestMapping(
    value = "/partnership/{id}/{startDate}/{endDate}",
    method = RequestMethod.GET
  )
  private ResponseEntity<IResearcherNet> getPartnership(
    @PathVariable String id,
    @PathVariable String startDate,
    @PathVariable String endDate
  ) {
    return taskPartnershipAnalysisService.getPartnership(
      id,
      startDate,
      endDate
    );
  }

  @RequestMapping(value = "potential-partners/{id}")
  private ResponseEntity<List<String>> getPotentialPartners(
    @PathVariable String id
  ) {
    return taskPartnershipAnalysisService.getPotentialPartners(id);
  }
}
