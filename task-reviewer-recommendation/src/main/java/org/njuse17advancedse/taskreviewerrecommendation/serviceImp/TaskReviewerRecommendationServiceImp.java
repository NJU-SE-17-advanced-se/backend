package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import java.util.*;
import org.njuse17advancedse.taskreviewerrecommendation.dto.*;
import org.njuse17advancedse.taskreviewerrecommendation.repository.PaperRepository;
import org.njuse17advancedse.taskreviewerrecommendation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 审稿人推荐服务实现
 * @author ycj
 * @date 2020/09/24
 */
@Service
public class TaskReviewerRecommendationServiceImp
  implements TaskReviewerRecommendationService {
  private final PaperRepository paperRepository;

  private final TaskImpactAnalysisService taskImpactAnalysisService;

  @Autowired
  public TaskReviewerRecommendationServiceImp(
    PaperRepository paperRepository,
    TaskImpactAnalysisService taskImpactAnalysisService
  ) {
    this.paperRepository = paperRepository;
    this.taskImpactAnalysisService = taskImpactAnalysisService;
  }

  @Override
  public List<String> getRecommendReviewer(IPaperUpload iPaperUpload) {
    List<String> researcherIds = new ArrayList<>();

    /* 第一步，获得作者曾今的合作者 */
    List<String> pastPartners = paperRepository.getPastPartners(
      iPaperUpload.getResearcherIds()
    );

    /* 第二步，获得引用文献的作者 */
    List<String> reviewersFromReferences = paperRepository.getResearcherIdsFromReferences(
      iPaperUpload.getReferenceIds(),
      pastPartners
    );

    /* 第三步，获得最近在投稿刊物发表过文章的作者 */
    List<String> reviewersFormPublication = paperRepository.getResearchersFromPublication(
      iPaperUpload.getJournal(),
      Integer.parseInt(iPaperUpload.getDate()),
      pastPartners
    );
    if (reviewersFormPublication.size() == 1) {
      if (reviewersFormPublication.get(0).equals("no such journal")) {
        return null;
      }
    }
    /* 第四步， 获得论文发表期间相同领域论文的作者列表*/
    List<String> reviewersFromSimilarDomain = paperRepository.getResearcherFromSimilarDomain(
      iPaperUpload.getDomainIds(),
      Integer.parseInt(iPaperUpload.getDate()),
      pastPartners
    );

    /* 第五步，选取结果 */
    for (String rev : reviewersFromSimilarDomain) {
      if (
        reviewersFormPublication.contains(rev) &&
        reviewersFromReferences.contains(rev)
      ) {
        researcherIds.add(rev);
      }
    }

    if (researcherIds.size() < 5) {
      for (String rev : reviewersFromSimilarDomain) {
        if (researcherIds.size() < 5 && !researcherIds.contains(rev)) {
          researcherIds.add(rev);
        }
      }
    }
    /* 第五步，将剩余作者按照影响力进行排序，取前5个作为推荐审稿人候选 */
    //researcherIds = sortResearchersByImpact(researcherIds);

    return researcherIds;
  }

  /**
   * 作者按照影响力进行排序
   * @param researcherIds 作者列表
   */
  private List<String> sortResearchersByImpact(List<String> researcherIds) {
    List<Integer> impacts = new ArrayList<>();
    try {
      //impacts = taskImpactAnalysisService.getImpactsByRids(researcherIds);
      //System.out.println(impacts);
      for (String researcherId : researcherIds) {
        impacts.add(
          taskImpactAnalysisService.getImpactByResearcherId(researcherId)
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    HashMap<String, Integer> map = new HashMap<>();
    for (int i = 0; i < researcherIds.size(); i++) {
      map.put(researcherIds.get(i), impacts.get(i));
    }
    researcherIds.sort((o1, o2) -> (map.get(o2) - map.get(o1)));
    return researcherIds;
  }

  @Override
  public List<String> getNotRecommendReviewer(IPaperUpload iPaperUpload) {
    List<String> researcherIds = new ArrayList<>();
    List<String> partners; //合作作者列表

    //第一步，根据id获得与论文作者合作过的作者列表
    List<String> pastPartners = paperRepository.getPastPartners(
      iPaperUpload.getResearcherIds()
    );

    //第二步，根据id获得与论文作者同机构的作者列表
    List<String> partnersFromSameAffiliation = paperRepository.getPartnersByAffiliation(
      iPaperUpload.getResearcherIds()
    );

    HashSet<String> hashSet = new HashSet<>(pastPartners);
    hashSet.addAll(partnersFromSameAffiliation);

    partners = new ArrayList<>(hashSet);

    //第三步，获得合作者的研究领域并按照相关程度排序，取前五名作者
    List<String> researchersFromSimilarDomain = paperRepository.getResearcherFromSimilarDomain(
      iPaperUpload.getDomainIds(),
      Integer.parseInt(iPaperUpload.getDate()),
      null
    );
    for (String rid : researchersFromSimilarDomain) {
      if (partners.contains(rid) && researcherIds.size() < 5) {
        researcherIds.add(rid);
      }
    }

    return researcherIds;
  }
}
