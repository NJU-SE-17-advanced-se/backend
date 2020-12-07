package org.njuse17advancedse.entitypaper.data;

import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;

public interface AllRepository {
  List<String> getDomains(String pid);

  IPaper getIPaper(String paperId);

  IPaperBasic getPaperBasic(String paperId);

  boolean existsById(String paperId);

  List<String> getCitations(String paperId);
  IResult getPaperByCond(String keyword, int page);
  IResult getPaperByCond(String keyword, String year, boolean type, int page);
  IResult getPaperByCond(
    String keyword,
    String startYear,
    String endYear,
    int page
  );
}
