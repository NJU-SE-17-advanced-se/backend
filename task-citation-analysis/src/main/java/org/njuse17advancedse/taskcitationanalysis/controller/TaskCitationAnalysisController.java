package org.njuse17advancedse.taskcitationanalysis.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(value = "/citations")
public class TaskCitationAnalysisController {
  @Autowired
  TaskCitationAnalysisService service;

  private static String notFound = "Not Found";

  // 某个学者引用了哪些学者
  // 某个学者被哪些学者引用
  @GetMapping("/researchers/{id}")
  public List<String> getResearcherCitations(
    @PathVariable String id,
    @RequestParam String type
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
  @GetMapping("/researchers/{id}/papers")
  public Map<String, List<String>> getResearcherPapersCitations(
    @PathVariable String id,
    @RequestParam String type
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
  @GetMapping("/researchers/{id}/papers/researchers")
  public Map<String, List<String>> getResearcherPapersCitedResearchers(
    @PathVariable String id,
    @RequestParam String type
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

  // 某篇论文引用了哪些论文
  // 某篇论文被哪些论文引用
  @GetMapping("/papers/{id}")
  public List<String> getPaperCitations(
    @PathVariable String id,
    @RequestParam String type
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
  @GetMapping("/papers/{id}/researchers")
  public List<String> getPaperCitedResearchers(
    @PathVariable String id,
    @RequestParam String type
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
