package org.njuse17advancedse.entitypaper.service;

import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;

public interface PaperService {
  IPaper getIPaper(String paperId);

  List<String> getPapers(String researcher, String publication, String date);

  //获取
  IPaperBasic getPaperBasicInfo(String id);

  // 根据指标获取论文简略信息
  // 如果都没填，返回全部论文的简略信息
  List<String> getPapersBasicInfo(
    String researcher,
    String publication,
    String date
  );

  //获取论文所属领域
  List<String> getDomains(String id);
}
