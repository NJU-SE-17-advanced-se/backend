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

  //  private Map<String, List<String>> quoted = new HashMap<>(); //被它引用的论文
  //
  //  private Map<String, List<String>> quoting = new HashMap<>(); //引用它的论文

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
    try {
      Map<String, List<String>> res = new HashMap<>();
      IResearcher r = researcherService.getResearcherById(researcherId);
      for (String s : r.getPapers()) {
        List<String> quotingPapers = getQuotingPapersByPaperId(s);
        if (
          quotingPapers.size() > 0 && quotingPapers.get(0).equals("Not Found")
        ) continue;
        res.put(s, quotingPapers);
      }
      return res;
    } catch (Exception e) {
      return getProblemMap(e.getMessage());
    }
  }

  /**
   * 获取学者的论文引用情况
   */
  @Override
  public Map<String, List<String>> getQuotedPapersByResearcherId(
    String researcherId
  ) {
    try {
      Map<String, List<String>> res = new HashMap<>();
      IResearcher r = researcherService.getResearcherById(researcherId);
      for (String s : r.getPapers()) {
        List<String> quotedPapers = getQuotedPapersByPaperId(s);
        if (
          quotedPapers.size() > 0 && quotedPapers.get(0).equals("Not Found")
        ) continue;
        res.put(s, quotedPapers);
      }
      return res;
    } catch (Exception e) {
      return getProblemMap(e.getMessage());
    }
  }

  /**
   * 查看某论文被引情况
   */
  @Override
  public List<String> getQuotingPapersByPaperId(String paperId) {
    try {
      IPaper p = paperService.getPaper(paperId);
      return paperService.getCitations(paperId);
    } catch (Exception e) {
      return getProblemList(e.getMessage());
    }
  }

  /**
   * 查看某论文引用情况
   */
  @Override
  public List<String> getQuotedPapersByPaperId(String paperId) {
    try {
      IPaper p = paperService.getPaper(paperId);
      return p.getReferences();
    } catch (Exception e) {
      return getProblemList(e.getMessage());
    }
  }

  @Override
  public Map<String, List<String>> getResearcherPaperQuotedResearcher(
    String researcherId
  ) {
    Map<String, List<String>> res = new HashMap<>();
    Set<String> set;
    try {
      IResearcher r = researcherService.getResearcherById(researcherId);
      List<String> pList = r.getPapers();
      for (String s : pList) {
        set = new HashSet<>();
        List<String> quotedPapers = getQuotedPapersByPaperId(s);
        if (
          quotedPapers.size() > 0 && quotedPapers.get(0).equals("Not Found")
        ) continue;
        for (String tp : quotedPapers) {
          try {
            IPaper p = paperService.getPaper(tp);
            set.addAll(p.getResearchers());
          } catch (Exception e) {}
        }
        res.put(s, new ArrayList<>(set));
      }
      return res;
    } catch (Exception e) {
      return getProblemMap(e.getMessage());
    }
  }

  @Override
  public Map<String, List<String>> getResearcherPaperQuotingResearcher(
    String researcherId
  ) {
    Map<String, List<String>> res = new HashMap<>();
    Set<String> set;
    try {
      IResearcher r = researcherService.getResearcherById(researcherId);
      List<String> pList = r.getPapers();
      for (String s : pList) {
        set = new HashSet<>();
        List<String> quotingPapers = getQuotingPapersByPaperId(s);
        if (
          quotingPapers.size() > 0 && quotingPapers.get(0).equals("Not Found")
        ) continue;
        for (String tp : quotingPapers) {
          IPaper p = paperService.getPaper(tp);
          set.addAll(p.getResearchers());
        }
        res.put(s, new ArrayList<>(set));
      }
      return res;
    } catch (Exception e) {
      return getProblemMap(e.getMessage());
    }
  }

  @Override
  public List<String> getResearcherQuotedResearcher(String researcherId) {
    try {
      Set<String> set = new HashSet<>();
      IResearcher r = researcherService.getResearcherById(researcherId);
      List<String> pList = r.getPapers();
      for (String s : pList) {
        List<String> quotedPapers = getQuotedPapersByPaperId(s);
        if (
          quotedPapers.size() > 0 && quotedPapers.get(0).equals("Not Found")
        ) return quotedPapers;
        for (String tp : quotedPapers) {
          try {
            IPaper p = paperService.getPaper(tp);
            set.addAll(p.getResearchers());
          } catch (Exception e) {}
        }
      }

      return new ArrayList<>(set);
    } catch (Exception e) {
      return getProblemList(e.getMessage());
    }
  }

  @Override
  public List<String> getResearcherQuotingResearcher(String researcherId) {
    try {
      Set<String> set = new HashSet<>();
      IResearcher r = researcherService.getResearcherById(researcherId);
      List<String> pList = r.getPapers();
      for (String s : pList) {
        List<String> quotingPapers = getQuotingPapersByPaperId(s);
        if (
          quotingPapers.size() > 0 && quotingPapers.get(0).equals("Not Found")
        ) return quotingPapers;
        for (String tp : quotingPapers) {
          IPaper p = paperService.getPaper(tp);
          set.addAll(p.getResearchers());
        }
      }

      return new ArrayList<>(set);
    } catch (Exception e) {
      return getProblemList(e.getMessage());
    }
  }

  //某论文引用了哪些学者
  @Override
  public List<String> getPaperQuotedResearcher(String paperId) {
    Set<String> set = new HashSet<>();
    try {
      IPaper p = paperService.getPaper(paperId);
      List<String> quotedPapers = p.getReferences();
      for (String tp : quotedPapers) {
        try {
          IPaper tip = paperService.getPaper(tp);
          set.addAll(tip.getResearchers());
        } catch (Exception e) {}
      }
      return new ArrayList<>(set);
    } catch (Exception e) {
      return getProblemList(e.getMessage());
    }
  }

  //某论文被哪些学者引用
  public List<String> getPaperQuotingResearcher(String paperId) {
    try {
      Set<String> set = new HashSet<>();
      List<String> quotingPapers = getQuotingPapersByPaperId(paperId);
      for (String tp : quotingPapers) {
        IPaper tip = paperService.getPaper(tp);
        set.addAll(tip.getResearchers());
      }
      return new ArrayList<>(set);
    } catch (Exception e) {
      return getProblemList(e.getMessage());
    }
  }

  //
  //  @Override
  //  public Map<String, Integer> getResearcherQuoteNums(
  //    String researcherId1,
  //    String researcherId2
  //  ) {
  //    IResearcher researcher1 = researcherService.getResearcherById(
  //      researcherId1
  //    );
  //    IResearcher researcher2 = researcherService.getResearcherById(
  //      researcherId2
  //    );
  //    var tmpVar1 = researcher1.getPapers();
  //    var tmpVar2 = researcher2.getPapers();
  //    Map<String, Integer> res = new HashMap<>();
  //
  //    Set<String> researcher2Ids = new HashSet<>(tmpVar2);
  //    for (String s : tmpVar1) {
  //      String id = s;
  //      List<String> tmpList = getQuotedPapersByPaperId(id);
  //      int cnt = 0;
  //      for (String tId : tmpList) {
  //        if (researcher2Ids.contains(tId)) {
  //          cnt++;
  //        }
  //      }
  //      res.put(id, cnt);
  //    }
  //
  //    return res;
  //  }

  //
  //  @Override
  //  public void init() {
  //    List<String> paperids = paperService.getPapers(null, null, null);
  //    //String cnt=0;
  //    quoted = new HashMap<>();
  //    quoting = new HashMap<>();
  //    int count = 0;
  //    for (String id : paperids) {
  //      //            string2String.put(id,cnt);
  //      //            String2String.put(cnt,id);
  //      System.out.println(count++);
  //      IPaper paper = paperService.getPaper(id);
  //      if (!isEmptyPaper(paper)) {
  //        List<String> quotedIds = paper.getReferences();
  //        for (String s : quotedIds) {
  //          put(quoted, id, s);
  //          put(quoting, s, id);
  //        }
  //      } else {
  //        System.out.println(id);
  //      }
  //    }
  //    inited = true;
  //    //        var tmp=ScalaObject.getQuotingIDS((scala.collection.immutable.List<Paper>) papers);
  //    //        var tmpQuoted=(Map<String,List<String>>)tmp._1;
  //  }
  //
  //  void put(Map<String, List<String>> map, String id, String s) {
  //    if (!map.containsKey(id)) {
  //      List<String> tmp = new ArrayList<>();
  //      tmp.add(s);
  //      map.put(id, tmp);
  //    } else {
  //      map.get(id).add(s);
  //    }
  //  }

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
  //  private boolean isEmptyResearcher(IResearcher r) {
  //    return r.getId() == null;
  //  }
  //
  //  private boolean isEmptyPaper(IPaper p) {
  //    return p.getId() == null;
  //  }

  private Map<String, List<String>> getProblemMap(String message) {
    Map<String, List<String>> res = new HashMap<>();
    List<String> parms = new ArrayList<>();
    //    if (message.contains("Researcher")) parms.add("Researcher");
    //    else if (message.contains("Paper")) parms.add("Paper");
    //    else
    parms.add("Unknown");
    res.put("Not Found", parms);
    return res;
  }

  private List<String> getProblemList(String message) {
    List<String> res = new ArrayList<>();
    res.add("Not Found");
    //    if (message.contains("Researcher")) res.add("Researcher");
    //    else if (message.contains("Paper")) res.add("Paper");
    //    else
    res.add("Unknown");
    res.add("Unknown Id");
    return res;
  }
}
