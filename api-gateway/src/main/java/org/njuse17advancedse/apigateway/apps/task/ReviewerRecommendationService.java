package org.njuse17advancedse.apigateway.apps.task;

import java.util.List;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaperUpload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "task-reviewer-recommendation")
public interface ReviewerRecommendationService {
  // 查看某论文推荐的审稿人
  @PostMapping(value = "/paper/recommend")
  List<String> getRecommendReviewer(@RequestBody IPaperUpload iPaperUpload);

  // 查看某论文不推荐的审稿人
  @PostMapping(value = "/paper/not-recommend")
  List<String> getNotRecommendReviewer(@RequestBody IPaperUpload iPaperUpload);
}
