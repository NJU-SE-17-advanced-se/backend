package org.njuse17advancedse.entitypaper.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitypaper.data.AllRepository;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperServiceImpl implements PaperService {
  @Autowired
  private AllRepository repository;

  public IPaper getIPaper(String paperId) {
    try {
      return repository.getIPaper(paperId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<String> getPapers(
    String researcher,
    String publication,
    String date
  ) {
    String sql = ";";
    List<String> res = new ArrayList<>();
    //啥都不限制
    if (researcher == null && publication == null && date == null) {
      return repository.getAllPapers();
    }
    //只限制researcher
    if (researcher != null && publication == null && date == null) {
      return repository.getPapersByResearcher(researcher);
    }
    //只限制publication
    if (researcher == null && publication != null && date == null) {
      return repository.getPapersByPublication(publication);
    }
    //只限制date
    if (researcher == null && publication == null && date != null) {
      return repository.getPapersByDate(date);
    }
    //限制researcher和publication
    if (researcher != null && publication != null && date == null) {
      return repository.getPapersByResearcherAndPublication(
        researcher,
        publication
      );
    }
    //限制researcher和date
    if (researcher != null && publication == null && date != null) {
      return repository.getPapersByResearcherAndDate(researcher, date);
    }
    //限制publication和date
    if (researcher == null && publication != null && date != null) {
      return repository.getPapersByPublicationAndDate(publication, date);
    }
    //全都限制
    if (researcher != null && publication != null && date != null) {
      return repository.getPapersByResearcherAndPublicationAndDate(
        researcher,
        publication,
        date
      );
    }
    return new ArrayList<>();
  }

  //获取
  public IPaperBasic getPaperBasicInfo(String id) {
    try {
      return repository.getPaperBasic(id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  //获取论文所属领域
  public List<String> getDomains(String id) {
    return repository.getDomains(id);
  }
}