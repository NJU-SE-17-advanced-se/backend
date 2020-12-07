package org.njuse17advancedse.taskcitationanalysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Api(tags = { "学者" })
@RequestMapping("/researchers")
@RestController
public class ResearcherController {
  private final TaskCitationAnalysisService service;

  private static String notFound = "Not Found";

  // 某个学者引用了哪些学者
  // 某个学者被哪些学者引用
  @ApiOperation(
    value = "查看某学者的引用和被引情况",
    notes = "需求 7.2：研究者引用其他研究者及被其他研究者引用情况"
  )
  @GetMapping("/{id}/citations")
  public List<String> getResearcherCitations(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (quoted(type)) {
      List<String> res = service.getResearcherQuotedResearcher(id);
      checkProblem(res);
      return res;
    }
    if (quoting(type)) {
      List<String> res = service.getResearcherQuotingResearcher(id);
      checkProblem(res);
      return res;
    }
    return Collections.emptyList();
  }

  // 某个学者的论文分别引用了哪些论文
  // 某个学者的论文分别被哪些论文引用
  @ApiOperation(
    value = "查看某学者的论文引用和被引情况",
    notes = "需求 7.2：研究者引用其他研究者的论文及论文被其他研究者引用情况"
  )
  @GetMapping("/{id}/citations/papers")
  public Map<String, List<String>> getResearcherPapersCitations(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (quoted(type)) {
      Map<String, List<String>> res = service.getQuotedPapersByResearcherId(id);
      checkProblem(res);
      return res;
    }
    if (quoting(type)) {
      Map<String, List<String>> res = service.getQuotingPapersByResearcherId(
        id
      );
      checkProblem(res);
      return res;
    }
    return new HashMap<>();
  }

  // 某个学者的论文分别引用了哪些学者
  // 某个学者的论文分别被哪些学者引用
  @ApiOperation(
    value = "查看某学者的论文引用其他学者和被其他学者引用情况",
    notes = "即，某个学者的论文分别引用了哪些学者，某个学者的论文分别被哪些学者引用"
  )
  @GetMapping("/{id}/citations/papers/researchers")
  public Map<String, List<String>> getResearcherPapersCitedResearchers(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (quoted(type)) {
      Map<String, List<String>> res = service.getResearcherPaperQuotedResearcher(
        id
      );
      checkProblem(res);
      return res;
    }
    if (quoting(type)) {
      Map<String, List<String>> res = service.getResearcherPaperQuotingResearcher(
        id
      );
      checkProblem(res);
      return res;
    }
    return new HashMap<>();
  }

  public ResearcherController(TaskCitationAnalysisService service) {
    this.service = service;
  }

  private boolean checkProblem(Map<String, List<String>> res) {
    if (res.containsKey(notFound)) {
      throw Problem.valueOf(
        Status.INTERNAL_SERVER_ERROR,
        "Author Data Corrupted"
      );
    }
    return true;
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
