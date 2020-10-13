package org.njuse17advancedse.apigateway.apps.entity;

import java.util.ArrayList;
import org.njuse17advancedse.apigateway.domains.entity.Paper;
import org.springframework.stereotype.Service;

@Service
public class PaperService {

  public Paper getPaperById(String id) {
    return new Paper(
      id,
      "测试论文" + id,
      "测试论文" + id + "的摘要",
      "google.com",
      new ArrayList<>(),
      new ArrayList<>(),
      new ArrayList<>()
    );
  }
}