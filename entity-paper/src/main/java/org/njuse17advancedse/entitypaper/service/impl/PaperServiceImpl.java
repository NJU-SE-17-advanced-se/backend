package org.njuse17advancedse.entitypaper.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.njuse17advancedse.entitypaper.data.AllRepository;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.njuse17advancedse.entitypaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperServiceImpl implements PaperService {
  @Autowired
  private AllRepository repository;

  public IPaper getIPaper(String paperId) {
    try {
      if (repository.existsById(paperId)) return repository.getIPaper(paperId);
      return new IPaper();
    } catch (Exception e) {
      return new IPaper();
    }
  }

  //获取
  public IPaperBasic getPaperBasicInfo(String id) {
    try {
      if (repository.existsById(id)) return repository.getPaperBasic(id);
      return new IPaperBasic();
    } catch (Exception e) {
      return new IPaperBasic();
    }
  }

  //获取论文所属领域
  public List<String> getDomains(String id) {
    if (repository.existsById(id)) return repository.getDomains(id);
    return Collections.singletonList("Not Found");
  }

  public List<String> getCitations(String id) {
    if (repository.existsById(id)) return repository.getCitations(id);
    return Collections.singletonList("Not Found");
  }

  public IResult getPapersByCond(
    String keyword,
    String start,
    String end,
    int page
  ) {
    if (page <= 0) return new IResult();
    keyword = keyword.toLowerCase();
    if (start == null && end == null) {
      return repository.getPaperByCond(keyword, page);
    }
    if (start == null) {
      return repository.getPaperByCond(keyword, end, false, page);
    }
    if (end == null) {
      return repository.getPaperByCond(keyword, start, true, page);
    }
    return repository.getPaperByCond(keyword, start, end, page);
  }
}
