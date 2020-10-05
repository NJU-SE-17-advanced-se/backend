package org.njuse17advancedse.taskreviewerrecommendation.controller;

import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Paper;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "paper")
public class TaskReviewerRecommendationController {
  private final TaskReviewerRecommendationService recommendationService;

  public TaskReviewerRecommendationController(
    TaskReviewerRecommendationService recommendationService
  ) {
    this.recommendationService = recommendationService;
  }

  @RequestMapping(value = "/recommend", method = RequestMethod.POST)
  private ResponseEntity<List<IResearcher>> getRecommendReviewer(
    @RequestBody Paper paper
  ) {
    return recommendationService.getRecommendReviewer(paper);
  }

  @RequestMapping(value = "/not-recommend", method = RequestMethod.POST)
  private ResponseEntity<List<IResearcher>> getNotRecommendReviewer(
    @RequestBody Paper paper
  ) {
    return recommendationService.getNotRecommendReviewer(paper);
  }
}
