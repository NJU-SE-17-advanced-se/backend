package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import java.util.*;
import org.njuse17advancedse.taskreviewerrecommendation.dto.*;
import org.njuse17advancedse.taskreviewerrecommendation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 审稿人推荐服务实现
 * @author ycj
 * @date 2020/09/24
 */
@Service
public class TaskReviewerRecommendationServiceImp
  implements TaskReviewerRecommendationService {
  private final PaperEntityService paperEntityService;

  private final DomainEntityService domainEntityService;

  private final PublicationEntityService publicationEntityService;

  private final ResearcherEntityService researcherEntityService;

  private final AffiliationEntityService affiliationEntityService;

  private final TaskImpactAnalysisService taskImpactAnalysisService;

  private final TaskPartnershipService taskPartnershipService;

  @Autowired
  public TaskReviewerRecommendationServiceImp(
    PaperEntityService paperEntityService,
    DomainEntityService domainEntityService,
    PublicationEntityService publicationEntityService,
    ResearcherEntityService researcherEntityService,
    AffiliationEntityService affiliationEntityService,
    TaskImpactAnalysisService taskImpactAnalysisService,
    TaskPartnershipService taskPartnershipService
  ) {
    this.paperEntityService = paperEntityService;
    this.domainEntityService = domainEntityService;
    this.publicationEntityService = publicationEntityService;
    this.researcherEntityService = researcherEntityService;
    this.affiliationEntityService = affiliationEntityService;
    this.taskImpactAnalysisService = taskImpactAnalysisService;
    this.taskPartnershipService = taskPartnershipService;
  }

  @Override
  public ResponseEntity<List<String>> getRecommendReviewer(
    IPaperUpload iPaperUpload
  ) {
    List<String> researcherIds = new ArrayList<>();

    if (iPaperUpload != null) {
      /* 第一步，获得引用文献的作者 */
      List<String> references = iPaperUpload.getReferenceIds(); //默认获得的引用文献中所有的作者实体均已处理完成

      List<String> referenceResearcherIds = getResearcherIdsByPaperIds(
        references
      );

      researcherIds = new ArrayList<>(referenceResearcherIds);
      // 作者id列表

      /* 第二步，获得最近在投稿刊物发表过文章（不在引文中）的作者 */
      String paperJournal = iPaperUpload.getJournal();
      getReviewerFromSameJournal(
        paperJournal,
        iPaperUpload.getDate(),
        researcherIds
      );

      /* 第三步， 获得论文发表期间相同领域论文(不在引文和本刊物中)的作者列表*/
      List<String> paperDomainIds = iPaperUpload.getDomainIds();
      getReviewerFormSimilarDomain(paperDomainIds, researcherIds);

      /* 第四步，获得这些作者的研究领域并与论文研究领域进行比照，删去无重叠领域的作者 */
      researcherIds = deduplicateAndSort(researcherIds); //去重并排序

      removeNoSameDomains(researcherIds, paperDomainIds);

      /* 第五步，将剩余作者按照影响力进行排序，取前5个作为推荐审稿人候选 */
      researcherIds = sortResearchersByImpact(researcherIds);
    }
    return new ResponseEntity<>(researcherIds, HttpStatus.OK);
  }

  /**
   * 根据引用论文id获得作者id
   * @param references 引用论文id列表
   * @return 作者id列表
   */
  private List<String> getResearcherIdsByPaperIds(List<String> references) {
    List<String> researcherIds = new ArrayList<>();
    for (String reference : references) {
      try {
        IPaperBasic iPaperBasic = paperEntityService.getPaperBasicInfo(
          reference
        );
        if (iPaperBasic != null) {
          researcherIds.addAll(iPaperBasic.getResearchers());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return researcherIds;
  }

  /**
   * 作者按照影响力进行排序
   * @param researcherIds 作者列表
   */
  private List<String> sortResearchersByImpact(List<String> researcherIds) {
    List<Double> impacts = new ArrayList<>();
    try {
      for (String researcherId : researcherIds) {
        impacts.add(
          taskImpactAnalysisService.getImpactByResearcherId(researcherId)
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    HashMap<String, Double> map = new HashMap<>();
    for (int i = 0; i < researcherIds.size(); i++) {
      map.put(researcherIds.get(i), impacts.get(i));
    }
    researcherIds.sort((o1, o2) -> (int) (map.get(o2) - map.get(o1)));
    if (researcherIds.size() > 5) {
      researcherIds = researcherIds.subList(0, 5);
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
    List<String> researcherOfSimilarDomainPapers = new ArrayList<>();
    try {
      for (String domainId : domainIds) {
        researcherOfSimilarDomainPapers.addAll(
          domainEntityService.getResearcherByDomain(domainId)
        );
      }
      HashSet<String> hashSet = new HashSet<>(researcherOfSimilarDomainPapers);
      researcherOfSimilarDomainPapers.clear();
      researcherOfSimilarDomainPapers.addAll(hashSet);
      researcherIds.addAll(researcherOfSimilarDomainPapers);
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
      List<String> researchersOfSameJournalPapers = publicationEntityService.getPapersByPublication(
        paperJournalId,
        date,
        null
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
      List<List<String>> reviewersDomainIds = new ArrayList<>();
      for (String researcherId : researcherIds) {
        reviewersDomainIds.add(
          researcherEntityService.getDomainsByResearcherId(researcherId)
        );
      }
      HashMap<String, List<String>> map = new HashMap<>();
      for (int i = 0; i < researcherIds.size(); i++) {
        map.put(researcherIds.get(i), reviewersDomainIds.get(i));
      }
      for (String rid : map.keySet()) {
        List<String> domainIds = map.get(rid);
        boolean hasNoSameDomain = Collections.disjoint(
          paperDomainIds,
          domainIds
        );
        if (hasNoSameDomain) {
          researcherIds.removeIf(s -> s.equals(rid));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public ResponseEntity<List<String>> getNotRecommendReviewer(
    IPaperUpload iPaperUpload
  ) {
    List<String> researcherIds = new ArrayList<>();
    List<String> partners = new ArrayList<>(); //合作作者列表

    if (iPaperUpload != null) {
      List<String> researcherIdsOfPaper = iPaperUpload.getResearcherIds();

      //第一步，根据id获得与论文作者合作过的作者列表
      partners.addAll(getPartnersByRid(researcherIdsOfPaper));

      //第二步，根据id获得与论文作者同机构的作者列表
      partners.addAll(getPartnerFromSameAffiliation(researcherIdsOfPaper));

      //第二步，获得合作者的研究领域并按照相关程度排序，取前五名作者
      researcherIds =
        sortPartnersByDomains(partners, iPaperUpload.getDomainIds());
    }
    return new ResponseEntity<>(researcherIds, HttpStatus.OK);
  }

  /**
   * 获得与论文作者相同机构的作者列表
   * @param researcherIdsOfPaper 当前论文作者id列表
   * @return 同机构作者id列表
   */
  private List<String> getPartnerFromSameAffiliation(
    List<String> researcherIdsOfPaper
  ) {
    List<String> partnersFromSameAffiliation = new ArrayList<>();
    for (String rid : researcherIdsOfPaper) {
      IResearcher iResearcher = researcherEntityService.getResearcherById(rid);
      List<String> affiliations = iResearcher.getAffiliation();
      for (String aid : affiliations) {
        IAffiliation iAffiliation = affiliationEntityService.getAffiliationById(
          aid
        );
        partnersFromSameAffiliation.addAll(iAffiliation.getResearchers());
      }
    }
    HashSet<String> hashSet = new HashSet<>(partnersFromSameAffiliation);
    partnersFromSameAffiliation.clear();
    partnersFromSameAffiliation.addAll(hashSet);
    return partnersFromSameAffiliation;
  }

  /**
   * 获得合作者的研究领域并按照相关程度排序，取前五名作者
   * @param rids 合作者列表
   * @param domainIds 投稿文章领域
   * @return 结果列表
   */
  private List<String> sortPartnersByDomains(
    List<String> rids,
    List<String> domainIds
  ) {
    List<String> researcherIds = new ArrayList<>();

    //合作者id列表去重
    HashSet<String> set = new HashSet<>(rids);
    rids.clear();
    rids.addAll(set);

    try {
      List<List<String>> partnerDomains = new ArrayList<>();
      for (String rid : rids) {
        partnerDomains.add(
          researcherEntityService.getDomainsByResearcherId(rid)
        );
      }
      HashMap<String, Integer> map = new HashMap<>();
      for (int i = 0; i < rids.size(); i++) {
        List<String> tempDomainIds = new ArrayList<>(domainIds);
        int sumOfDomain;
        List<String> domainOfPartnersIds = partnerDomains.get(i);
        //System.out.println("this:" + tempDomainIds);
        //System.out.println("that:" + domainOfPartnersIds);
        tempDomainIds.retainAll(domainOfPartnersIds);
        sumOfDomain = tempDomainIds.size();
        map.put(rids.get(i), sumOfDomain);
      }
      rids.sort((o1, o2) -> map.get(o2) - map.get(o1));
      if (rids.size() > 5) {
        rids = rids.subList(0, 5);
      }
      researcherIds = new ArrayList<>(rids);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return researcherIds;
  }

  /**
   * 根据作者id获得合作作者
   * @param rids 作者id列表
   * @return 合作作者列表
   */
  private List<String> getPartnersByRid(List<String> rids) {
    List<String> researchers = new ArrayList<>();
    try {
      for (String rid : rids) {
        researchers.addAll(
          taskPartnershipService.getPartnersByResearcherId(rid)
        );
      }
      HashSet<String> hashSet = new HashSet<>(researchers);
      researchers.clear();
      researchers.addAll(hashSet);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return researchers;
  }
}
