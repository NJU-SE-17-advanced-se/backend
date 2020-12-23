package org.njuse17advancedse.taskpartnershipanalysis.implement;

import java.util.*;
import java.util.stream.Collectors;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.entity.JpaPaper;
import org.njuse17advancedse.taskpartnershipanalysis.repository.ResearcherRepository;
import org.njuse17advancedse.taskpartnershipanalysis.service.*;
import org.springframework.stereotype.Service;

@Service
public class TaskPartnershipAnalysisServiceImp
  implements TaskPartnershipAnalysisService {
  private final ResearcherRepository researcherRepository;

  public TaskPartnershipAnalysisServiceImp(
    ResearcherRepository researcherRepository
  ) {
    this.researcherRepository = researcherRepository;
  }

  @Override
  public List<String> getPartners(String researcherId) {
    return researcherRepository.getPartnersByRid(researcherId);
  }

  @Override
  public IResearcherNet getPartnership(
    String researcherId,
    int startDate,
    int endDate
  ) {
    IResearcherNet iResearcherNet;

    //根据作者，时间获得该作者发表的论文
    List<String> papers = researcherRepository.getPapersByRid(
      researcherId,
      startDate,
      endDate
    );

    //根据作者，时间段获得该作者引用过的论文
    List<String> references = researcherRepository.getReferencesByPapers(
      papers
    );

    //生成合作作者与频次的map
    HashMap<String, Integer> coAuthorMap = researcherRepository.getCoAuthorMap(
      researcherId,
      papers
    );

    //生成共引作者与数量的map
    HashMap<String, Integer> coCitationMap = researcherRepository.getCitationMap(
      researcherId,
      references
    );

    //计算后得到作者合作关系图
    iResearcherNet = countByAuthorAndCitation(coAuthorMap, coCitationMap);
    return iResearcherNet;
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
    if (authorValues.size() >= 1) {
      int max_a = Collections.max(authorValues); //合著次数最大值
      int min_a = Collections.min(authorValues); //合著次数最小值
      int max_c = 0;
      int min_c = 0;
      if (citationValues.size() != 0) {
        max_c = Collections.max(citationValues); //共引论文数最大值
        min_c = Collections.min(citationValues); //共引论文数最小值
      }
      List<String> partners = new ArrayList<>(coAuthorMap.keySet());
      List<Double[]> weights = new ArrayList<>();
      for (String partner : partners) {
        Double a = (double) (coAuthorMap.get(partner) - min_a + 1) /
        (max_a - min_a + 1);
        double c;
        if (coCitationMap.get(partner) == null) {
          c = 0.0;
        } else {
          c =
            (double) (coCitationMap.get(partner) - min_c + 1) /
            (max_c - min_c + 1);
        }
        Double[] doubles = new Double[2];
        doubles[0] = Double.parseDouble(String.format("%.2f", a));
        doubles[1] = Double.parseDouble(String.format("%.2f", c));
        weights.add(doubles);
      }
      iResearcherNet.setPartners(partners);
      List<Integer> impacts = new ArrayList<>();
      for (String rid : partners) {
        impacts.add(researcherRepository.getImpactByResearcherId(rid));
      }
      iResearcherNet.setImpacts(impacts);
      iResearcherNet.setWeight(weights);
    }
    return iResearcherNet;
  }

  @Override
  public Map<String, Double> getPotentialPartners(String researcherId) {
    Map<String, Double> potentialPartnerNet = new HashMap<>();

    //获得作者的研究方向列表
    List<String> domains = researcherRepository.getDomainsByResearcherId(
      researcherId
    );

    //通过合作关系网络获得推荐合作者候选
    List<String> candidate_cooperation = researcherRepository.getNearPartnersByRid(
      researcherId
    );

    //通过相似的研究领域获得推荐合作者候选
    List<String> candidate_domain = researcherRepository.getResearchersOfSimilarDomain(
      researcherId,
      domains,
      candidate_cooperation
    );

    //取并集
    candidate_domain.addAll(candidate_cooperation);
    List<String> candidate = candidate_domain
      .stream()
      .distinct()
      .collect(Collectors.toList());

    //根据作者id获得年份发表的文章
    List<JpaPaper> papers1 = researcherRepository.getPaperDateById(
      researcherId
    );

    for (String partner : candidate) {
      List<JpaPaper> papers2 = researcherRepository.getPaperDateById(
        researcherId
      );
      //计算三个评估方向的得分加权和
      Double score =
        countByImpact(partner) *
        0.1 + //影响力数值评分
        countByDomain(domains, partner) + //领域契合度数值评分
        countByCooperation(papers1, papers2); //合作程度量化数值评分
      potentialPartnerNet.put(
        partner,
        Double.parseDouble(String.format("%.2f", score))
      );
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
    return potentialPartnerNet;
  }

  @Override
  public boolean containResearcher(String researcherId) {
    return researcherRepository.containThisResearcher(researcherId);
  }

  /**
   * 计算作者之间的关联程度
   * @param papers1 作者论文列表1
   * @param papers2 合作者论文列表2
   * @return 量化数值
   */
  private Double countByCooperation(
    List<JpaPaper> papers1,
    List<JpaPaper> papers2
  ) {
    double score = 0.0;
    Calendar cal = Calendar.getInstance();
    int nowYear = cal.get(Calendar.YEAR) + 1;

    //通过论文简要信息获得year-num的map
    HashMap<Integer, Integer> mapOfYearAndCoNumber = new HashMap<>();
    HashMap<Integer, Integer> mapOfYearAndSum1 = new HashMap<>();
    HashMap<Integer, Integer> mapOfYearAndSum2 = new HashMap<>();
    for (JpaPaper paper : papers1) {
      Integer count = mapOfYearAndSum1.get(paper.getPublicationDate());
      mapOfYearAndSum1.put(
        paper.getPublicationDate(),
        (count == null) ? 1 : count + 1
      );
    }
    for (JpaPaper paper : papers2) {
      Integer count = mapOfYearAndSum2.get(paper.getPublicationDate());
      mapOfYearAndSum2.put(
        paper.getPublicationDate(),
        (count == null) ? 1 : count + 1
      );
    }
    for (Integer year : mapOfYearAndSum1.keySet()) {
      List<String> p1 = new ArrayList<>();
      for (JpaPaper paper : papers1) {
        if (paper.getPublicationDate() != 0) {
          if (paper.getPublicationDate() == year) {
            p1.add(paper.getId());
          }
        }
      }
      List<String> p2 = new ArrayList<>();
      for (JpaPaper paper : papers2) {
        if (paper.getPublicationDate() == year) {
          p2.add(paper.getId());
        }
      }
      p1.retainAll(p2);
      if (p1.size() >= 1) {
        mapOfYearAndCoNumber.put(year, p1.size());
      }
    }

    for (int year : mapOfYearAndCoNumber.keySet()) {
      double co_num = mapOfYearAndCoNumber.get(year);
      double sum1 = mapOfYearAndSum1.get(year);
      double sum2 = mapOfYearAndSum2.get(year);
      //计算公式为e的（当前年份-合作发生年份）分之一次方*(当前年份合作发表论文数/（当前年份两者发表论文总数）)
      score +=
        Math.pow(Math.E, (double) 1 / (nowYear - year)) *
        (co_num / (sum1 + sum2));
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
    double score;
    List<String> partnerDomains = researcherRepository.getDomainsByResearcherId(
      partnerId
    );
    List<String> intersection = new ArrayList<>(domains);
    intersection.retainAll(partnerDomains);
    if ((domains.size() + partnerDomains.size()) == 0) {
      return 0.0;
    }
    score =
      (double) intersection.size() / (domains.size() + partnerDomains.size());
    return score;
  }

  /**
   * 根据作者id获得影响力
   * @param partnerId 作者id
   * @return 影响力数值
   */
  private Integer countByImpact(String partnerId) {
    return researcherRepository.getImpactByResearcherId(partnerId);
  }
}
