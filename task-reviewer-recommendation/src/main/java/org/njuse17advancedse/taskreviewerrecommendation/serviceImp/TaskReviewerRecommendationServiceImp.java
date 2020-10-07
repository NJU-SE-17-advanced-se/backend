package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import java.util.*;
import java.util.stream.Collectors;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IImpact;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperUpload;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Domain;
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
  public ResponseEntity<List<IResearcher>> getRecommendReviewer(
    IPaperUpload iPaperUpload
  ) {
    List<IResearcher> iResearchers = new ArrayList<>();

    if (iPaperUpload != null) {
      /* 第一步，获得引用文献的作者 */
      List<String> references = iPaperUpload.getReferenceIds(); //默认获得的引用文献中所有的作者实体均已处理完成

      List<String> researcherIds = new ArrayList<>();

      List<String> referenceResearcherIds = getResearcherIdsByPaperIds(
        references
      );

      if (referenceResearcherIds != null) {
        researcherIds = new ArrayList<>(referenceResearcherIds);
      }
      // 作者id列表

      /* 第二步，获得最近在投稿刊物发表过文章（不在引文中）的作者 */
      String paperJournal = iPaperUpload.getJournal();
      //getReviewerFromSameJournal(paperJournal, iPaperUpload.getDate(), researcherIds);

      /* 第三步， 获得论文发表期间相同领域论文(不在引文和本刊物中)的作者列表*/
      List<String> paperDomainIds = iPaperUpload.getDomainIds();
      //getReviewerFormSimilarDomain(paperDomainIds, researcherIds);

      /* 第四步，获得这些作者的研究领域并与论文研究领域进行比照，删去无重叠领域的作者 */
      researcherIds = deduplicateAndSort(researcherIds); //去重并排序

      removeNoSameDomains(researcherIds, paperDomainIds);

      /* 第五步，将剩余作者按照影响力进行排序，取前5个作为推荐审稿人候选 */
      researcherIds = sortResearchersByImpact(researcherIds);

      iResearchers = getResearcherByIds(researcherIds);
    }
    return new ResponseEntity<>(iResearchers, HttpStatus.OK);
  }

  /**
   * 根据作者id列表获得作者实体列表
   * @param researcherIds 作者id列表
   * @return 作者实体列表
   */
  private List<IResearcher> getResearcherByIds(List<String> researcherIds) {
    List<Researcher> researchers;
    List<IResearcher> iResearchers = new ArrayList<>();
    try {
      researchers =
        (List<Researcher>) restTemplate.postForObject(
          researcherServiceAddress + "/getResearchers",
          researcherIds,
          List.class
        );
      if (researchers != null) {
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
   * 根据引用论文id获得作者id
   * @param references 引用论文id列表
   * @return 作者id列表
   */
  private List<String> getResearcherIdsByPaperIds(List<String> references) {
    List<String> researcherIds = new ArrayList<>();
    try {
      researcherIds =
        (List<String>) restTemplate.postForObject(
          paperServiceAddress + "/getResearchersByPaperIds",
          references,
          List.class
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return researcherIds;
  }

  /**
   * 作者按照影响力进行排序
   * @param researcherIds 作者列表
   */
  private List<String> sortResearchersByImpact(List<String> researcherIds) {
    try {
      List<IImpact> impacts = (List<IImpact>) restTemplate.postForObject(
        taskImpactAnalysisServiceAddress + "/impact/researchers",
        researcherIds,
        List.class
      );
      if (impacts != null) {
        HashMap<String, Double> map = new HashMap<>();
        for (int i = 0; i < researcherIds.size(); i++) {
          map.put(researcherIds.get(i), impacts.get(i).getImpact());
        }
        researcherIds.sort((o1, o2) -> (int) (map.get(o2) - map.get(o1)));
        if (researcherIds.size() > 5) {
          researcherIds = researcherIds.subList(0, 5);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return researcherIds;
  }

  /**
   * 获得论文发表期间相同领域论文(不在引文和本刊物中)的作者列表
   * @param domainIds 论文领域
   * @param researcherIds 作者列表
   */
  private void getReviewerFormSimilarDomain(
    List<String> domainIds,
    List<String> researcherIds
  ) {
    try {
      List<String> researcherOfSimilarDomainPapers = (List<String>) restTemplate.postForObject(
        paperServiceAddress + "/paper/getPapersByDomain",
        domainIds,
        List.class
      );
      if (researcherOfSimilarDomainPapers != null) {
        researcherIds.addAll(researcherOfSimilarDomainPapers);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获得在投稿刊物发表过相似领域文章（不在引文中）的作者
   * @param paperJournalId 刊物
   * @param date 论文发表时间
   * @param researcherIds 作者id列表
   */
  private void getReviewerFromSameJournal(
    String paperJournalId,
    String date,
    List<String> researcherIds
  ) {
    try {
      List<String> researchersOfSameJournalPapers = (List<String>) restTemplate.getForObject(
        paperServiceAddress +
        "/paper/getPapersByJournal/" +
        paperJournalId +
        "/" +
        date,
        List.class
      );
      if (researchersOfSameJournalPapers != null) {
        researcherIds.addAll(researchersOfSameJournalPapers);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 作者去重按照出现次数降序排列
   * @param researcherIds 作者id列表
   */
  private List<String> deduplicateAndSort(List<String> researcherIds) {
    Map<String, Integer> map = new HashMap<>();

    for (String rid : researcherIds) {
      Integer count = map.get(rid);
      map.put(rid, (count == null) ? 1 : count + 1);
    }

    HashSet<String> set = new HashSet<>(researcherIds);
    researcherIds.clear();
    researcherIds.addAll(set);
    researcherIds.sort((o1, o2) -> map.get(o2) - map.get(o1)); //去重完成并按照出现次数降序排列

    List<String> researcherList = new ArrayList<>();
    for (String rid : researcherIds) {
      for (String researcher : researcherIds) {
        if (researcher.equals(rid)) {
          researcherList.add(researcher);
          break;
        }
      }
    }
    return researcherList;
  }

  /**
   * 去除无重叠领域的作者
   * @param researcherIds 作者id列表
   * @param paperDomainIds 论文领域
   **/
  private void removeNoSameDomains(
    List<String> researcherIds,
    List<String> paperDomainIds
  ) {
    try {
      List<List<Domain>> reviewersDomains = (List<List<Domain>>) restTemplate.postForObject(
        researcherServiceAddress + "/researcher/getDomains",
        researcherIds,
        List.class
      );

      if (reviewersDomains != null) {
        HashMap<String, List<Domain>> map = new HashMap<>();
        for (int i = 0; i < researcherIds.size(); i++) {
          map.put(researcherIds.get(i), reviewersDomains.get(i));
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
            researcherIds.removeIf(s -> s.equals(rid));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public ResponseEntity<List<IResearcher>> getNotRecommendReviewer(
    IPaperUpload iPaperUpload
  ) {
    List<IResearcher> iResearchers = new ArrayList<>();
    List<Researcher> partners = new ArrayList<>(); //合作作者列表

    if (iPaperUpload != null) {
      List<String> researcherIdsOfPaper = iPaperUpload.getResearcherIds();

      //第一步，根据id获得与论文作者合作过的作者列表
      partners.addAll(getPartnersByRid(researcherIdsOfPaper));

      //第二步，获得合作者的研究领域并按照相关程度排序，取前五名作者
      iResearchers =
        sortPartnersByDomains(partners, iPaperUpload.getDomainIds());
    }
    return new ResponseEntity<>(iResearchers, HttpStatus.OK);
  }

  /**
   * 获得合作者的研究领域并按照相关程度排序，取前五名作者
   * @param partners 合作者列表
   * @param domainIds 投稿文章领域
   * @return 结果列表
   */
  private List<IResearcher> sortPartnersByDomains(
    List<Researcher> partners,
    List<String> domainIds
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
          List<String> tempDomainIds = new ArrayList<>(domainIds);
          int sumOfDomain;
          List<Domain> domainsOfPartners = partnerDomains.get(i);
          List<String> domainOfPartnersIds = domainsOfPartners
            .stream()
            .map(Domain::getId)
            .collect(Collectors.toList());
          System.out.println("this:" + tempDomainIds);
          System.out.println("that:" + domainOfPartnersIds);
          tempDomainIds.retainAll(domainOfPartnersIds);
          sumOfDomain = tempDomainIds.size();
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
