package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.ResearcherService;
import org.njuse17advancedse.apigateway.apps.task.CitationAnalysisService;
import org.njuse17advancedse.apigateway.apps.task.ImpactAnalysisService;
import org.njuse17advancedse.apigateway.interfaces.dto.IResearcher;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "学者" })
@RequestMapping("/researcher")
@RestController
public class ResearcherController {
  private final CitationAnalysisService citationAnalysisService;

  private final ImpactAnalysisService impactAnalysisService;

  private final ModelMapper modelMapper;

  private final ResearcherService researcherService;

  @ApiOperation(
    value = "接口 1.1：查看某学者某一时间段所在机构（WIP）",
    notes = "需求 3.1：能够识别同一研究者在不同时间的单位情况"
  )
  @Deprecated
  @GetMapping("/{id}/affiliations")
  // TODO: 完成该接口
  public List<String> getAffiliationsByTimeRange(
    @ApiParam(value = "学者id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    return researcherService.getAffiliationsByTimeRange(id, start, end);
  }

  @ApiOperation(
    value = "接口 1.2：查看某学者某一时间段的研究方向（WIP）",
    notes = "需求 4.1：能够识别研究者的兴趣\n\n" +
    "需求 4.2：能够识别研究者在不同阶段的研究兴趣"
  )
  @Deprecated
  @GetMapping("/{id}/domains")
  // TODO: 完成该接口
  public List<String> getDomainsByTimeRange(
    @ApiParam(value = "学者id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
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
    @ApiParam(value = "学者id") @PathVariable String id
  ) {
    return researcherService.getFutureDomains(id);
  }

  @ApiOperation(
    value = "接口 1.4：查看某学者某一时间段的合作关系（WIP）",
    notes = "需求 5.1：能够识别研究者存在的合作关系，形成社会网络"
  )
  @Deprecated
  @GetMapping("/{id}/partnership")
  // TODO: 完成该接口
  public List<String> getPartnershipByTimeRange(
    @ApiParam(value = "学者id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    return new ArrayList<>();
  }

  @ApiOperation(
    value = "接口 1.5：预测某学者未来的合作关系（WIP）",
    notes = "需求 5.2：能够初步预测研究者之间的合作走向"
  )
  @Deprecated
  @GetMapping("/{id}/future/partnership")
  // TODO: 完成该接口
  public List<String> getFuturePartnership(
    @ApiParam(value = "学者id") @PathVariable String id
  ) {
    return new ArrayList<>();
  }

  @ApiOperation(
    value = "接口 1.6：查看某学者的论文引用情况（不稳定）",
    notes = "需求 7.2：研究者引用其他研究者（的论文）"
  )
  @Deprecated
  @GetMapping("/{id}/references")
  // TODO: 明确接口内容
  public Map<String, List<String>> getReferences(
    @ApiParam(value = "学者id") @PathVariable String id
  )
    throws Exception {
    Map<String, List<String>> res = new HashMap<>();
    Map<Long, List<Long>> references = citationAnalysisService.getResearcherReferences(
      id
    );
    for (Map.Entry<Long, List<Long>> reference : references.entrySet()) {
      res.put(
        reference.getKey().toString(),
        reference
          .getValue()
          .stream()
          .map(String::valueOf)
          .collect(Collectors.toList())
      );
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.7：查看某学者的论文被引情况（不稳定）",
    notes = "需求 7.2：研究者被其他研究者引用情况"
  )
  @Deprecated
  @GetMapping("/{id}/citations")
  // TODO: 明确接口内容
  public Map<String, List<String>> getCitations(
    @ApiParam(value = "学者id") @PathVariable String id
  )
    throws Exception {
    Map<String, List<String>> res = new HashMap<>();
    Map<Long, List<Long>> citations = citationAnalysisService.getResearcherCitations(
      id
    );
    for (Map.Entry<Long, List<Long>> citation : citations.entrySet()) {
      res.put(
        citation.getKey().toString(),
        citation
          .getValue()
          .stream()
          .map(String::valueOf)
          .collect(Collectors.toList())
      );
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.8：查看某学者的影响力",
    notes = "需求 7.3：评价研究者影响力"
  )
  @GetMapping("/{id}/impact")
  public double getImpact(@ApiParam(value = "学者id") @PathVariable String id)
    throws Exception {
    return impactAnalysisService.getResearcherImpact(id);
  }

  @ApiOperation("根据学者的id获取学者详细信息（WIP)")
  @Deprecated
  @GetMapping("/{id}")
  // TODO: 完成该接口
  public IResearcher getResearcherById(
    @ApiParam(value = "学者id") @PathVariable String id
  ) {
    return modelMapper.map(
      researcherService.getResearcherById(id),
      IResearcher.class
    );
  }

  public ResearcherController(
    CitationAnalysisService citationAnalysisService,
    ImpactAnalysisService impactAnalysisService,
    ModelMapper modelMapper,
    ResearcherService researcherService
  ) {
    this.citationAnalysisService = citationAnalysisService;
    this.impactAnalysisService = impactAnalysisService;
    this.modelMapper = modelMapper;
    this.researcherService = researcherService;
  }
}
