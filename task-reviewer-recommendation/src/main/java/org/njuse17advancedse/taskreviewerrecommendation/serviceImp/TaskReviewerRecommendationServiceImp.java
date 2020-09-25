package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import java.util.*;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IImpact;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Domain;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Paper;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Researcher;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
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
        paperServiceAddress + "/paper/getNewPaper/" + id,
        Paper.class
      );
      if (paper != null) {
        /* 第一步，获得引用文献的作者 */
        List<Paper> references = paper.getReferences(); //默认获得的引用文献中所有的作者实体均已处理完成
        List<Researcher> researchers = new ArrayList<>(); // 作者id列表

        addRid(researchers, references);

        /* 第二步，获得最近在投稿刊物发表过文章（不在引文中）的作者 */
        String paperJournal = paper.getJournal();
        //getReviewerFromSameJournal(paperJournal,paper.getDate(),researchers);

        /* 第三步， 获得论文发表期间相同领域论文(不在引文和本刊物中)的作者列表*/
        List<Domain> paperDomains = paper.getDomains();
        //getReviewerFormSimilarDomain(paperDomains,researchers);

        /* 第四步，获得这些作者的研究领域并与论文研究领域进行比照，删去无重叠领域的作者 */
        researchers = deduplicateAndSort(researchers); //去重并排序

        removeNoSameDomains(researchers, paperDomains);

        /* 第五步，将剩余作者按照影响力进行排序，取前5个作为推荐审稿人候选 */
        iResearchers = sortResearchersByImpact(researchers);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(iResearchers, HttpStatus.OK);
  }

  /**
   * 作者按照影响力进行排序
   * @param researchers 作者列表
   * @return 结果列表
   */
  private List<IResearcher> sortResearchersByImpact(
    List<Researcher> researchers
  ) {
    List<IResearcher> iResearchers = new ArrayList<>();
    List<String> rids = new ArrayList<>();
    for (Researcher researcher : researchers) {
      rids.add(researcher.getId());
    }
    try {
      List<IImpact> impacts = (List<IImpact>) restTemplate.postForObject(
        taskImpactAnalysisServiceAddress + "/impact/researchers",
        rids,
        List.class
      );
      if (impacts != null) {
        HashMap<String, Double> map = new HashMap<>();
        for (int i = 0; i < researchers.size(); i++) {
          map.put(researchers.get(i).getId(), impacts.get(i).getImpact());
        }
        researchers.sort(
          (o1, o2) -> (int) (map.get(o2.getId()) - map.get(o1.getId()))
        );
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
    return iResearchers;
  }

  /**
   * 获得论文发表期间相同领域论文(不在引文和本刊物中)的作者列表
   * @param paperDomains 论文领域
   * @param researchers 作者列表
   */
  private void getReviewerFormSimilarDomain(
    List<Domain> paperDomains,
    List<Researcher> researchers
  ) {
    List<String> domainIds = new ArrayList<>();
    for (Domain domain : paperDomains) {
      domainIds.add(domain.getId());
    }
    try {
      List<Paper> similarDomainPapers = (List<Paper>) restTemplate.postForObject(
        paperServiceAddress + "/paper/getPapersByDomain",
        domainIds,
        List.class
      );
      addRid(researchers, similarDomainPapers);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获得在投稿刊物发表过相似领域文章（不在引文中）的作者
   * @param paperJournal 刊物
   * @param date 论文发表时间
   * @param researchers 作者列表
   */
  private void getReviewerFromSameJournal(
    String paperJournal,
    String date,
    List<Researcher> researchers
  ) {
    try {
      List<Paper> sameJournalPapers = (List<Paper>) restTemplate.getForObject(
        paperServiceAddress + "/paper/getPapersByJournal/{journal}/{date}",
        List.class,
        paperJournal,
        date
      );
      addRid(researchers, sameJournalPapers);
    } catch (Exception e) {
      e.printStackTrace();
    }
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
   * @param paperDomains 论文领域
   **/
  private void removeNoSameDomains(
    List<Researcher> researchers,
    List<Domain> paperDomains
  ) {
    try {
      List<String> rids = new ArrayList<>();
      for (Researcher researcher : researchers) {
        rids.add(researcher.getId());
      }
      List<List<Domain>> reviewersDomains = (List<List<Domain>>) restTemplate.postForObject(
        researcherServiceAddress + "/researcher/getDomains",
        rids,
        List.class
      );

      if (reviewersDomains != null) {
        List<String> paperDomainIds = new ArrayList<>();
        for (Domain domain : paperDomains) {
          paperDomainIds.add(domain.getId());
        }

        HashMap<String, List<Domain>> map = new HashMap<>();
        for (int i = 0; i < researchers.size(); i++) {
          map.put(researchers.get(i).getId(), reviewersDomains.get(i));
        }
        for (String rid : map.keySet()) {
          List<Domain> domains = map.get(rid);
          List<String> domainIds = new ArrayList<>();
          for (Domain domain : domains) {
            domainIds.add(domain.getId());
          }
          boolean hasNoSameDomain = Collections.disjoint(
            paperDomainIds,
            domainIds
          );
          if (hasNoSameDomain) {
            researchers.removeIf(s -> s.getId().equals(rid));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public ResponseEntity<List<IResearcher>> getNotRecommendReviewer(String id) {
    return null;
  }
}
