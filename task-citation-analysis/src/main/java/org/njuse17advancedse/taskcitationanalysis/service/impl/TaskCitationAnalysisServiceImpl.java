package org.njuse17advancedse.taskcitationanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.var;
import org.njuse17advancedse.taskcitationanalysis.dto.IPaper;
import org.njuse17advancedse.taskcitationanalysis.dto.IResearcher;
import org.njuse17advancedse.taskcitationanalysis.service.PaperService;
import org.njuse17advancedse.taskcitationanalysis.service.ResearcherService;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskCitationAnalysisServiceImpl
  implements TaskCitationAnalysisService {
  @Autowired
  private PaperService paperService;

  @Autowired
  private ResearcherService researcherService;

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
    for (String s : researcherService
      .getResearcherById(researcherId)
      .getPapers()) {
      res.put(s, quoting.get(s));
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
    for (String s : researcherService
      .getResearcherById(researcherId)
      .getPapers()) {
      res.put(s, quoted.get(s));
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
  public Map<String, List<String>> getResearcherPaperQuotedResearcher(
    String researcherId
  ) {
    Map<String, List<String>> res = new HashMap<>();
    Set<String> set = new HashSet<>();
    List<String> pList = researcherService
      .getResearcherById(researcherId)
      .getPapers();
    for (String s : pList) {
      set = new HashSet<>();
      List<String> quotedPapers = getQuotedPapersByPaperId(s);
      for (String tp : quotedPapers) {
        for (String r : paperService.getPaper(tp).getResearchers()) {
          set.add(r);
        }
      }
      res.put(s, new ArrayList<>(set));
    }
    return res;
  }

  @Override
  public Map<String, List<String>> getResearcherPaperQuotingResearcher(
    String researcherId
  ) {
    Map<String, List<String>> res = new HashMap<>();
    Set<String> set;
    List<String> pList = researcherService
      .getResearcherById(researcherId)
      .getPapers();
    for (String s : pList) {
      set = new HashSet<>();
      List<String> quotingPapers = getQuotingPapersByPaperId(s);
      for (String tp : quotingPapers) {
        for (String r : paperService.getPaper(tp).getResearchers()) {
          set.add(r);
        }
      }
      res.put(s, new ArrayList<>(set));
    }

    return res;
  }

  @Override
  public List<String> getResearcherQuotedResearcher(String researcherId) {
    Set<String> set = new HashSet<>();
    List<String> pList = researcherService
      .getResearcherById(researcherId)
      .getPapers();
    for (String s : pList) {
      List<String> quotedPapers = getQuotedPapersByPaperId(s);
      for (String tp : quotedPapers) {
        for (String r : paperService.getPaper(tp).getResearchers()) {
          set.add(r);
        }
      }
    }

    return new ArrayList<>(set);
  }

  @Override
  public List<String> getResearcherQuotingResearcher(String researcherId) {
    Set<String> set = new HashSet<>();
    List<String> pList = researcherService
      .getResearcherById(researcherId)
      .getPapers();
    for (String s : pList) {
      List<String> quotingPapers = getQuotingPapersByPaperId(s);
      for (String tp : quotingPapers) {
        for (String r : paperService.getPaper(tp).getResearchers()) {
          set.add(r);
        }
      }
    }
    return new ArrayList<>(set);
  }

  //某论文引用了哪些学者
  @Override
  public List<String> getPaperQuotedResearcher(String paperId) {
    Set<String> set = new HashSet<>();
    IPaper p = paperService.getPaper(paperId);
    List<String> quotedPapers = getQuotedPapersByPaperId(p.getId());
    for (String tp : quotedPapers) {
      for (String r : paperService.getPaper(tp).getResearchers()) {
        set.add(r);
      }
    }
    return new ArrayList<>(set);
  }

  //某论文被哪些学者引用
  public List<String> getPaperQuotingResearcher(String paperId) {
    Set<String> set = new HashSet<>();
    IPaper p = paperService.getPaper(paperId);
    List<String> quotingPapers = getQuotingPapersByPaperId(p.getId());
    for (String tp : quotingPapers) {
      for (String r : paperService.getPaper(tp).getResearchers()) {
        set.add(r);
      }
    }
    return new ArrayList<>(set);
  }

  @Override
  public Map<String, Integer> getResearcherQuoteNums(
    String researcherId1,
    String researcherId2
  ) {
    IResearcher researcher1 = researcherService.getResearcherById(
      researcherId1
    );
    IResearcher researcher2 = researcherService.getResearcherById(
      researcherId2
    );
    var tmpVar1 = researcher1.getPapers();
    var tmpVar2 = researcher2.getPapers();
    Set<String> researcher2Ids = new HashSet<>();
    Map<String, Integer> res = new HashMap<>();

    for (String s : tmpVar2) {
      researcher2Ids.add(s);
    }
    for (String s : tmpVar1) {
      String id = s;
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
    List<String> paperids = paperService.getPapers(null, null, null);
    //String cnt=0;
    quoted = new HashMap<>();
    quoting = new HashMap<>();
    for (String id : paperids) {
      //            string2String.put(id,cnt);
      //            String2String.put(cnt,id);
      IPaper paper = paperService.getPaper(id);
      List<String> quotedIds = paper.getReferences();
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
  //  @Override
  //  public String test(){
  //    return paperService.getPaper("4").getPublication();
  //  }
  // String string2String(String string) {
  //   return string2String.get(string);
  // }

  // String String2String(String l) {
  //   return String2String.get(l);
  // }
}
