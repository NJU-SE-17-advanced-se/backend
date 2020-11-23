package org.njuse17advancedse.taskreviewerrecommendation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

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
  private List<String> getRecommendReviewer(
    @ApiParam("论文详细信息") @RequestBody IPaperUpload iPaperUpload
  ) {
    checkPaperUpload(iPaperUpload);
    List<String> reviewers = recommendationService.getRecommendReviewer(
      iPaperUpload
    );
    if (reviewers == null) {
      throw Problem.valueOf(Status.NOT_FOUND, "journal id not found");
    }
    return reviewers;
  }

  @ApiOperation(
    value = "查看某论文不推荐的审稿人",
    notes = "需求 6.2：提交审稿时，能够自动屏蔽相关审稿人"
  )
  @RequestMapping(value = "/not-recommend", method = RequestMethod.POST)
  private List<String> getNotRecommendReviewer(
    @ApiParam("论文详细信息") @RequestBody IPaperUpload iPaperUpload
  ) {
    checkPaperUpload(iPaperUpload);
    List<String> reviewers = recommendationService.getNotRecommendReviewer(
      iPaperUpload
    );
    if (reviewers == null) {
      throw Problem.valueOf(Status.NOT_FOUND, "journal id not found");
    }
    return reviewers;
  }

  /**
   * 检查上传的论文实体
   * @param iPaperUpload 上传论文实体
   */
  private void checkPaperUpload(IPaperUpload iPaperUpload) {
    if (
      iPaperUpload.getResearcherIds() == null ||
      iPaperUpload.getResearcherIds().size() == 0
    ) {
      throw Problem.valueOf(Status.BAD_REQUEST, "researchers is null");
    }
    if (
      iPaperUpload.getDomainIds() == null ||
      iPaperUpload.getDomainIds().size() == 0
    ) {
      throw Problem.valueOf(Status.BAD_REQUEST, "domains is null");
    }
    if (
      iPaperUpload.getPublication() == null ||
      iPaperUpload.getPublication().equals("")
    ) {
      throw Problem.valueOf(Status.BAD_REQUEST, "journal is null");
    }
    if (iPaperUpload.getReferenceIds() == null) {
      iPaperUpload.setReferenceIds(new ArrayList<>());
    }
    if (iPaperUpload.getDate() == null || iPaperUpload.getDate().equals("")) {
      Calendar cal = Calendar.getInstance();
      iPaperUpload.setDate(cal.get(Calendar.YEAR) + "");
    }
    int year = 0;
    try {
      year = Integer.parseInt(iPaperUpload.getDate());
      iPaperUpload.setDate(year + "");
    } catch (NumberFormatException e) {
      throw Problem.valueOf(Status.BAD_REQUEST, "publication date is illegal");
    }
  }

  public TaskReviewerRecommendationController(
    TaskReviewerRecommendationService recommendationService
  ) {
    this.recommendationService = recommendationService;
  }
}
