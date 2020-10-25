package org.njuse17advancedse.taskpartnershipanalysis.serviceImp;

import java.util.*;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IPaper;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IPaperBasic;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskPartnershipAnalysisServiceImp
  implements TaskPartnershipAnalysisService {
  private final ResearcherEntityService researcherEntityService;

  private final PaperEntityService paperEntityService;

  private final DomainEntityService domainEntityService;

  private final TaskImpactAnalysisService taskImpactAnalysisService;

  public TaskPartnershipAnalysisServiceImp(
    ResearcherEntityService researcherEntityService,
    PaperEntityService paperEntityService,
    DomainEntityService domainEntityService,
    TaskImpactAnalysisService taskImpactAnalysisService
  ) {
    this.researcherEntityService = researcherEntityService;
    this.paperEntityService = paperEntityService;
    this.domainEntityService = domainEntityService;
    this.taskImpactAnalysisService = taskImpactAnalysisService;
  }

  @Override
  public ResponseEntity<List<String>> getPartners(String researcherId) {
    List<String> partners = new ArrayList<>();
    try {
      partners = getPartnersById(researcherId);
      return new ResponseEntity<>(partners, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(partners, HttpStatus.EXPECTATION_FAILED);
  }

  /**
   * 根据作者id获得论文列表
   * @param researcherId 作者id
   * @return 论文id列表
   */
  private List<String> getPartnersById(String researcherId) {
    List<String> partners = new ArrayList<>();
    List<String> papers = new ArrayList<>();
    //获得作者的所有论文
    try {
      papers =
        researcherEntityService.getPaperByResearcherId(
          researcherId,
          null,
          null
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
    for (String paperId : papers) {
      //根据获得的论文id获得论文的简要信息
      IPaperBasic iPaperBasic = new IPaperBasic();
      try {
        iPaperBasic = paperEntityService.getPaperBasicInfo(paperId);
        if (iPaperBasic.getResearchers() != null) {
          partners.addAll(iPaperBasic.getResearchers());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    HashSet<String> hashSet = new HashSet<>(partners);
    partners.clear();
    partners.addAll(hashSet);
    return partners;
  }

  @Override
  public ResponseEntity<IResearcherNet> getPartnership(
    String researcherId,
    String startDate,
    String endDate
  ) {
    IResearcherNet iResearcherNet;
    //根据作者，时间获得该作者发表的论文
    List<IPaper> iPapers = getPapersByResearcherId(
      researcherId,
      startDate,
      endDate
    );
    List<String> referenceIds = new ArrayList<>(); //作者引用文章id列表

    //根据论文得到合作作者与频次的map
    HashMap<String, Integer> coAuthorMap = new HashMap<>();
    for (IPaper iPaper : iPapers) {
      referenceIds.addAll(iPaper.getReferences());
      List<String> rids = iPaper.getResearchers();
      if (rids != null) {
        for (String rid : rids) {
          if (!rid.equals(researcherId)) {
            Integer count = coAuthorMap.get(rid);
            coAuthorMap.put(rid, (count == null) ? 1 : count + 1);
          }
        }
      }
    }
    HashSet<String> set = new HashSet<>(referenceIds);
    referenceIds.clear();
    referenceIds.addAll(set);
    List<String> partnerIds = Lists.newArrayList(coAuthorMap.keySet());

    //根据论文得到共引作者与数量的map
    HashMap<String, Integer> coCitationMap = getReferenceOfPartners(
      referenceIds,
      partnerIds,
      startDate,
      endDate
    );
    //计算后得到作者合作关系图
    iResearcherNet = countByAuthorAndCitation(coAuthorMap, coCitationMap);
    //    for(Double[] doubles:iResearcherNet.getWeight()){
    //      System.out.println(doubles[0]+","+doubles[1]);
    //    }
    return new ResponseEntity<>(iResearcherNet, HttpStatus.OK);
  }

  /**
   * 根据公式计算得到结果
   * @param coAuthorMap 合著map
   * @param coCitationMap 共引map
   * @return 结果表示
   */
  private IResearcherNet countByAuthorAndCitation(
    HashMap<String, Integer> coAuthorMap,
    HashMap<String, Integer> coCitationMap
  ) {
    IResearcherNet iResearcherNet = new IResearcherNet();
    List<Integer> authorValues = new ArrayList<>(coAuthorMap.values());
    List<Integer> citationValues = new ArrayList<>(coCitationMap.values());
    if (authorValues.size() >= 1 && citationValues.size() >= 1) {
      int max_a = Collections.max(authorValues); //合著次数最大值
      int min_a = Collections.min(authorValues); //合著次数最小值
      int max_c = Collections.max(citationValues); //共引论文数最大值
      int min_c = Collections.min(citationValues); //共引论文数最小值
      List<String> partners = new ArrayList<>(coAuthorMap.keySet());
      List<Double[]> weights = new ArrayList<>();
      for (String partner : partners) {
        Double a = (double) (coAuthorMap.get(partner) - min_a + 1) /
        (max_a - min_a + 1);
        Double c = (double) (coCitationMap.get(partner) - min_c + 1) /
        (max_c - min_c + 1);
        Double[] doubles = new Double[2];
        doubles[0] = Double.parseDouble(String.format("%.2f", a));
        doubles[1] = Double.parseDouble(String.format("%.2f", c));
        weights.add(doubles);
      }
      iResearcherNet.setPartners(partners);
      iResearcherNet.setWeight(weights);
    }
    return iResearcherNet;
  }

  /**
   * 根据id列表获得引文id列表
   * @param referenceIds 该作者引文id列表
   * @param partnerIds 合作者id列表
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @return 引文id列表
   */
  private HashMap<String, Integer> getReferenceOfPartners(
    List<String> referenceIds,
    List<String> partnerIds,
    String startDate,
    String endDate
  ) {
    HashMap<String, Integer> map = new HashMap<>();
    for (String partnerId : partnerIds) {
      List<IPaper> iPapers = getPapersByResearcherId(
        partnerId,
        startDate,
        endDate
      );

      List<String> references = new ArrayList<>();
      List<String> temp = new ArrayList<>(referenceIds);
      for (IPaper iPaper : iPapers) {
        List<String> refs = new ArrayList<>();
        if (iPaper != null) {
          refs = iPaper.getReferences();
        }
        for (String ref : refs) {
          if (!references.contains(ref)) {
            references.add(ref);
          }
        }
      }
      temp.retainAll(references);
      map.put(partnerId, temp.size());
    }
    return map;
  }

  /**
   * 根据作者id和时间段获得与当前作者该时间段内发表的论文列表
   * @param researcherId 作者id
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @return 论文列表
   */
  private List<IPaper> getPapersByResearcherId(
    String researcherId,
    String startDate,
    String endDate
  ) {
    List<IPaper> papers = new ArrayList<>();
    try {
      List<String> paperIds = researcherEntityService.getPaperByResearcherId(
        researcherId,
        startDate,
        endDate
      );
      if (paperIds != null) {
        for (String pid : paperIds) {
          IPaper iPaper = paperEntityService.getPaper(pid);
          if (iPaper != null) {
            papers.add(iPaper);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return papers;
  }

  @Override
  public ResponseEntity<Map<String, Double>> getPotentialPartners(
    String researchId
  ) {
    Map<String, Double> potentialPartnerNet = new HashMap<>();

    //通过相似的研究领域获得推荐合作者候选
    List<String> candidate_domain = getResearchersOfSimilarDomainById(
      researchId
    );

    List<String> domains = new ArrayList<>();
    try {
      domains = researcherEntityService.getDomainsByResearcherId(researchId);
      List<IPaperBasic> iPaperBasics = getPaperBasicsById(researchId);

      //通过合作关系网络获得推荐合作者候选
      List<String> candidate_cooperation = getPartnersById(researchId);

      candidate_domain.addAll(candidate_cooperation);
      //取并集
      List<String> candidate = candidate_domain
        .stream()
        .distinct()
        .collect(Collectors.toList());
      for (String partner : candidate) {
        List<IPaperBasic> iPaperBasics2 = getPaperBasicsById(partner);
        //计算三个评估方向的得分加权和
        //TODO 权值待确定
        Double score =
          countByImpact(partner) + //影响力数值评分
          countByDomain(domains, partner) + //领域契合度数值评分
          countByCooperation(iPaperBasics, iPaperBasics2); //合作程度量化数值评分
        potentialPartnerNet.put(
          partner,
          Double.parseDouble(String.format("%.2f", score))
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    //保留得分最高的前十个数据
    if (potentialPartnerNet.size() > 10) {
      List<Double> scores = new ArrayList<>(potentialPartnerNet.values());
      scores.sort((o1, o2) -> (int) (o2 - o1));
      Iterator<Map.Entry<String, Double>> iterator = potentialPartnerNet
        .entrySet()
        .iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, Double> entry = iterator.next();
        Double value = entry.getValue();
        if (value <= scores.get(10)) {
          iterator.remove();
        }
      }
    }
    return new ResponseEntity<>(potentialPartnerNet, HttpStatus.OK);
  }

  /**
   * 根据作者id获得论文列表
   * @param researchId 作者id
   * @return 论文列表
   */
  private List<IPaperBasic> getPaperBasicsById(String researchId) {
    List<String> papers = new ArrayList<>();
    List<IPaperBasic> iPaperBasics = new ArrayList<>();
    try {
      papers =
        researcherEntityService.getPaperByResearcherId(researchId, null, null);
      for (String pid : papers) {
        iPaperBasics.add(paperEntityService.getPaperBasicInfo(pid));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return iPaperBasics;
  }

  /**
   * 计算作者之间的关联程度
   * @param iPaperBasics1 作者论文列表1
   * @param iPaperBasics2 合作者论文列表2
   * @return 量化数值
   */
  private Double countByCooperation(
    List<IPaperBasic> iPaperBasics1,
    List<IPaperBasic> iPaperBasics2
  ) {
    double score = 0.0;
    Calendar cal = Calendar.getInstance();
    int nowYear = cal.get(Calendar.YEAR) + 1;
    try {
      //通过论文简要信息获得year-num的map
      HashMap<String, Integer> mapOfYearAndCoNumber = new HashMap<>();
      HashMap<String, Integer> mapOfYearAndSum1 = new HashMap<>();
      HashMap<String, Integer> mapOfYearAndSum2 = new HashMap<>();
      for (IPaperBasic iPaperBasic : iPaperBasics1) {
        Integer count = mapOfYearAndSum1.get(iPaperBasic.getPublicationDate());
        mapOfYearAndSum1.put(
          iPaperBasic.getPublicationDate(),
          (count == null) ? 1 : count + 1
        );
      }
      for (IPaperBasic iPaperBasic : iPaperBasics2) {
        Integer count = mapOfYearAndSum2.get(iPaperBasic.getPublicationDate());
        mapOfYearAndSum2.put(
          iPaperBasic.getPublicationDate(),
          (count == null) ? 1 : count + 1
        );
      }
      for (String year : mapOfYearAndSum1.keySet()) {
        List<String> p1 = new ArrayList<>();
        for (IPaperBasic iPaperBasic : iPaperBasics1) {
          if (iPaperBasic.getPublicationDate() != null) {
            if (iPaperBasic.getPublicationDate().equals(year)) {
              p1.add(iPaperBasic.getId());
            }
          }
        }
        List<String> p2 = new ArrayList<>();
        for (IPaperBasic iPaperBasic : iPaperBasics2) {
          if (iPaperBasic.getPublicationDate().equals(year)) {
            p2.add(iPaperBasic.getId());
          }
        }
        p1.retainAll(p2);
        if (p1.size() >= 1) {
          mapOfYearAndCoNumber.put(year, p1.size());
        }
      }

      for (String year : mapOfYearAndCoNumber.keySet()) {
        double co_num = mapOfYearAndCoNumber.get(year);
        double sum1 = mapOfYearAndSum1.get(year);
        double sum2 = mapOfYearAndSum2.get(year);
        //计算公式为e的（当前年份-合作发生年份）分之一次方*(当前年份合作发表论文数/（当前年份两者发表论文总数）)
        score +=
          Math.pow(Math.E, (double) 1 / (nowYear - Integer.parseInt(year))) *
          (co_num / (sum1 + sum2));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    score = Double.parseDouble(String.format("%.2f", score));
    return score;
  }

  /**
   * 计算研究者领域覆盖程度
   * @param domains 作者领域列表
   * @param partnerId 合作者id
   * @return 量化数值
   */
  private Double countByDomain(List<String> domains, String partnerId) {
    double score = 0.0;
    try {
      List<String> partnerDomains = researcherEntityService.getDomainsByResearcherId(
        partnerId
      );
      List<String> intersection = new ArrayList<>(domains);
      intersection.retainAll(partnerDomains);
      score =
        (double) intersection.size() / (domains.size() + partnerDomains.size());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return score;
  }

  /**
   * 根据作者id获得影响力
   * @param partnerId 作者id
   * @return 影响力数值
   */
  private Double countByImpact(String partnerId) {
    Double impact = 0.0;
    try {
      impact = taskImpactAnalysisService.getImpactByResearcherId(partnerId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return impact;
  }

  /**
   * 根据作者id获得具有相同研究方向的作者id列表
   * @param researchId 作者id
   * @return 作者id列表
   */
  private List<String> getResearchersOfSimilarDomainById(String researchId) {
    List<String> researcherOfSimilarDomain = new ArrayList<>();
    try {
      List<String> domains = researcherEntityService.getDomainsByResearcherId(
        researchId
      );
      for (String domainId : domains) {
        List<String> researchers = domainEntityService.getResearcherByDomain(
          domainId
        );
        researcherOfSimilarDomain.addAll(researchers);
      }
      HashSet<String> hashSet = new HashSet<>(researcherOfSimilarDomain);
      researcherOfSimilarDomain.clear();
      researcherOfSimilarDomain.addAll(hashSet);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return researcherOfSimilarDomain;
  }
}
