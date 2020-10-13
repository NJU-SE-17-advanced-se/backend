package org.njuse17advancedse.taskcitationanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

  private Map<String, List<String>> quoted = new HashMap<>(); //被它引用的论文

  private Map<String, List<String>> quoting = new HashMap<>(); //引用它的论文

  // private Map<String, String> string2String;

  // private Map<String, String> String2String;

  public TaskCitationAnalysisServiceImpl() {}

  /**
   *  获取学者论文被引情况
   */
  @Override
  public Map<String, List<String>> getQuotingPapersByResearcherId(
    String researcherId
  ) {
    Map<String, List<String>> res = new HashMap<>();
    for (Paper p : fakeService.getResearcherById(researcherId).getPapers()) {
      res.put(p.getId(), quoting.get(p.getId()));
    }
    return res;
  }

  /**
   * 获取学者的论文引用情况
   */
  @Override
  public Map<String, List<String>> getQuotedPapersByResearcherId(
    String researcherId
  ) {
    Map<String, List<String>> res = new HashMap<>();
    for (Paper p : fakeService.getResearcherById(researcherId).getPapers()) {
      res.put(p.getId(), quoted.get(p.getId()));
    }
    return res;
  }

  /**
   * 查看某论文被引情况
   */
  @Override
  public List<String> getQuotingPapersByPaperId(String paperId) {
    return quoting.getOrDefault(paperId, new ArrayList<>());
  }

  /**
   * 查看某论文引用情况
   */
  @Override
  public List<String> getQuotedPapersByPaperId(String paperId) {
    return quoted.getOrDefault(paperId, new ArrayList<>());
  }

  @Override
  public Map<String, Integer> getResearcherQuoteNums(
    String researcherId1,
    String researcherId2
  ) {
    Researcher researcher1 = fakeService.getResearcherById(researcherId1);
    Researcher researcher2 = fakeService.getResearcherById(researcherId2);
    var tmpVar1 = researcher1.getPapers();
    var tmpVar2 = researcher2.getPapers();
    Set<String> researcher2Ids = new HashSet<>();
    Map<String, Integer> res = new HashMap<>();

    for (Paper p : tmpVar2) {
      researcher2Ids.add(p.getId());
    }
    for (Paper p : tmpVar1) {
      String id = p.getId();
      List<String> tmpList = getQuotedPapersByPaperId(id);
      int cnt = 0;
      for (String tId : tmpList) {
        if (researcher2Ids.contains(tId)) {
          cnt++;
        }
      }
      res.put(id, cnt);
    }

    return res;
  }

  @Override
  public void init() {
    List<Paper> papers = fakeService.getAllPapers();
    //String cnt=0;
    for (Paper paper : papers) {
      String id = paper.getId();
      //            string2String.put(id,cnt);
      //            String2String.put(cnt,id);
      List<String> quotedIds = paper.getQuotedIds();
      for (String s : quotedIds) {
        put(quoted, id, s);
        put(quoting, s, id);
      }
    }
    //        var tmp=ScalaObject.getQuotingIDS((scala.collection.immutable.List<Paper>) papers);
    //        var tmpQuoted=(Map<String,List<String>>)tmp._1;
  }

  void put(Map<String, List<String>> map, String id, String s) {
    if (!map.containsKey(id)) {
      List<String> tmp = new ArrayList<>();
      tmp.add(s);
      map.put(id, tmp);
    } else {
      map.get(id).add(s);
    }
  }
  // String string2String(String string) {
  //   return string2String.get(string);
  // }

  // String String2String(String l) {
  //   return String2String.get(l);
  // }
}
