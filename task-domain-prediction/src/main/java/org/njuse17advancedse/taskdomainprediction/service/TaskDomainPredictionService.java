package org.njuse17advancedse.taskdomainprediction.service;

import java.util.List;

public interface TaskDomainPredictionService {
  /**
   * 预测学者未来可能的研究方向
   * @param rid 学者id
   * @return 研究领域id列表
   */
  List<String> getFutureDomains(String rid);

  /**
   * 判断学者是否存在
   * @param id 学者id
   * @return true or false
   */
  boolean containResearcher(String id);
}
