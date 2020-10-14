package org.njuse17advancedse.taskpartnershipanalysis.serviceImp;

import java.util.*;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;
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
    IResearcherNet iResearcherNet = new IResearcherNet();
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
      int max_a = Collections.max(authorValues);
      int min_a = Collections.min(authorValues);
      int max_c = Collections.max(citationValues);
      int min_c = Collections.min(citationValues);
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
  public ResponseEntity<List<String>> getPotentialPartners(String researchId) {
    return null;
  }
}
