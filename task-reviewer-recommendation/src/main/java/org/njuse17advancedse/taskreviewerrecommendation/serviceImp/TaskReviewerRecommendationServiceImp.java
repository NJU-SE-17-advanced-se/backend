package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import java.util.*;
import java.util.stream.Collectors;
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
  private static String taskPartnerShipServiceAddress = ""; // 合作关系业务服务地址

  private final RestTemplate restTemplate;

  public TaskReviewerRecommendationServiceImp(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ResponseEntity<List<IResearcher>> getRecommendReviewer(String id) {
    List<IResearcher> iResearchers = new ArrayList<>();

    //通过论文实体服务获得解析完成的论文实体
    Paper paper = getPaperById(id);
    if (paper != null) {
      /* 第一步，获得引用文献的作者 */
      List<Paper> references = paper.getReferences(); //默认获得的引用文献中所有的作者实体均已处理完成
      List<Researcher> researchers = new ArrayList<>(); // 作者id列表

      addRid(researchers, references);

      /* 第二步，获得最近在投稿刊物发表过文章（不在引文中）的作者 */
      String paperJournal = paper.getJournal();
      //getReviewerFromSameJournal(paperJournal, paper.getDate(), researchers);

      /* 第三步， 获得论文发表期间相同领域论文(不在引文和本刊物中)的作者列表*/
      List<Domain> paperDomains = paper.getDomains();
      //getReviewerFormSimilarDomain(paperDomains, researchers);

      /* 第四步，获得这些作者的研究领域并与论文研究领域进行比照，删去无重叠领域的作者 */
      researchers = deduplicateAndSort(researchers); //去重并排序

      removeNoSameDomains(researchers, paperDomains);

      /* 第五步，将剩余作者按照影响力进行排序，取前5个作为推荐审稿人候选 */
      iResearchers = sortResearchersByImpact(researchers);
    }
    return new ResponseEntity<>(iResearchers, HttpStatus.OK);
  }

  /**
   * 根据id获取论文实体
   * @param id 论文id
   * @return 论文实体paper
   */
  private Paper getPaperById(String id) {
    Paper paper = new Paper();
    try {
      paper =
        restTemplate.getForObject(
          paperServiceAddress + "/paper/getNewPaper/" + id,
          Paper.class
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return paper;
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
          researchers = researchers.subList(0, 5);
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
        paperServiceAddress +
        "/paper/getPapersByJournal/" +
        paperJournal +
        "/" +
        date,
        List.class
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
    List<IResearcher> iResearchers = new ArrayList<>();
    List<Researcher> partners = new ArrayList<>(); //合作作者列表

    //根据id获得论文实体
    Paper paper = getPaperById(id);
    if (paper != null) {
      List<Researcher> researchersOfPaper = paper.getResearchers();
      List<String> rids = researchersOfPaper
        .stream()
        .map(Researcher::getId)
        .collect(Collectors.toList());

      //第一步，根据id获得与论文作者合作过的作者列表
      partners.addAll(getPartnersByRid(rids));

      //第二步，获得合作者的研究领域并按照相关程度排序，取前五名作者
      iResearchers = sortPartnersByDomains(partners, paper.getDomains());
    }
    return new ResponseEntity<>(iResearchers, HttpStatus.OK);
  }

  /**
   * 获得合作者的研究领域并按照相关程度排序，取前五名作者
   * @param partners 合作者列表
   * @param domains 投稿文章领域
   * @return 结果列表
   */
  private List<IResearcher> sortPartnersByDomains(
    List<Researcher> partners,
    List<Domain> domains
  ) {
    List<IResearcher> iResearchers = new ArrayList<>();

    //合作者id列表去重
    List<String> rids = partners
      .stream()
      .map(Researcher::getId)
      .collect(Collectors.toList());
    HashSet<String> set = new HashSet<>(rids);
    rids.clear();
    rids.addAll(set);

    try {
      List<List<Domain>> partnerDomains = (List<List<Domain>>) restTemplate.postForObject(
        researcherServiceAddress + "/researcher/getDomains",
        rids,
        List.class
      );
      if (partnerDomains != null) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < rids.size(); i++) {
          List<String> domainIds = domains
            .stream()
            .map(Domain::getId)
            .collect(Collectors.toList());
          int sumOfDomain = 0;
          List<Domain> domainsOfPartners = partnerDomains.get(i);
          List<String> domainOfPartnersIds = domainsOfPartners
            .stream()
            .map(Domain::getId)
            .collect(Collectors.toList());
          //          System.out.println("this:"+domainIds);
          //          System.out.println("that:"+domainOfPartnersIds);
          domainIds.retainAll(domainOfPartnersIds);
          sumOfDomain = domainIds.size();
          map.put(rids.get(i), sumOfDomain);
        }
        rids.sort((o1, o2) -> map.get(o2) - map.get(o1));
        if (rids.size() > 5) {
          rids = rids.subList(0, 5);
        }
        for (String rid : rids) {
          for (Researcher researcher : partners) {
            if (researcher.getId().equals(rid)) {
              iResearchers.add(new IResearcher(researcher));
              break;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return iResearchers;
  }

  /**
   * 根据作者id获得合作作者
   * @param rids 作者id列表
   * @return 合作作者列表
   */
  private List<Researcher> getPartnersByRid(List<String> rids) {
    List<Researcher> researchers = new ArrayList<>();
    try {
      researchers =
        (List<Researcher>) restTemplate.postForObject(
          taskPartnerShipServiceAddress + "/getPartnersByRids/",
          rids,
          List.class
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return researchers;
  }
}
