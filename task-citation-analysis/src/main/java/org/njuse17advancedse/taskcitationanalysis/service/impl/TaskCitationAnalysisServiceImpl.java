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

  private boolean inited = false;

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
    if (!inited) {
      init();
      inited = true;
    }
    Map<String, List<String>> res = new HashMap<>();
    IResearcher r = researcherService.getResearcherById(researcherId);
    if (isEmptyResearcher(r)) return res;
    for (String s : r.getPapers()) {
      res.put(s, getQuotingPapersByPaperId(s));
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
    if (!inited) {
      init();
      inited = true;
    }
    Map<String, List<String>> res = new HashMap<>();
    IResearcher r = researcherService.getResearcherById(researcherId);
    if (isEmptyResearcher(r)) return res;
    for (String s : r.getPapers()) {
      res.put(s, getQuotedPapersByPaperId(s));
    }
    return res;
  }

  /**
   * 查看某论文被引情况
   */
  @Override
  public List<String> getQuotingPapersByPaperId(String paperId) {
    if (!inited) {
      init();
      inited = true;
    }
    return quoting.getOrDefault(paperId, new ArrayList<>());
  }

  /**
   * 查看某论文引用情况
   */
  @Override
  public List<String> getQuotedPapersByPaperId(String paperId) {
    if (!inited) {
      init();
      inited = true;
    }
    return quoted.getOrDefault(paperId, new ArrayList<>());
  }

  @Override
  public Map<String, List<String>> getResearcherPaperQuotedResearcher(
    String researcherId
  ) {
    if (!inited) {
      init();
      inited = true;
    }
    Map<String, List<String>> res = new HashMap<>();
    Set<String> set;
    IResearcher r = researcherService.getResearcherById(researcherId);
    if (isEmptyResearcher(r)) return res;
    List<String> pList = r.getPapers();
    for (String s : pList) {
      set = new HashSet<>();
      List<String> quotedPapers = getQuotedPapersByPaperId(s);
      for (String tp : quotedPapers) {
        IPaper p = paperService.getPaper(tp);
        if (isEmptyPaper(p)) {
          continue;
        }
        set.addAll(p.getResearchers());
      }
      res.put(s, new ArrayList<>(set));
    }
    return res;
  }

  @Override
  public Map<String, List<String>> getResearcherPaperQuotingResearcher(
    String researcherId
  ) {
    if (!inited) {
      init();
      inited = true;
    }
    Map<String, List<String>> res = new HashMap<>();
    Set<String> set;
    IResearcher r = researcherService.getResearcherById(researcherId);
    if (isEmptyResearcher(r)) return res;
    List<String> pList = r.getPapers();
    for (String s : pList) {
      set = new HashSet<>();
      List<String> quotingPapers = getQuotingPapersByPaperId(s);
      for (String tp : quotingPapers) {
        IPaper p = paperService.getPaper(tp);
        if (isEmptyPaper(p)) {
          continue;
        }
        set.addAll(p.getResearchers());
      }
      res.put(s, new ArrayList<>(set));
    }
    return res;
  }

  @Override
  public List<String> getResearcherQuotedResearcher(String researcherId) {
    if (!inited) {
      init();
      inited = true;
    }
    Set<String> set = new HashSet<>();
    IResearcher r = researcherService.getResearcherById(researcherId);
    if (isEmptyResearcher(r)) return new ArrayList<>();
    List<String> pList = r.getPapers();
    for (String s : pList) {
      List<String> quotedPapers = getQuotedPapersByPaperId(s);
      for (String tp : quotedPapers) {
        IPaper p = paperService.getPaper(tp);
        if (isEmptyPaper(p)) {
          continue;
        }
        set.addAll(p.getResearchers());
      }
    }

    return new ArrayList<>(set);
  }

  @Override
  public List<String> getResearcherQuotingResearcher(String researcherId) {
    if (!inited) {
      init();
      inited = true;
    }
    Set<String> set = new HashSet<>();
    IResearcher r = researcherService.getResearcherById(researcherId);
    if (isEmptyResearcher(r)) return new ArrayList<>();
    List<String> pList = r.getPapers();
    for (String s : pList) {
      List<String> quotingPapers = getQuotingPapersByPaperId(s);
      for (String tp : quotingPapers) {
        IPaper p = paperService.getPaper(tp);
        if (isEmptyPaper(p)) {
          continue;
        }
        set.addAll(p.getResearchers());
      }
    }

    return new ArrayList<>(set);
  }

  //某论文引用了哪些学者
  @Override
  public List<String> getPaperQuotedResearcher(String paperId) {
    if (!inited) {
      init();
      inited = true;
    }
    Set<String> set = new HashSet<>();
    IPaper p = paperService.getPaper(paperId);
    if (isEmptyPaper(p)) return new ArrayList<>();
    List<String> quotedPapers = getQuotedPapersByPaperId(p.getId());
    for (String tp : quotedPapers) {
      IPaper tip = paperService.getPaper(tp);
      if (isEmptyPaper(tip)) continue;
      set.addAll(tip.getResearchers());
    }
    return new ArrayList<>(set);
  }

  //某论文被哪些学者引用
  public List<String> getPaperQuotingResearcher(String paperId) {
    if (!inited) {
      init();
      inited = true;
    }
    Set<String> set = new HashSet<>();
    IPaper p = paperService.getPaper(paperId);
    if (isEmptyPaper(p)) return new ArrayList<>();
    List<String> quotingPapers = getQuotingPapersByPaperId(p.getId());
    for (String tp : quotingPapers) {
      IPaper tip = paperService.getPaper(tp);
      if (isEmptyPaper(tip)) continue;
      set.addAll(tip.getResearchers());
    }
    return new ArrayList<>(set);
  }

  @Override
  public Map<String, Integer> getResearcherQuoteNums(
    String researcherId1,
    String researcherId2
  ) {
    if (!inited) {
      init();
      inited = true;
    }
    IResearcher researcher1 = researcherService.getResearcherById(
      researcherId1
    );
    IResearcher researcher2 = researcherService.getResearcherById(
      researcherId2
    );
    var tmpVar1 = researcher1.getPapers();
    var tmpVar2 = researcher2.getPapers();
    Map<String, Integer> res = new HashMap<>();

    Set<String> researcher2Ids = new HashSet<>(tmpVar2);
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
      if (paper != null) {
        List<String> quotedIds = paper.getReferences();
        for (String s : quotedIds) {
          put(quoted, id, s);
          put(quoting, s, id);
        }
      }
    }
    inited = true;
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

  //    @Override
  //    public String test(){
  //      String p=paperService.getPaper("011ea2744e0166871e6da341da6f7f08").getPublication();
  //      String s=researcherService.getResearcherById("IEEE_37087235342").getName();
  //
  //      List<String> ps=paperService.getPapers(null,null,null);
  //      return p+" "+s+ps.size();
  //    }
  // String string2String(String string) {
  //   return string2String.get(string);
  // }

  // String String2String(String l) {
  //   return String2String.get(l);
  // }
  private boolean isEmptyResearcher(IResearcher r) {
    return r.getId() == null;
  }

  private boolean isEmptyPaper(IPaper p) {
    return p.getId() == null;
  }
}
