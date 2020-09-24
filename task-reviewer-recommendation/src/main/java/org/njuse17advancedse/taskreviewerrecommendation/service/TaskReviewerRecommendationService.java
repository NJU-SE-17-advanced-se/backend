package org.njuse17advancedse.taskreviewerrecommendation.service;

import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.springframework.http.ResponseEntity;

/**
 * @author ycj
 * @date 2020/9/23
 */
public interface TaskReviewerRecommendationService {
  /**
   * 获得推荐审稿人
   * @param id 论文标识id
   * @return ResponseEntity
   */
  ResponseEntity<List<IResearcher>> getRecommendReviewer(String id);

  /**
   * 获得不推荐审稿人
   * @param id 论文标识id
   * @return ResponseEntity
   */
  ResponseEntity<List<IResearcher>> getNotRecommendReviewer(String id);
}
