package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.ResearcherService;
import org.njuse17advancedse.apigateway.apps.task.CitationAnalysisService;
import org.njuse17advancedse.apigateway.apps.task.ImpactAnalysisService;
import org.njuse17advancedse.apigateway.apps.task.PartnershipAnalysisService;
import org.njuse17advancedse.apigateway.interfaces.dto.IResearcher;
import org.njuse17advancedse.apigateway.interfaces.dto.IResearcherBasic;
import org.njuse17advancedse.apigateway.interfaces.dto.IResearcherNet;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "学者" })
@RequestMapping("/researchers")
@RestController
public class ResearcherController {
  private final CitationAnalysisService citationAnalysisService;

  private final ImpactAnalysisService impactAnalysisService;

  private final ModelMapper modelMapper;

  private final PartnershipAnalysisService partnershipAnalysisService;

  private final ResearcherService researcherService;

  @ApiOperation(
    value = "接口 1.1：查看某学者某一时间段所在机构",
    notes = "需求 3.1：能够识别同一研究者在不同时间的单位情况"
  )
  @GetMapping("/{id}/affiliations")
  public List<String> getAffiliationsByTimeRange(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020'") @RequestParam String end
  ) {
    return researcherService.getAffiliationsByTimeRange(id, start, end);
  }

  @ApiOperation(
    value = "接口 1.2：查看某学者某一时间段的研究方向",
    notes = "需求 4.1：能够识别研究者的兴趣\n\n" +
    "需求 4.2：能够识别研究者在不同阶段的研究兴趣"
  )
  @GetMapping("/{id}/domains")
  public List<String> getDomainsByTimeRange(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020'") @RequestParam String end
  ) {
    return researcherService.getDomainsByTimeRange(id, start, end);
  }

  @ApiOperation(
    value = "接口 1.3：预测某学者未来的研究方向（WIP）",
    notes = "需求 4.3：能够初步预测研究者的研究兴趣"
  )
  @Deprecated
  @GetMapping("/{id}/future/domains")
  // TODO: 完成该接口
  public List<String> getFutureDomains(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    return new ArrayList<>();
  }

  @ApiOperation(
    value = "接口 1.4：查看某学者某一时间段的合作关系",
    notes = "需求 5.1：能够识别研究者存在的合作关系，形成社会网络"
  )
  @GetMapping("/{id}/partnership")
  public IResearcherNet getPartnershipByTimeRange(
    @ApiParam(value = "学者id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020'") @RequestParam String end
  ) {
    return partnershipAnalysisService.getPartnership(id, start, end);
  }

  @ApiOperation(
    value = "接口 1.5：预测某学者未来的合作关系",
    notes = "需求 5.2：能够初步预测研究者之间的合作走向"
  )
  @GetMapping("/{id}/partnership/prediction")
  // 返回的是每个学者合作的**可能性**，所以是double
  public Map<String, Double> getPartnershipPrediction(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    return partnershipAnalysisService.getPotentialPartners(id);
  }

  @ApiOperation(
    value = "接口 1.6：查看某学者的引用和被引情况",
    notes = "需求 7.2：研究者引用其他研究者及被其他研究者引用情况"
  )
  @GetMapping("/{id}/citations")
  public List<String> getResearcherCitations(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    return citationAnalysisService.getResearcherCitations(id, type);
  }

  @ApiOperation(
    value = "接口 1.7：查看某学者的论文引用和被引情况",
    notes = "需求 7.2：研究者引用其他研究者的论文及论文被其他研究者引用情况"
  )
  @GetMapping("/{id}/citations/papers")
  public Map<String, List<String>> getResearcherPapersCitations(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    return citationAnalysisService.getResearcherPapersCitations(id, type);
  }

  @ApiOperation(
    value = "接口 1.8：查看某学者的论文引用其他学者和被其他学者引用情况",
    notes = "即，某个学者的论文分别引用了哪些学者，某个学者的论文分别被哪些学者引用"
  )
  @GetMapping("/{id}/citations/papers/researchers")
  public Map<String, List<String>> getResearcherPapersCitedResearchers(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    return citationAnalysisService.getResearcherPapersCitedResearchers(
      id,
      type
    );
  }

  @ApiOperation(
    value = "接口 1.9：查看某学者的影响力",
    notes = "需求 7.3：评价研究者影响力"
  )
  @GetMapping("/{id}/impact")
  public double getImpact(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @RequestParam(value = "影响力指标", defaultValue = "h-index") String type
  ) {
    return impactAnalysisService.getResearcherImpact(id, type);
  }

  @ApiOperation("根据 id 获取学者详细信息")
  @GetMapping("/{id}")
  public IResearcher getResearcherById(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    return modelMapper.map(
      researcherService.getResearcherById(id),
      IResearcher.class
    );
  }

  @ApiOperation("根据学者 id 获取学者简略信息")
  @GetMapping("/{id}/basic-info")
  public IResearcherBasic getResearcherBasicInfoById(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    return modelMapper.map(
      researcherService.getResearcherById(id),
      IResearcherBasic.class
    );
  }

  @ApiOperation("获取某作者的论文 id")
  @GetMapping("/{id}/papers")
  public List<String> getResearcherPapersByTimeRange(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return researcherService.getResearcherPapersByTimeRange(id, start, end);
  }

  public ResearcherController(
    CitationAnalysisService citationAnalysisService,
    ImpactAnalysisService impactAnalysisService,
    ModelMapper modelMapper,
    PartnershipAnalysisService partnershipAnalysisService,
    ResearcherService researcherService
  ) {
    this.citationAnalysisService = citationAnalysisService;
    this.impactAnalysisService = impactAnalysisService;
    this.modelMapper = modelMapper;
    this.partnershipAnalysisService = partnershipAnalysisService;
    this.researcherService = researcherService;
  }
}
