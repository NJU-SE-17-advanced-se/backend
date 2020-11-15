package org.njuse17advancedse.taskreviewerrecommendation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文" })
@RestController
@RequestMapping(value = "/papers")
public class TaskReviewerRecommendationController {
  private final TaskReviewerRecommendationService recommendationService;

  @ApiOperation(
    value = "查看某论文推荐的审稿人",
    notes = "需求 6.1：提交审稿时，能够自动推荐相关审稿人"
  )
  @RequestMapping(value = "/recommend", method = RequestMethod.POST)
  private ResponseEntity<List<String>> getRecommendReviewer(
    @ApiParam("论文详细信息") @RequestBody IPaperUpload iPaperUpload
  ) {
    return recommendationService.getRecommendReviewer(iPaperUpload);
  }

  @ApiOperation(
    value = "查看某论文不推荐的审稿人",
    notes = "需求 6.2：提交审稿时，能够自动屏蔽相关审稿人"
  )
  @RequestMapping(value = "/not-recommend", method = RequestMethod.POST)
  private ResponseEntity<List<String>> getNotRecommendReviewer(
    @ApiParam("论文详细信息") @RequestBody IPaperUpload iPaperUpload
  ) {
    return recommendationService.getNotRecommendReviewer(iPaperUpload);
  }

  public TaskReviewerRecommendationController(
    TaskReviewerRecommendationService recommendationService
  ) {
    this.recommendationService = recommendationService;
  }
}
