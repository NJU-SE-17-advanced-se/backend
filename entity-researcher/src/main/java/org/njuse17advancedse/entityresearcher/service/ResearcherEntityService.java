package org.njuse17advancedse.entityresearcher.service;

import com.sun.istack.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.entity.JpaAffiliation;
import org.njuse17advancedse.entityresearcher.entity.JpaDomain;
import org.njuse17advancedse.entityresearcher.entity.JpaPaper;
import org.njuse17advancedse.entityresearcher.entity.JpaResearcher;
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
      JpaResearcher jpaResearcher = researcherRepository.findResearcherById(id);
      if (jpaResearcher.getPapers() != null) {
        List<String> papers = jpaResearcher
          .getPapers()
          .stream()
          .map(JpaPaper::getId)
          .collect(Collectors.toList());
        List<String> domains = getDomains(jpaResearcher);
        iResearcher.setId(jpaResearcher.getId());
        iResearcher.setName(jpaResearcher.getName());
        if (jpaResearcher.getAffiliations() != null) {
          iResearcher.setAffiliation(
            jpaResearcher
              .getAffiliations()
              .stream()
              .map(JpaAffiliation::getId)
              .collect(Collectors.toList())
          );
        }
        iResearcher.setPapers(papers);
        iResearcher.setDomains(domains);
      }
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
      JpaResearcher jpaResearcher = researcherRepository.findResearcherById(
        rid
      );
      if (jpaResearcher != null) {
        List<JpaPaper> paperList = jpaResearcher.getPapers();

        for (JpaPaper paper : paperList) {
          if (isInTime(start, end, paper)) {
            papers.add(paper.getId());
          }
        }
      }
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
      JpaResearcher jpaResearcher = researcherRepository.findResearcherById(
        rid
      );
      if (jpaResearcher != null) {
        List<JpaPaper> paperList = jpaResearcher.getPapers();
        for (JpaPaper paper : paperList) {
          if (isInTime(start, end, paper)) {
            domains.add(
              paper
                .getDomains()
                .stream()
                .map(JpaDomain::getId)
                .collect(Collectors.joining())
            );
          }
        }
      }
      HashSet<String> hashSet = new HashSet<>(domains);
      domains.clear();
      domains.addAll(hashSet);
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
      JpaResearcher jpaResearcher = researcherRepository.findResearcherById(id);
      if (jpaResearcher.getPapers() != null) {
        List<JpaPaper> jpaPapers = jpaResearcher.getPapers();
        List<String> papers = new ArrayList<>();
        jpaPapers.sort((o1, o2) -> o2.getCitation() - o1.getCitation());
        if (jpaPapers.size() <= 5) {
          for (JpaPaper jpaPaper : jpaPapers) {
            papers.add(jpaPaper.getId());
          }
        } else {
          for (int i = 0; i < 5; i++) {
            papers.add(jpaPapers.get(i).getId());
          }
        }
        List<String> domains = getDomains(jpaResearcher);
        iResearcherBasic.setId(jpaResearcher.getId());
        iResearcherBasic.setName(jpaResearcher.getName());
        if (jpaResearcher.getAffiliations() != null) {
          iResearcherBasic.setAffiliation(
            jpaResearcher
              .getAffiliations()
              .stream()
              .map(JpaAffiliation::getId)
              .collect(Collectors.toList())
          );
        }
        iResearcherBasic.setPapers(papers);
        iResearcherBasic.setDomains(domains);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return iResearcherBasic;
  }

  /**
   * 获得领域列表
   * @param jpaResearcher 作者实体
   * @return 领域id列表
   */
  private List<String> getDomains(JpaResearcher jpaResearcher) {
    List<String> domains = new ArrayList<>();
    for (JpaPaper jpaPaper : jpaResearcher.getPapers()) {
      domains.addAll(
        jpaPaper
          .getDomains()
          .stream()
          .map(JpaDomain::getId)
          .collect(Collectors.toList())
      );
    }
    HashSet<String> hashSet = new HashSet<>(domains);
    domains.clear();
    domains.addAll(hashSet);
    return domains;
  }

  /**
   * 判断是否在时间内
   * @param start 开始时间
   * @param end 结束时间
   * @param paper 论文实体
   * @return true or false
   */
  private boolean isInTime(
    @Nullable String start,
    @Nullable String end,
    JpaPaper paper
  ) {
    boolean betweenDate = true;
    if (start != null) {
      if (
        Integer.parseInt(paper.getPublicationDate()) < Integer.parseInt(start)
      ) {
        betweenDate = false;
      }
    }
    if (end != null) {
      if (
        Integer.parseInt(paper.getPublicationDate()) < Integer.parseInt(end)
      ) {
        betweenDate = false;
      }
    }
    return betweenDate;
  }
}
