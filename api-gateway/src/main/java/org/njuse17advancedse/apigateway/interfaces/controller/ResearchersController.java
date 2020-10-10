package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.apigateway.apps.service.ResearcherService;
import org.njuse17advancedse.apigateway.interfaces.dto.researcher.*;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "学者扩展" })
@RequestMapping("/researchers")
@RestController
public class ResearchersController {
  private final ResearcherService researcherService;

  @ApiOperation(
    value = "接口 1.1.2：查看某些学者某一时间段所在机构",
    notes = "接口 1.1 的附属版本"
  )
  @GetMapping("/{ids}/affiliations")
  public Map<String, List<IAffiliation>> getAffiliationsByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    Map<String, List<IAffiliation>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getAffiliationsByTimeRange(id, start, end));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.2.2：查看某些学者某一时间段的研究方向",
    notes = "接口 1.2 的附属版本"
  )
  @GetMapping("/{ids}/domains")
  public Map<String, List<IDomain>> getDomainsByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    Map<String, List<IDomain>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getDomainsByTimeRange(id, start, end));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.3.2：预测某些学者未来的研究方向",
    notes = "接口 1.3 的附属版本"
  )
  @GetMapping("/{ids}/future/domains")
  public Map<String, List<IDomain>> getFutureDomains(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    Map<String, List<IDomain>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getFutureDomains(id));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.4.2：查看某些学者某一时间段的合作关系",
    notes = "接口 1.4 的附属版本"
  )
  @GetMapping("/{ids}/partnership")
  public Map<String, List<IResearcher>> getPartnershipByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    Map<String, List<IResearcher>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getPartnershipByTimeRange(id, start, end));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.5.2：预测某些学者未来的合作关系",
    notes = "接口 1.5 的附属版本"
  )
  @GetMapping("/{ids}/future/partnership")
  public Map<String, List<IResearcher>> getFuturePartnership(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    Map<String, List<IResearcher>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getFuturePartnership(id));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.6.2：查看某些学者的论文引用情况",
    notes = "接口 1.6 的附属版本"
  )
  @GetMapping("/{ids}/references")
  public Map<String, Map<String, List<IPaper>>> getReferences(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, Map<String, List<IPaper>>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getReferences(id));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.7.2：查看某些学者的论文被引情况",
    notes = "接口 1.7 的附属版本"
  )
  @GetMapping("/{ids}/citations")
  public Map<String, Map<String, List<IPaper>>> getCitations(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, Map<String, List<IPaper>>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, researcherService.getCitations(id));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 1.8.2：查看某些学者的影响力",
    notes = "接口 1.8 的附属版本"
  )
  @GetMapping("/{ids}/impact")
  public Map<String, IImpact> getImpact(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, IImpact> res = new HashMap<>();
    for (String id : ids) {
      String criteria = "H-index";
      double impact = researcherService.getImpact(id);
      res.put(id, new IImpact(impact, criteria));
    }
    return res;
  }

  public ResearchersController(ResearcherService researcherService) {
    this.researcherService = researcherService;
  }
}
