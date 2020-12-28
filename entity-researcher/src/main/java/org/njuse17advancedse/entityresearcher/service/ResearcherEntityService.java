package org.njuse17advancedse.entityresearcher.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.dto.ISearchResult;
import org.njuse17advancedse.entityresearcher.repository.ResearcherRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ResearcherEntityService {
  private final ResearcherRepository researcherRepository;

  public ResearcherEntityService(ResearcherRepository researcherRepository) {
    this.researcherRepository = researcherRepository;
  }

  /**
   * 根据id获得作者
   * @param id 作者id
   * @return 作者实体
   */
  @Async
  public CompletableFuture<IResearcher> getResearcherById(String id) {
    return CompletableFuture.completedFuture(
      researcherRepository.getResearcherById(id)
    );
  }

  /**
   * 获得某作者的论文
   * @param rid 作者id
   * @param start 开始时间
   * @param end 结束时间
   * @return 论文id列表
   */
  public List<String> getPapersByRid(String rid, int start, int end) {
    return researcherRepository.findPapers(rid, start, end);
  }

  /**
   * 根据作者id获得领域
   * @param rid 作者id
   * @param start 开始时间
   * @param end 结束时间
   * @return 领域列表
   */
  public List<String> getDomainByRid(String rid, int start, int end) {
    return researcherRepository.findDomains(rid, start, end);
  }

  /**
   * 根据作者id获得作者机构信息
   * @param rid 作者id
   * @param start 开始时间
   * @param end 结束时间
   * @return 作者机构
   */
  public List<String> getAffiliationByRid(String rid, int start, int end) {
    return researcherRepository.findAffiliations(rid, start, end);
  }

  /**
   * 根据id获得作者简要信息
   * @param id 作者id
   * @return 作者简要实体
   */
  @Async
  public CompletableFuture<IResearcherBasic> getResearcherBasicById(String id) {
    return CompletableFuture.completedFuture(
      researcherRepository.getResearcherBasic(id)
    );
  }

  /**
   * 根据条件搜索作者
   * @param keyword 关键词
   * @param page 页码
   * @return 搜索总条数和列表
   */
  public ISearchResult searchByCond(String keyword, int page) {
    return researcherRepository.searchByCond(keyword, page);
  }

  /**
   * 判断学者是否存在
   * @param id 学者id
   * @return true or false
   */
  public boolean containResearcher(String id) {
    return researcherRepository.containResearcher(id);
  }
}
