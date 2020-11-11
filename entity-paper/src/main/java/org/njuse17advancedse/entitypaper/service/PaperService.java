package org.njuse17advancedse.entitypaper.service;

import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;

public interface PaperService {
  IPaper getIPaper(String paperId);

  List<String> getPapers(String researcher, String publication, String date);

  //获取
  IPaperBasic getPaperBasicInfo(String id);

  //获取论文所属领域
  List<String> getDomains(String id);

  //获取所有引用了一篇论文的论文
  List<String> getCitations(String id);
}
