package org.njuse17advancedse.taskimpactanalysis.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "task-citation-analysis")
public interface CitationService {
  @GetMapping("/citations/papers/{id}")
  List<String> getPaperCitations(
    @PathVariable String id,
    @RequestParam String type
  );
}
