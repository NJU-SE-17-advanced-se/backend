package org.njuse17advancedse.taskreviewerrecommendation.service;

import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Paper;
import org.springframework.http.ResponseEntity;

/**
 * @author ycj
 * @date 2020/9/23
 */
public interface TaskReviewerRecommendationService {
  /**
   * 获得推荐审稿人
   * @param paper 论文实体
   * @return ResponseEntity
   */
  ResponseEntity<List<IResearcher>> getRecommendReviewer(Paper paper);

  /**
   * 获得不推荐审稿人
   * @param paper 论文实体
   * @return ResponseEntity
   */
  ResponseEntity<List<IResearcher>> getNotRecommendReviewer(Paper paper);
}
