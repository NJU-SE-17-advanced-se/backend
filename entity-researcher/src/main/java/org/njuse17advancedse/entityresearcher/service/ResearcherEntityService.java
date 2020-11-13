package org.njuse17advancedse.entityresearcher.service;

import com.sun.istack.Nullable;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.entity.JpaPaper;
import org.njuse17advancedse.entityresearcher.repository.ResearcherRepository;
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
  public IResearcher getResearcherById(String id) {
    IResearcher iResearcher = new IResearcher();
    try {
      iResearcher = researcherRepository.getResearcherById(id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return iResearcher;
  }

  /**
   * 获得某作者的论文
   * @param rid 作者id
   * @param start 开始时间
   * @param end 结束时间
   * @return 论文id列表
   */
  public List<String> getPapersByRid(
    String rid,
    @Nullable String start,
    @Nullable String end
  ) {
    List<String> papers = new ArrayList<>();
    try {
      papers = researcherRepository.findPapers(rid, start, end);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return papers;
  }

  /**
   * 根据作者id获得领域
   * @param rid 作者id
   * @param start 开始时间
   * @param end 结束时间
   * @return 领域列表
   */
  public List<String> getDomainByRid(
    String rid,
    @Nullable String start,
    @Nullable String end
  ) {
    List<String> domains = new ArrayList<>();
    try {
      domains = researcherRepository.findDomains(rid, start, end);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return domains;
  }

  /**
   * 根据作者id获得作者机构信息
   * @param rid 作者id
   * @param start 开始时间
   * @param end 结束时间
   * @return 作者机构
   */
  public List<String> getAffiliationByRid(
    String rid,
    @Nullable String start,
    @Nullable String end
  ) {
    List<String> affiliations = new ArrayList<>();
    try {
      affiliations = researcherRepository.findById(rid, start, end);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return affiliations;
  }

  /**
   * 根据id获得作者简要信息
   * @param id 作者id
   * @return 作者简要实体
   */
  public IResearcherBasic getResearcherBasicById(String id) {
    IResearcherBasic iResearcherBasic = new IResearcherBasic();
    try {
      iResearcherBasic = researcherRepository.getResearcherBasic(id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return iResearcherBasic;
  }
}
