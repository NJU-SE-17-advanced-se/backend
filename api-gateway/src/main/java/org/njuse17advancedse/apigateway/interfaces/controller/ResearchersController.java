package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = { "学者扩展（存在性能问题）" })
@Deprecated
@RequestMapping("/researchers")
@RestController
public class ResearchersController {
  private final CitationAnalysisService citationAnalysisService;

  private final ImpactAnalysisService impactAnalysisService;

  private final ModelMapper modelMapper;

  private final ResearcherService researcherService;

  @ApiOperation(
    value = "接口 1.1.2：查看某些学者某一时间段所在机构（WIP）",
    notes = "接口 1.1 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/affiliations")
  // TODO: 完成该接口
  public Map<String, List<String>> getAffiliationsByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    Map<String, List<String>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getAffiliationsByTimeRange(id, start, end));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.2.2：查看某些学者某一时间段的研究方向（WIP）",
    notes = "接口 1.2 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/domains")
  // TODO: 完成该接口
  public Map<String, List<String>> getDomainsByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    Map<String, List<String>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getDomainsByTimeRange(id, start, end));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.3.2：预测某些学者未来的研究方向（WIP）",
    notes = "接口 1.3 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/future/domains")
  // TODO: 完成该接口
  public Map<String, List<String>> getFutureDomains(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    Map<String, List<String>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getFutureDomains(id));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.4.2：查看某些学者某一时间段的合作关系（WIP）",
    notes = "接口 1.4 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/partnership")
  // TODO: 完成该接口
  public Map<String, List<IResearcher>> getPartnershipByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    return new HashMap<>();
  }

  @ApiOperation(
    value = "接口 1.5.2：预测某些学者未来的合作关系（WIP）",
    notes = "接口 1.5 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/future/partnership")
  // TODO: 完成该接口
  public Map<String, List<String>> getFuturePartnership(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    return new HashMap<>();
  }

  @ApiOperation(
    value = "接口 1.6.2：查看某些学者的论文引用情况（不稳定）",
    notes = "接口 1.6 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/references")
  public Map<String, Map<String, List<String>>> getReferences(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, Map<String, List<String>>> res = new HashMap<>();
    for (String id : ids) {
      Map<String, List<String>> researcherReferencesIds = new HashMap<>();
      Map<Long, List<Long>> references = citationAnalysisService.getResearcherReferences(
        id
      );
      for (Map.Entry<Long, List<Long>> citation : references.entrySet()) {
        researcherReferencesIds.put(
          citation.getKey().toString(),
          citation
            .getValue()
            .stream()
            .map(String::valueOf)
            .collect(Collectors.toList())
        );
      }
      res.put(id, researcherReferencesIds);
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.7.2：查看某些学者的论文被引情况（不稳定）",
    notes = "接口 1.7 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/citations")
  public Map<String, Map<String, List<String>>> getCitations(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, Map<String, List<String>>> res = new HashMap<>();
    for (String id : ids) {
      Map<String, List<String>> researcherCitationIds = new HashMap<>();
      Map<Long, List<Long>> citations = citationAnalysisService.getResearcherCitations(
        id
      );
      for (Map.Entry<Long, List<Long>> citation : citations.entrySet()) {
        researcherCitationIds.put(
          citation.getKey().toString(),
          citation
            .getValue()
            .stream()
            .map(String::valueOf)
            .collect(Collectors.toList())
        );
      }
      res.put(id, researcherCitationIds);
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.8.2：查看某些学者的影响力",
    notes = "接口 1.8 的附属版本"
  )
  @GetMapping("/{ids}/impact")
  public Map<String, Double> getImpact(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, Double> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, impactAnalysisService.getResearcherImpact(id));
    }
    return res;
  }

  @ApiOperation("根据学者**们**的id获取学者详细信息（WIP)")
  @GetMapping("/{ids}")
  // TODO: 完成该接口
  public List<IResearcher> getResearcherById(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    return ids
      .stream()
      .map(
        id ->
          modelMapper.map(
            researcherService.getResearcherById(id),
            IResearcher.class
          )
      )
      .collect(Collectors.toList());
  }

  public ResearchersController(
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
