package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import java.util.*;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IImpact;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Domain;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Paper;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Researcher;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 审稿人推荐服务实现
 * @author ycj
 * @date 2020/09/24
 */
@Service
public class TaskReviewerRecommendationServiceImp
  implements TaskReviewerRecommendationService {
  private static String paperServiceAddress = ""; // 论文实体服务地址
  private static String researcherServiceAddress = ""; // 作者实体服务地址
  private static String taskImpactAnalysisServiceAddress = ""; // 影响力业务服务地址

  private final RestTemplate restTemplate;

  public TaskReviewerRecommendationServiceImp(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ResponseEntity<List<IResearcher>> getRecommendReviewer(String id) {
    List<IResearcher> iResearchers = new ArrayList<>();

    //通过论文实体服务获得解析完成的论文实体
    try {
      Paper paper = restTemplate.getForObject(
        paperServiceAddress + "/paper/getNewPaper/{id}",
        Paper.class,
        id
      );
      if (paper != null) {
        /* 第一步，获得引用文献的作者 */
        List<Paper> references = paper.getReferences(); //默认获得的引用文献中所有的作者实体均已处理完成
        List<Researcher> researchers = new ArrayList<>(); // 作者id列表
        addRid(researchers, references);

        /* 第二步，获得在投稿刊物发表过相似领域文章（不在引文中）的作者 */
        List<Domain> paperDomains = paper.getDomains();
        String paperJournal = paper.getJournal();
        try {
          List<Paper> sameJournalPapers = (List<Paper>) restTemplate.getForObject(
            paperServiceAddress + "/paper/getPapersByJournal/{journal}",
            List.class,
            paperJournal
          );
          addRid(researchers, sameJournalPapers);
        } catch (Exception e) {
          e.printStackTrace();
        }

        /* 第三步， 获得论文发表期间相同领域论文(不在引文和本刊物中)的作者列表*/
        try {
          List<Paper> similarDomainPapers = (List<Paper>) restTemplate.postForObject(
            paperServiceAddress + "/paper/getPapersByDomain",
            paperDomains,
            List.class
          );
          addRid(researchers, similarDomainPapers);
        } catch (Exception e) {
          e.printStackTrace();
        }

        /* 第四步，获得这些作者的研究领域并与论文研究领域进行比照，删去无重叠领域的作者 */
        researchers = deduplicateAndSort(researchers); //去重并排序
        try {
          List<List<Domain>> reviewersDomains = (List<List<Domain>>) restTemplate.postForObject(
            researcherServiceAddress + "/researcher/getDomains",
            researchers,
            List.class
          );
          if (reviewersDomains != null) {
            removeNoSameDomains(researchers, paperDomains, reviewersDomains);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }

        /* 第五步，将剩余作者按照影响力进行排序，取前5个作为推荐审稿人候选 */
        try {
          List<IImpact> impacts = (List<IImpact>) restTemplate.postForObject(
            taskImpactAnalysisServiceAddress + "/impact/researchers",
            researchers,
            List.class
          );
          if (impacts != null) {
            HashMap<String, IImpact> map = new HashMap<>();
            for (int i = 0; i < researchers.size(); i++) {
              map.put(researchers.get(i).getId(), impacts.get(i));
              researchers.sort(
                (o1, o2) ->
                  (int) (
                    map.get(o2.getId()).getImpact() -
                    map.get(o1.getId()).getImpact()
                  )
              );
            }
            if (researchers.size() > 5) {
              researchers.subList(0, 5);
            }
            for (Researcher researcher : researchers) {
              iResearchers.add(new IResearcher(researcher));
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(iResearchers, HttpStatus.OK);
  }

  /**
   * 添加作者id
   * @param researchers 作者id列表
   * @param paperList 论文列表
   */
  private void addRid(List<Researcher> researchers, List<Paper> paperList) {
    if (paperList != null) {
      for (Paper p : paperList) {
        researchers.addAll(p.getResearchers());
      }
    }
  }

  /**
   * 作者去重按照出现次数降序排列
   * @param researchers 作者id列表
   */
  private List<Researcher> deduplicateAndSort(List<Researcher> researchers) {
    Map<String, Integer> map = new HashMap<>();
    List<String> rids = new ArrayList<>();
    for (Researcher researcher : researchers) {
      rids.add(researcher.getId());
    }
    for (String rid : rids) {
      Integer count = map.get(rid);
      map.put(rid, (count == null) ? 1 : count + 1);
    }

    HashSet<String> set = new HashSet<>(rids);
    rids.clear();
    rids.addAll(set);
    rids.sort((o1, o2) -> map.get(o2) - map.get(o1)); //去重完成并按照出现次数降序排列

    List<Researcher> researcherList = new ArrayList<>();
    for (String rid : rids) {
      for (Researcher researcher : researchers) {
        if (researcher.getId().equals(rid)) {
          researcherList.add(researcher);
          break;
        }
      }
    }

    return researcherList;
  }

  /**
   * 去除无重叠领域的作者
   * @param researchers 作者id列表
   * @param reviewersDomains 作者研究领域列表
   */
  private void removeNoSameDomains(
    List<Researcher> researchers,
    List<Domain> paperDomains,
    List<List<Domain>> reviewersDomains
  ) {
    HashMap<String, List<Domain>> map = new HashMap<>();
    for (int i = 0; i < researchers.size(); i++) {
      map.put(researchers.get(i).getId(), reviewersDomains.get(i));
    }
    for (String rid : map.keySet()) {
      List<Domain> domains = map.get(rid);
      boolean hasNoSameDomain = Collections.disjoint(paperDomains, domains);
      if (hasNoSameDomain) {
        researchers.removeIf(s -> s.getId().equals(rid));
      }
    }
  }

  @Override
  public ResponseEntity<List<IResearcher>> getNotRecommendReviewer(String id) {
    return null;
  }
}
