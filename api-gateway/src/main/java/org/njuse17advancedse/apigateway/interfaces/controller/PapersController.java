package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.PaperService;
import org.njuse17advancedse.apigateway.apps.task.CitationAnalysisService;
import org.njuse17advancedse.apigateway.apps.task.ImpactAnalysisService;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "论文扩展（存在性能问题）" })
@Deprecated
@RequestMapping("/papers")
@RestController
public class PapersController {
  private final CitationAnalysisService citationAnalysisService;

  private final ImpactAnalysisService impactAnalysisService;

  private final ModelMapper modelMapper;

  private final PaperService paperService;

  @ApiOperation(
    value = "接口 2.1.2：查看某些论文引用情况（不稳定）",
    notes = "接口 2.1 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/references")
  // TODO: 明确接口内容
  public Map<String, List<String>> getReferences(
    @ApiParam(value = "论文id的列表") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, List<String>> res = new HashMap<>();
    for (String id : ids) {
      List<Long> referenceIds = citationAnalysisService.getPaperReferences(id);
      res.put(
        id,
        referenceIds.stream().map(String::valueOf).collect(Collectors.toList())
      );
    }
    return res;
  }

  @ApiOperation(
    value = "接口 2.2.2：查看某些论文被引情况（不稳定）",
    notes = "接口 2.2 的附属版本"
  )
  @Deprecated
  @GetMapping("/{ids}/citations")
  // TODO: 明确接口内容
  public Map<String, List<String>> getCitations(
    @ApiParam(value = "论文id的列表") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, List<String>> res = new HashMap<>();
    for (String id : ids) {
      List<Long> citationIds = citationAnalysisService.getPaperCitations(id);
      res.put(
        id,
        citationIds.stream().map(String::valueOf).collect(Collectors.toList())
      );
    }
    return res;
  }

  @ApiOperation(
    value = "接口 2.5.2：查看某些论文的影响力",
    notes = "接口 2.5 的附属版本"
  )
  @GetMapping("/{ids}/impact")
  public Map<String, Double> getImpact(
    @ApiParam(value = "论文id的列表") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, Double> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, impactAnalysisService.getPaperImpact(id));
    }
    return res;
  }

  @ApiOperation("根据论文**们**的id获取论文详细信息（WIP）")
  @GetMapping("/{ids}")
  // TODO: 完成该接口
  public List<IPaper> getPapersByIds(
    @ApiParam(value = "论文**们**的id") @PathVariable List<String> ids
  ) {
    return ids
      .stream()
      .map(id -> modelMapper.map(paperService.getPaperById(id), IPaper.class))
      .collect(Collectors.toList());
  }

  public PapersController(
    CitationAnalysisService citationAnalysisService,
    ImpactAnalysisService impactAnalysisService,
    ModelMapper modelMapper,
    PaperService paperService
  ) {
    this.citationAnalysisService = citationAnalysisService;
    this.impactAnalysisService = impactAnalysisService;
    this.modelMapper = modelMapper;
    this.paperService = paperService;
  }
}
