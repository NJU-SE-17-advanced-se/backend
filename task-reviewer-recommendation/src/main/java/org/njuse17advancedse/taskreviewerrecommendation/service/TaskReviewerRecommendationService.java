package org.njuse17advancedse.taskreviewerrecommendation.service;

import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.springframework.http.ResponseEntity;

/**
 * @author ycj
 * @date 2020/9/23
 */
public interface TaskReviewerRecommendationService {
  /**
   * 获得推荐审稿人
   * @param iPaperUpload 上传论文实体
   * @return ResponseEntity
   */
  ResponseEntity<List<IResearcher>> getRecommendReviewer(
    IPaperUpload iPaperUpload
  );

  /**
   * 获得不推荐审稿人
   * @param iPaperUpload 上传论文实体
   * @return ResponseEntity
   */
  ResponseEntity<List<IResearcher>> getNotRecommendReviewer(
    IPaperUpload iPaperUpload
  );
}
