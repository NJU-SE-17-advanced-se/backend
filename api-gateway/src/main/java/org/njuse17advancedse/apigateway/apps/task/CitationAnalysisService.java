package org.njuse17advancedse.apigateway.apps.task;

import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "task-citation-analysis")
@RequestMapping("/citation")
public interface CitationAnalysisService {
  // 某个学者引用了哪些学者
  // 某个学者被哪些学者引用
  @GetMapping("/researcher/{id}")
  List<String> getResearcherCitations(
    @PathVariable String id,
    @RequestParam String type
  );

  // 某个学者的论文分别引用了哪些论文
  // 某个学者的论文分别被哪些论文引用
  @GetMapping("/researcher/{id}/papers")
  Map<String, List<String>> getResearcherPapersCitations(
    @PathVariable String id,
    @RequestParam String type
  );

  // 某个学者的论文分别引用了哪些学者
  // 某个学者的论文分别被哪些学者引用
  @GetMapping("/researcher/{id}/papers/researchers")
  Map<String, List<String>> getResearcherPapersCitedResearchers(
    @PathVariable String id,
    @RequestParam String type
  );

  // 某篇论文引用了哪些论文
  // 某篇论文被哪些论文引用
  @GetMapping("/paper/{id}")
  List<String> getPaperCitations(
    @PathVariable String id,
    @RequestParam String type
  );

  // 某篇论文引用了哪些学者
  // 某篇论文被哪些学者引用
  @GetMapping("/paper/{id}/researchers")
  List<String> getPaperCitedResearchers(
    @PathVariable String id,
    @RequestParam String type
  );
}
