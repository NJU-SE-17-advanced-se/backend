package org.njuse17advancedse.taskcitationanalysis.controller;

import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskcitationanalysis.exception.PaperNotFoundProblem;
import org.njuse17advancedse.taskcitationanalysis.exception.ResearcherNotFoundProblem;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(value = "/citations")
public class TaskCitationAnalysisController {
  @Autowired
  TaskCitationAnalysisService service;

  // 某个学者引用了哪些学者
  // 某个学者被哪些学者引用
  @GetMapping("/researchers/{id}")
  public List<String> getResearcherCitations(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      List<String> res = service.getResearcherQuotedResearcher(id);
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      List<String> res = service.getResearcherQuotingResearcher(id);
      if (checkProblem(res)) return res;
    }
    return null;
  }

  // 某个学者的论文分别引用了哪些论文
  // 某个学者的论文分别被哪些论文引用
  @GetMapping("/researchers/{id}/papers")
  public Map<String, List<String>> getResearcherPapersCitations(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      Map<String, List<String>> res = service.getQuotedPapersByResearcherId(id);
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      Map<String, List<String>> res = service.getQuotingPapersByResearcherId(
        id
      );
      if (checkProblem(res)) return res;
    }
    return null;
  }

  // 某个学者的论文分别引用了哪些学者
  // 某个学者的论文分别被哪些学者引用
  @GetMapping("/researchers/{id}/papers/researchers")
  public Map<String, List<String>> getResearcherPapersCitedResearchers(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      Map<String, List<String>> res = service.getResearcherPaperQuotedResearcher(
        id
      );
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      Map<String, List<String>> res = service.getResearcherPaperQuotingResearcher(
        id
      );
      if (checkProblem(res)) return res;
    }
    return null;
  }

  // 某篇论文引用了哪些论文
  // 某篇论文被哪些论文引用
  @GetMapping("/papers/{id}")
  public List<String> getPaperCitations(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      List<String> res = service.getQuotedPapersByPaperId(id);
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      List<String> res = service.getQuotingPapersByPaperId(id);
      if (checkProblem(res)) return res;
    }
    return null;
  }

  // 某篇论文引用了哪些学者
  // 某篇论文被哪些学者引用
  @GetMapping("/papers/{id}/researchers")
  public List<String> getPaperCitedResearchers(
    @PathVariable String id,
    @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      List<String> res = service.getPaperQuotedResearcher(id);
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      List<String> res = service.getPaperQuotingResearcher(id);
      if (checkProblem(res)) return res;
    }
    return null;
  }

  //  @GetMapping("/test")
  //  public String test(){
  //    return service.test();
  //  }
  private boolean checkProblem(Map<String, List<String>> res) {
    if (res.containsKey("Not Found")) {
      List<String> parms = res.get("Not Found");
      if (
        parms.get(0).equals("Researcher")
      ) throw new ResearcherNotFoundProblem(parms.get(1));
      if (parms.get(0).equals("Paper")) throw new PaperNotFoundProblem(
        parms.get(1)
      );
    }
    return true;
  }

  private boolean checkProblem(List<String> res) {
    if (res.size() != 3) return true;
    if (res.get(0).equals("Not Found")) {
      if (res.get(1).equals("Researcher")) throw new ResearcherNotFoundProblem(
        res.get(2)
      );
      if (res.get(1).equals("Paper")) throw new PaperNotFoundProblem(
        res.get(2)
      );
    }
    return true;
  }
}
