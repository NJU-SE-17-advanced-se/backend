package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.PaperService;
import org.njuse17advancedse.apigateway.apps.task.CitationAnalysisService;
import org.njuse17advancedse.apigateway.apps.task.ImpactAnalysisService;
import org.njuse17advancedse.apigateway.apps.task.ReviewerRecommendationService;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaperUpload;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文" })
@RequestMapping("/paper")
@RestController
public class PaperController {
  private final CitationAnalysisService citationAnalysisService;

  private final ImpactAnalysisService impactAnalysisService;

  private final ModelMapper modelMapper;

  private final PaperService paperService;

  private final ReviewerRecommendationService reviewerRecommendationService;

  @ApiOperation(
    value = "接口 2.1：查看某论文引用情况（不稳定）",
    notes = "需求 7.1：论文引用其它论文"
  )
  @Deprecated
  @GetMapping("/{id}/references")
  // TODO: 明确接口内容
  public List<String> getReferences(
    @ApiParam(value = "论文id") @PathVariable String id
  )
    throws Exception {
    List<Long> referenceIds = citationAnalysisService.getPaperReferences(id);
    // 转换 long 类型为 String 类型
    return referenceIds
      .stream()
      .map(String::valueOf)
      .collect(Collectors.toList());
  }

  @ApiOperation(
    value = "接口 2.2：查看某论文被引情况（不稳定）",
    notes = "需求 7.1：论文被其它论文引用情况"
  )
  @Deprecated
  @GetMapping("/{id}/citations")
  // TODO: 明确接口内容
  public List<String> getCitations(
    @ApiParam(value = "论文id") @PathVariable String id
  )
    throws Exception {
    List<Long> citationIds = citationAnalysisService.getPaperCitations(id);
    // 转换 long 类型为 String 类型
    return citationIds
      .stream()
      .map(String::valueOf)
      .collect(Collectors.toList());
  }

  @ApiOperation(
    value = "接口 2.3：查看某论文推荐的审稿人",
    notes = "需求 6.1：提交审稿时，能够自动推荐相关审稿人"
  )
  @PostMapping("/recommend-reviewers")
  public List<String> getRecommendedReviewers(
    @ApiParam(value = "论文内容") @RequestBody IPaperUpload paper
  )
    throws Exception {
    return reviewerRecommendationService.getPaperRecommendedReviewers(paper);
  }

  @ApiOperation(
    value = "接口 2.4：查看某论文不推荐的审稿人",
    notes = "需求 6.2：提交审稿时，能够自动屏蔽相关审稿人"
  )
  @PostMapping("/not-recommend-reviewers")
  public List<String> getNotRecommendedReviewers(
    @ApiParam(value = "论文内容") @RequestBody IPaperUpload paper
  )
    throws Exception {
    return reviewerRecommendationService.getPaperNotRecommendedReviewers(paper);
  }

  @ApiOperation(
    value = "接口 2.5：查看某论文的影响力",
    notes = "需求 7.3：评价研究影响力"
  )
  @GetMapping("/{id}/impact")
  public double getImpact(@ApiParam(value = "论文id") @PathVariable String id)
    throws Exception {
    return impactAnalysisService.getPaperImpact(id);
  }

  @ApiOperation("根据论文的id获取论文详细信息（WIP)")
  @Deprecated
  @GetMapping("/{id}")
  // TODO: 完成该接口
  public IPaper getPaperById(
    @ApiParam(value = "论文id") @PathVariable String id
  ) {
    return modelMapper.map(paperService.getPaperById(id), IPaper.class);
  }

  public PaperController(
    CitationAnalysisService citationAnalysisService,
    ImpactAnalysisService impactAnalysisService,
    ModelMapper modelMapper,
    PaperService paperService,
    ReviewerRecommendationService reviewerRecommendationService
  ) {
    this.citationAnalysisService = citationAnalysisService;
    this.impactAnalysisService = impactAnalysisService;
    this.modelMapper = modelMapper;
    this.paperService = paperService;
    this.reviewerRecommendationService = reviewerRecommendationService;
  }
}
