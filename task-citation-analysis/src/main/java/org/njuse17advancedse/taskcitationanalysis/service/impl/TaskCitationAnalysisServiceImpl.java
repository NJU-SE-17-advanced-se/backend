package org.njuse17advancedse.taskcitationanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.var;
import org.njuse17advancedse.taskcitationanalysis.entity.*;
import org.njuse17advancedse.taskcitationanalysis.service.FakeService;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskCitationAnalysisServiceImpl
  implements TaskCitationAnalysisService {
  @Autowired
  FakeService fakeService;

  private Map<Long, List<Long>> quoted = new HashMap<>(); //被它引用的论文

  private Map<Long, List<Long>> quoting = new HashMap<>(); //引用它的论文

  private Map<String, Long> string2Long;

  private Map<Long, String> long2String;

  public TaskCitationAnalysisServiceImpl() {}

  /**
   *  获取学者论文被引情况
   */
  @Override
  public Map<Long, List<Long>> getQuotingPapersByResearcherId(
    Long researcherId
  ) {
    Map<Long, List<Long>> res = new HashMap<>();
    for (Paper p : fakeService.getResearcherById(researcherId).getPapers()) {
      res.put(p.getLongId(), quoting.get(p.getLongId()));
    }
    return res;
  }

  /**
   * 获取学者的论文引用情况
   */
  @Override
  public Map<Long, List<Long>> getQuotedPapersByResearcherId(
    Long researcherId
  ) {
    Map<Long, List<Long>> res = new HashMap<>();
    for (Paper p : fakeService.getResearcherById(researcherId).getPapers()) {
      res.put(p.getLongId(), quoted.get(p.getLongId()));
    }
    return res;
  }

  /**
   * 查看某论文被引情况
   */
  @Override
  public List<Long> getQuotingPapersByPaperId(Long paperId) {
    return quoting.getOrDefault(paperId, new ArrayList<>());
  }

  /**
   * 查看某论文引用情况
   */
  @Override
  public List<Long> getQuotedPapersByPaperId(Long paperId) {
    return quoted.getOrDefault(paperId, new ArrayList<>());
  }

  @Override
  public void init() {
    List<Paper> papers = fakeService.getAllPapers();
    //long cnt=0;
    for (Paper paper : papers) {
      Long id = paper.getLongId();
      //            string2Long.put(id,cnt);
      //            long2String.put(cnt,id);
      List<Long> quotedIds = paper.getQuotedIds();
      for (Long s : quotedIds) {
        put(quoted, id, s);
        put(quoting, s, id);
      }
    }
    //        var tmp=ScalaObject.getQuotingIDS((scala.collection.immutable.List<Paper>) papers);
    //        var tmpQuoted=(Map<Long,List<Long>>)tmp._1;
  }

  void put(Map<Long, List<Long>> map, Long id, Long s) {
    if (!map.containsKey(id)) {
      List<Long> tmp = new ArrayList<>();
      tmp.add(s);
      map.put(id, tmp);
    } else {
      map.get(id).add(s);
    }
  }

  Long string2long(String string) {
    return string2Long.get(string);
  }

  String long2String(Long l) {
    return long2String.get(l);
  }
}
