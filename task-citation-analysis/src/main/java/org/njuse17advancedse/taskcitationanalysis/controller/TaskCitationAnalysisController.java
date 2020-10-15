package org.njuse17advancedse.taskcitationanalysis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/citation")
public class TaskCitationAnalysisController {
  @Autowired
  TaskCitationAnalysisService service;

  // 某个学者引用了哪些学者
  // 某个学者被哪些学者引用
  @GetMapping("/researcher/{id}")
  public List<String> getResearcherCitations(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      return service.getResearcherQuotedResearcher(id);
    }
    if (type.equals("quoting")) {
      return service.getResearcherQuotingResearcher(id);
    }
    return null;
  }

  // 某个学者的论文分别引用了哪些论文
  // 某个学者的论文分别被哪些论文引用
  @GetMapping("/researcher/{id}/papers")
  public Map<String, List<String>> getResearcherPapersCitations(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      return service.getQuotedPapersByResearcherId(id);
    }
    if (type.equals("quoting")) {
      return service.getQuotingPapersByResearcherId(id);
    }
    return null;
  }

  // 某个学者的论文分别引用了哪些学者
  // 某个学者的论文分别被哪些学者引用
  @GetMapping("/researcher/{id}/papers/researchers")
  public Map<String, List<String>> getResearcherPapersCitedResearchers(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      return service.getResearcherPaperQuotedResearcher(id);
    }
    if (type.equals("quoting")) {
      return service.getResearcherPaperQuotingResearcher(id);
    }
    return null;
  }

  // 某篇论文引用了哪些论文
  // 某篇论文被哪些论文引用
  @GetMapping("/paper/{id}")
  public List<String> getPaperCitations(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      return service.getQuotedPapersByPaperId(id);
    }
    if (type.equals("quoting")) {
      return service.getQuotingPapersByPaperId(id);
    }
    return null;
  }

  // 某篇论文引用了哪些学者
  // 某篇论文被哪些学者引用
  @GetMapping("/paper/{id}/researchers")
  public List<String> getPaperCitedResearchers(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      return service.getPaperQuotedResearcher(id);
    }
    if (type.equals("quoting")) {
      return service.getPaperQuotingResearcher(id);
    }
    return null;
  }
}
