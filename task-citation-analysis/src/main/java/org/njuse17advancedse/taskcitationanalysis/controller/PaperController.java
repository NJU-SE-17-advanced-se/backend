package org.njuse17advancedse.taskcitationanalysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Api(tags = { "论文" })
@RequestMapping("/papers")
@RestController
public class PaperController {
  private final TaskCitationAnalysisService service;

  private static String notFound = "Not Found";

  // 某篇论文引用了哪些论文
  // 某篇论文被哪些论文引用
  @ApiOperation(
    value = "查看某论文和其他论文的引用情况",
    notes = "需求 7.1：论文引用其它论文及被其它论文引用情况"
  )
  @GetMapping("/{id}/citations")
  public List<String> getPaperCitations(
    @ApiParam(value = "论文 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (quoted(type)) {
      List<String> res = service.getQuotedPapersByPaperId(id);
      checkProblem(res);
      return res;
    }
    if (quoting(type)) {
      List<String> res = service.getQuotingPapersByPaperId(id);
      checkProblem(res);
      return res;
    }
    return Collections.emptyList();
  }

  // 某篇论文引用了哪些学者
  // 某篇论文被哪些学者引用
  @ApiOperation(
    value = "查看某论文和学者的引用情况",
    notes = "即，论文引用了哪些学者，哪些学者引用了该论文"
  )
  @GetMapping("/{id}/citations/researchers")
  public List<String> getPaperCitedResearchers(
    @ApiParam(value = "论文 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (quoted(type)) {
      List<String> res = service.getPaperQuotedResearcher(id);
      checkProblem(res);
      return res;
    }
    if (quoting(type)) {
      List<String> res = service.getPaperQuotingResearcher(id);
      checkProblem(res);
      return res;
    }
    return Collections.emptyList();
  }

  public PaperController(TaskCitationAnalysisService service) {
    this.service = service;
  }

  private void checkProblem(List<String> res) {
    if (res.size() == 3 && res.get(0).equals(notFound)) {
      throw Problem.valueOf(
        Status.INTERNAL_SERVER_ERROR,
        "Author Data Corrupted"
      );
    }
  }

  private boolean quoted(String type) {
    return type.equals("quoted");
  }

  private boolean quoting(String type) {
    return type.equals("quoting");
  }
}
