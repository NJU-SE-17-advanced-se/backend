package org.njuse17advancedse.entitypublication.service;

import com.sun.istack.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.entity.JpaPaper;
import org.njuse17advancedse.entitypublication.entity.JpaPublication;
import org.njuse17advancedse.entitypublication.repository.PublicationRepository;
import org.springframework.stereotype.Service;

@Service
public class PublicationEntityService {
  private final PublicationRepository publicationRepository;

  public PublicationEntityService(PublicationRepository publicationRepository) {
    this.publicationRepository = publicationRepository;
  }

  /**
   * 根据id获得出版物
   * @param id 出版物id
   * @return 出版物实体
   */
  public IPublication getPublicationById(String id) {
    IPublication iPublication = new IPublication();
    try {
      JpaPublication jpaPublication = publicationRepository.findPublicationById(
        id
      );
      iPublication.setId(jpaPublication.getId());
      iPublication.setName(jpaPublication.getName());
      iPublication.setImpact(jpaPublication.getImpact());
      iPublication.setPublicationDate(jpaPublication.getPublicationDate() + "");
      if (jpaPublication.getPapers() != null) {
        iPublication.setPapers(
          jpaPublication
            .getPapers()
            .stream()
            .map(JpaPaper::getId)
            .collect(Collectors.toList())
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return iPublication;
  }

  /**
   * 根据相关信息查找机构
   * @param name 名称
   * @param start 开始时间
   * @param end 结束时间
   * @return 机构id列表
   */
  public List<String> getPublications(
    @Nullable String name,
    @Nullable String start,
    @Nullable String end
  ) {
    return publicationRepository.findPublications(name, start, end);
  }

  /**
   * 根据id和时间段获得出版的论文
   * @param id 出版物id
   * @param start 开始时间
   * @param end 结束时间
   * @return 论文id列表
   */
  public List<String> getPapersByIdOrTimeRange(
    String id,
    @Nullable String start,
    @Nullable String end
  ) {
    List<String> papers = new ArrayList<>();
    try {
      int startTime = 0;
      int endTime = 9999;
      if (start != null) {
        startTime = Integer.parseInt(start);
      }
      if (end != null) {
        endTime = Integer.parseInt(end);
      }
      JpaPublication jpaPublication = publicationRepository.findPublicationById(
        id
      );
      List<JpaPaper> jpaPapers = jpaPublication.getPapers();
      if (jpaPapers != null) {
        for (JpaPaper jpaPaper : jpaPapers) {
          if (
            jpaPaper.getPublicationDate() >= startTime &&
            jpaPaper.getPublicationDate() <= endTime
          ) {
            papers.add(jpaPaper.getId());
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return papers;
  }

  /**
   * 获得出版物简要信息
   * @param id id
   * @return 出版物简要信息实体
   */
  public IPublicationBasic getIPublicationBasic(String id) {
    IPublicationBasic iPublicationBasic = new IPublicationBasic();
    try {
      JpaPublication jpaPublication = publicationRepository.findPublicationById(
        id
      );
      if (jpaPublication != null) {
        iPublicationBasic.setId(jpaPublication.getId());
        iPublicationBasic.setName(jpaPublication.getName());
        iPublicationBasic.setPublicationDate(
          jpaPublication.getPublicationDate() + ""
        );
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return iPublicationBasic;
  }
}
