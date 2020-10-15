package org.njuse17advancedse.taskpartnershipanalysis.serviceImp;

import java.util.*;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
import org.njuse17advancedse.taskpartnershipanalysis.dto.R_ScoreData;
import org.njuse17advancedse.taskpartnershipanalysis.entity.Paper;
import org.njuse17advancedse.taskpartnershipanalysis.entity.Researcher;
import org.njuse17advancedse.taskpartnershipanalysis.service.TaskPartnershipAnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TaskPartnershipAnalysisServiceImp
  implements TaskPartnershipAnalysisService {
  private final String paperServiceAddress = "";
  private final String researcherServiceAddress = "";
  private final String taskImpactAnalysisServiceAddress = "";
  private final String taskDomainAnalysisServiceAddress = "";

  private final RestTemplate restTemplate;

  public TaskPartnershipAnalysisServiceImp(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ResponseEntity<IResearcherNet> getPartnership(
    String researcherId,
    String startDate,
    String endDate
  ) {
    IResearcherNet iResearcherNet;
    //根据作者，时间获得该作者发表的论文
    List<Paper> papers = getPapersByResearcherId(
      researcherId,
      startDate,
      endDate
    );

    List<String> referenceIds = new ArrayList<>(); //作者引用文章id列表

    //根据论文得到合作作者与频次的map
    HashMap<String, Integer> coAuthorMap = new HashMap<>();
    if (papers != null) {
      for (Paper paper : papers) {
        referenceIds.addAll(
          paper
            .getReferences()
            .stream()
            .map(Paper::getId)
            .collect(Collectors.toList())
        );
        List<String> rids = paper
          .getResearchers()
          .stream()
          .map(Researcher::getId)
          .collect(Collectors.toList());
        for (String rid : rids) {
          if (!rid.equals(researcherId)) {
            Integer count = coAuthorMap.get(rid);
            coAuthorMap.put(rid, (count == null) ? 1 : count + 1);
          }
        }
      }
      HashSet<String> set = new HashSet<>(referenceIds);
      referenceIds.clear();
      referenceIds.addAll(set);
    }
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
      List<Paper> papers = getPapersByResearcherId(
        partnerId,
        startDate,
        endDate
      );
      List<String> references = new ArrayList<>();
      List<String> temp = new ArrayList<>(referenceIds);
      if (papers != null) {
        for (Paper paper : papers) {
          for (String paperId : paper
            .getReferences()
            .stream()
            .map(Paper::getId)
            .collect(Collectors.toList())) {
            if (!references.contains(paperId)) {
              references.add(paperId);
            }
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
  private List<Paper> getPapersByResearcherId(
    String researcherId,
    String startDate,
    String endDate
  ) {
    List<Paper> papers = new ArrayList<>();
    try {
      papers =
        (List<Paper>) restTemplate.getForObject(
          paperServiceAddress +
          "/getPapers/" +
          researcherId +
          "/" +
          startDate +
          "/" +
          endDate,
          List.class
        );
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
    //通过合作关系网络获得推荐合作者候选
    List<String> candidate_cooperation = getResearchersOfPartners(researchId);

    if (candidate_domain != null) {
      candidate_domain.addAll(candidate_cooperation);
      //取并集
      List<String> candidate = candidate_domain
        .stream()
        .distinct()
        .collect(Collectors.toList());
      for (String partner : candidate) {
        //计算三个评估方向的得分加权和
        //TODO 权值待确定
        Double score =
          countByImpact(partner) + //影响力数值评分
          countByDomain(researchId, partner) + //领域契合度数值评分
          countByCooperation(researchId, partner); //合作程度量化数值评分
        potentialPartnerNet.put(partner, score);
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
    }
    return new ResponseEntity<>(potentialPartnerNet, HttpStatus.OK);
  }

  /**
   * 计算作者之间的关联程度
   * @param researchId 作者id
   * @param partnerId 合作者id
   * @return 量化数值
   */
  private Double countByCooperation(String researchId, String partnerId) {
    double score = 0.0;
    Calendar cal = Calendar.getInstance();
    int nowYear = cal.get(Calendar.YEAR) + 1;
    try {
      //通过R_ScoreData中的数据来计算两作者之间的合作程度量化数值
      R_ScoreData r_scoreData = restTemplate.getForObject(
        researcherServiceAddress + "/R-score/" + researchId + "/" + partnerId,
        R_ScoreData.class
      );
      if (r_scoreData != null) {
        for (Integer year : r_scoreData.getMapOfYearAndCoNumber().keySet()) {
          double co_num = r_scoreData.getMapOfYearAndCoNumber().get(year);
          double sum1 = r_scoreData.getMapOfYearAndSum1().get(year);
          double sum2 = r_scoreData.getMapOfYearAndSum2().get(year);
          //计算公式为e的（当前年份-合作发生年份）分之一次方*(当前年份合作发表论文数/（当前年份两者发表论文总数）)
          score +=
            Math.pow(Math.E, (double) 1 / (nowYear - year)) *
            (co_num / (sum1 + sum2));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    score = Double.parseDouble(String.format("%.2f", score));
    return score;
  }

  /**
   * 计算研究者领域覆盖程度
   * @param researchId 作者id
   * @param partner 合作者id
   * @return 量化数值
   */
  private Double countByDomain(String researchId, String partner) {
    Double score = 0.0;
    try {
      score =
        restTemplate.getForObject(
          taskDomainAnalysisServiceAddress +
          "/domain-coverage/" +
          researchId +
          "/" +
          partner,
          Double.class
        );
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
      impact =
        restTemplate.getForObject(
          taskImpactAnalysisServiceAddress + "/impact/" + partnerId,
          Double.class
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return impact;
  }

  /**
   * 获得与当前作者有过关联的作者列表
   * @param researchId 作者id
   * @return 作者id列表
   */
  private List<String> getResearchersOfPartners(String researchId) {
    List<String> partners = new ArrayList<>();
    try {
      partners =
        (List<String>) restTemplate.getForObject(
          researcherServiceAddress + "/past-partners/" + researchId,
          List.class
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return partners;
  }

  /**
   * 根据作者id获得具有相同研究方向的作者id列表
   * @param researchId 作者id
   * @return 作者id列表
   */
  private List<String> getResearchersOfSimilarDomainById(String researchId) {
    List<String> researcherOfSimilarDomain = new ArrayList<>();
    try {
      researcherOfSimilarDomain =
        (List<String>) restTemplate.getForObject(
          researcherServiceAddress +
          "/researchers-similar-domain/" +
          researchId,
          List.class
        );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return researcherOfSimilarDomain;
  }
}
