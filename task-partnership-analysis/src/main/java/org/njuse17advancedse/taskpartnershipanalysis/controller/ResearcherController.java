package org.njuse17advancedse.taskpartnershipanalysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.service.TaskPartnershipAnalysisService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Api(tags = { "学者" })
@RestController
@RequestMapping(value = "/researchers")
public class ResearcherController {
  private final TaskPartnershipAnalysisService taskPartnershipAnalysisService;

  private String message = "Researcher '%s' not found";

  // 获得合作作者列表
  @ApiOperation("获得合作学者列表")
  @GetMapping(value = "/{id}/partners")
  public List<String> getPartners(
    @ApiParam("学者 id") @PathVariable String id
  ) {
    if (!taskPartnershipAnalysisService.containResearcher(id)) {
      throw Problem.valueOf(Status.NOT_FOUND, String.format(message, id));
    }
    return taskPartnershipAnalysisService.getPartners(id);
  }

  // 合作关系分析
  @ApiOperation(
    value = "查看某学者某一时间段的合作关系",
    notes = "需求 5.1：能够识别研究者存在的合作关系，形成社会网络"
  )
  @GetMapping(value = "/{id}/partners-net")
  public IResearcherNet getPartnership(
    @ApiParam("学者 id") @PathVariable String id,
    @ApiParam("开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam("结束年份，形如'2020'") @RequestParam(required = false) String end
  ) {
    if (!taskPartnershipAnalysisService.containResearcher(id)) {
      throw Problem.valueOf(Status.NOT_FOUND, String.format(message, id));
    }
    int startDate = 0;
    int endDate = Integer.MAX_VALUE;
    if (start != null) {
      startDate = checkArgument(start);
    }
    if (end != null) {
      endDate = checkArgument(end);
    }
    if (startDate < 0 || startDate > endDate) {
      throw Problem.valueOf(
        Status.BAD_REQUEST,
        String.format(
          "Argument '%s' illegal, Date error",
          startDate + "," + endDate
        )
      );
    }
    return taskPartnershipAnalysisService.getPartnership(
      id,
      startDate,
      endDate
    );
  }

  // 合作关系预测
  // 返回的是每个学者合作的**可能性**，所以是double
  @ApiOperation(
    value = "预测某学者未来的合作关系",
    notes = "需求 5.2：能够初步预测研究者之间的合作走向"
  )
  @GetMapping(value = "/{id}/potential-partners")
  public Map<String, Double> getPotentialPartners(
    @ApiParam("学者 id") @PathVariable String id
  ) {
    if (!taskPartnershipAnalysisService.containResearcher(id)) {
      throw Problem.valueOf(Status.NOT_FOUND, String.format(message, id));
    }
    return taskPartnershipAnalysisService.getPotentialPartners(id);
  }

  /**
   * 检查参数是否合法
   * @param arg 参数
   * @return 参数值
   */
  private int checkArgument(String arg) {
    int value;
    try {
      value = Integer.parseInt(arg);
      return value;
    } catch (NumberFormatException e) {
      throw Problem.valueOf(
        Status.BAD_REQUEST,
        String.format("Argument '%s' illegal, can not parseInt", arg)
      );
    }
  }

  public ResearcherController(
    TaskPartnershipAnalysisService taskPartnershipAnalysisService
  ) {
    this.taskPartnershipAnalysisService = taskPartnershipAnalysisService;
  }
}
