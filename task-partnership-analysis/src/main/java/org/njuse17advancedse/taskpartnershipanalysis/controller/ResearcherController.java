package org.njuse17advancedse.taskpartnershipanalysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.service.TaskPartnershipAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "学者" })
@RestController
@RequestMapping(value = "/researchers")
public class ResearcherController {
  private final TaskPartnershipAnalysisService taskPartnershipAnalysisService;

  // 获得合作作者列表
  @ApiOperation("获得合作学者列表")
  @RequestMapping(value = "/{id}/partners", method = RequestMethod.GET)
  private ResponseEntity<List<String>> getPartners(
    @ApiParam("学者 id") @PathVariable String id
  ) {
    return taskPartnershipAnalysisService.getPartners(id);
  }

  // 合作关系分析
  @ApiOperation(
    value = "查看某学者某一时间段的合作关系",
    notes = "需求 5.1：能够识别研究者存在的合作关系，形成社会网络"
  )
  @RequestMapping(value = "/{id}/partners-net", method = RequestMethod.GET)
  private ResponseEntity<IResearcherNet> getPartnership(
    @ApiParam("学者 id") @PathVariable String id,
    @ApiParam("开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam("结束年份，形如'2020'") @RequestParam(required = false) String end
  ) {
    return taskPartnershipAnalysisService.getPartnership(id, start, end);
  }

  // 合作关系预测
  // 返回的是每个学者合作的**可能性**，所以是double
  @ApiOperation(
    value = "预测某学者未来的合作关系",
    notes = "需求 5.2：能够初步预测研究者之间的合作走向"
  )
  @RequestMapping(
    value = "/{id}/potential-partners",
    method = RequestMethod.GET
  )
  private ResponseEntity<Map<String, Double>> getPotentialPartners(
    @ApiParam("学者 id") @PathVariable String id
  ) {
    return taskPartnershipAnalysisService.getPotentialPartners(id);
  }

  public ResearcherController(
    TaskPartnershipAnalysisService taskPartnershipAnalysisService
  ) {
    this.taskPartnershipAnalysisService = taskPartnershipAnalysisService;
  }
}
