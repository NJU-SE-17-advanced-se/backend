package org.njuse17advancedse.taskdomainprediction.service;

import java.util.HashMap;
import java.util.List;
import org.njuse17advancedse.taskdomainprediction.repository.ResearcherRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskDomainPredictionServiceImp
  implements TaskDomainPredictionService {
  private final ResearcherRepository researcherRepository;

  public TaskDomainPredictionServiceImp(
    ResearcherRepository researcherRepository
  ) {
    this.researcherRepository = researcherRepository;
  }

  @Override
  public List<String> getFutureDomains(String rid) {
    List<String> predictDomains;

    /* 第一步，获得作者最近三年的研究领域 */
    List<String> pastDomains = researcherRepository.getPastDomains(rid);

    /* 第二步，计算每个研究领域的影响力 */
    HashMap<String, Double> hashMap = new HashMap<>();
    for (String domain : pastDomains) {
      hashMap.put(domain, researcherRepository.getDomainImpact(domain));
    }

    /* 按照研究领域的热门程度进行排序，取前五个 */
    pastDomains.sort((o1, o2) -> (int) (hashMap.get(o2) - hashMap.get(o1)));
    if (pastDomains.size() > 5) {
      predictDomains = pastDomains.subList(0, 5);
    } else {
      predictDomains = pastDomains;
    }
    return predictDomains;
  }

  @Override
  public boolean containResearcher(String id) {
    return researcherRepository.containResearcher(id);
  }
}
