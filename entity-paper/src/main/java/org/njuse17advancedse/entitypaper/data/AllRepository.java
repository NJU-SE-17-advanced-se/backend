package org.njuse17advancedse.entitypaper.data;

import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.springframework.cache.annotation.Cacheable;

public interface AllRepository {
  @Cacheable("getDomains")
  List<String> getDomains(String pid);

  @Cacheable("getIPaper")
  IPaper getIPaper(String paperId);

  @Cacheable("getPaperBasic")
  IPaperBasic getPaperBasic(String paperId);

  @Cacheable("existsById")
  boolean existsById(String paperId);

  @Cacheable("getCitations")
  List<String> getCitations(String paperId);

  // 暂时不缓存搜索，确保一个比较准确的结果
  IResult getPaperByCond(String keyword, int page);
  IResult getPaperByCond(String keyword, String year, boolean type, int page);
  IResult getPaperByCond(
    String keyword,
    String startYear,
    String endYear,
    int page
  );
}
