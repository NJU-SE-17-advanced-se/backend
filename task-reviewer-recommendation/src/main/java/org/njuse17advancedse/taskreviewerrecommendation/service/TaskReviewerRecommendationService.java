package org.njuse17advancedse.taskreviewerrecommendation.service;

import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;

/**
 * @author ycj
 * @date 2020/9/23
 */
public interface TaskReviewerRecommendationService {
  /**
   * 判断投稿出版社是否存在
   */
  boolean containPublication(String publicationId);

  /**
   * 获得推荐审稿人
   * @param iPaperUpload 上传论文实体
   * @return ResponseEntity
   */
  List<String> getRecommendReviewer(IPaperUpload iPaperUpload);

  /**
   * 获得不推荐审稿人
   * @param iPaperUpload 上传论文实体
   * @return ResponseEntity
   */
  List<String> getNotRecommendReviewer(IPaperUpload iPaperUpload);
}
