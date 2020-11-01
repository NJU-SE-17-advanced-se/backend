package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.PaperService;
import org.njuse17advancedse.apigateway.apps.task.CitationAnalysisService;
import org.njuse17advancedse.apigateway.apps.task.ImpactAnalysisService;
import org.njuse17advancedse.apigateway.apps.task.ReviewerRecommendationService;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaperBasic;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaperUpload;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文" })
@RequestMapping("/papers")
@RestController
public class PaperController {
  private final CitationAnalysisService citationAnalysisService;

  private final ImpactAnalysisService impactAnalysisService;

  private final ModelMapper modelMapper;

  private final PaperService paperService;

  private final ReviewerRecommendationService reviewerRecommendationService;

  @ApiOperation(
    value = "接口 2.1：查看某论文和其他论文的引用情况",
    notes = "需求 7.1：论文引用其它论文及被其它论文引用情况"
  )
  @GetMapping("/{id}/citations")
  public List<String> getPaperCitations(
    @ApiParam(value = "论文 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    return citationAnalysisService.getPaperCitations(id, type);
  }

  @ApiOperation(
    value = "接口 2.2：查看某论文和学者的引用情况",
    notes = "即，论文引用了哪些学者，哪些学者引用了该论文"
  )
  @GetMapping("/{id}/citations/researchers")
  public List<String> getPaperCitedResearchers(
    @ApiParam(value = "论文 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    return citationAnalysisService.getPaperCitedResearchers(id, type);
  }

  @ApiOperation(
    value = "接口 2.3：查看某论文推荐的审稿人",
    notes = "需求 6.1：提交审稿时，能够自动推荐相关审稿人"
  )
  @PostMapping("/recommend-reviewers")
  // TODO: 2.3 和 2.4 两个接口是否需要合并，以满足 REST 的风格？
  public List<String> getRecommendedReviewers(
    @ApiParam(value = "论文内容") @RequestBody IPaperUpload paper
  ) {
    return reviewerRecommendationService.getRecommendReviewer(paper);
  }

  @ApiOperation(
    value = "接口 2.4：查看某论文不推荐的审稿人",
    notes = "需求 6.2：提交审稿时，能够自动屏蔽相关审稿人"
  )
  @PostMapping("/not-recommend-reviewers")
  // TODO: 2.3 和 2.4 两个接口是否需要合并，以满足 REST 的风格？
  public List<String> getNotRecommendedReviewers(
    @ApiParam(value = "论文内容") @RequestBody IPaperUpload paper
  ) {
    return reviewerRecommendationService.getNotRecommendReviewer(paper);
  }

  @ApiOperation(
    value = "接口 2.5：查看某论文的影响力",
    notes = "需求 7.3：评价研究影响力"
  )
  @GetMapping("/{id}/impact")
  public double getImpact(
    @ApiParam(value = "论文 id") @PathVariable String id,
    @RequestParam(value = "影响力指标", defaultValue = "custom") String type
  ) {
    return impactAnalysisService.getPaperImpact(id, type);
  }

  @ApiOperation("根据 id 获取论文信息")
  @GetMapping("/{id}")
  public IPaper getPaper(@PathVariable String id) {
    return modelMapper.map(paperService.getPaper(id), IPaper.class);
  }

  @ApiOperation(
    value = "根据其他查询条件获取论文 id",
    notes = "如果没有任何查询条件，返回全部论文 id"
  )
  @GetMapping("")
  public List<String> getPapers(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  ) {
    return paperService.getPapers(researcher, publication, date);
  }

  @ApiOperation("根据 id 获取论文简略信息")
  @GetMapping("/{id}/basic-info")
  public IPaperBasic getPaperBasicInfo(@PathVariable String id) {
    return modelMapper.map(
      paperService.getPaperBasicInfo(id),
      IPaperBasic.class
    );
  }

  @ApiOperation("获取论文所属领域 id")
  @GetMapping("/{id}/domains")
  public List<String> getDomains(@PathVariable String id) {
    return paperService.getDomains(id);
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
