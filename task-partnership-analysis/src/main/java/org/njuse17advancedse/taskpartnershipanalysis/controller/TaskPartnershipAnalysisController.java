package org.njuse17advancedse.taskpartnershipanalysis.controller;

import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.service.TaskPartnershipAnalysisService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(value = "/partnership")
public class TaskPartnershipAnalysisController {
  private final TaskPartnershipAnalysisService taskPartnershipAnalysisService;

  public TaskPartnershipAnalysisController(
    TaskPartnershipAnalysisService taskPartnershipAnalysisService
  ) {
    this.taskPartnershipAnalysisService = taskPartnershipAnalysisService;
  }

  //获得合作作者列表
  @RequestMapping(value = "/{id}/partners", method = RequestMethod.GET)
  private List<String> getPartners(@PathVariable String id) {
    List<String> partners = taskPartnershipAnalysisService.getPartners(id);
    if (partners == null) {
      throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
    }
    return taskPartnershipAnalysisService.getPartners(id);
  }

  // 合作关系分析
  @RequestMapping(value = "/{id}/partners-net", method = RequestMethod.GET)
  private IResearcherNet getPartnership(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
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
    IResearcherNet iResearcherNet = taskPartnershipAnalysisService.getPartnership(
      id,
      startDate,
      endDate
    );
    if (iResearcherNet == null) {
      throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
    }
    return iResearcherNet;
  }

  // 合作关系预测
  @RequestMapping(
    value = "/{id}/potential-partners",
    method = RequestMethod.GET
  )
  private Map<String, Double> getPotentialPartners(@PathVariable String id) {
    Map<String, Double> map = taskPartnershipAnalysisService.getPotentialPartners(
      id
    );
    if (map == null) {
      throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
    }
    return map;
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
}
