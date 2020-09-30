package org.njuse17advancedse.taskreviewerrecommendation.controller;

import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "paper")
public class TaskReviewerRecommendationController {
  private final TaskReviewerRecommendationService recommendationService;

  public TaskReviewerRecommendationController(
    TaskReviewerRecommendationService recommendationService
  ) {
    this.recommendationService = recommendationService;
  }

  @RequestMapping(value = "/{id}/recommend", method = RequestMethod.GET)
  private ResponseEntity<List<IResearcher>> getRecommendReviewer(
    @PathVariable String id
  ) {
    return recommendationService.getRecommendReviewer(id);
  }

  @RequestMapping(value = "/{id}/not-recommend", method = RequestMethod.GET)
  private ResponseEntity<List<IResearcher>> getNotRecommendReviewer(
    @PathVariable String id
  ) {
    return recommendationService.getNotRecommendReviewer(id);
  }
}
