package org.njuse17advancedse.entitypublication.service;

import com.sun.istack.Nullable;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;
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
      iPublication = publicationRepository.findPublication(id);
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
      papers = publicationRepository.getPapers(id, start, end);
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
      iPublicationBasic = publicationRepository.findPublicationBasic(id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return iPublicationBasic;
  }

  /**
   * 根据条件搜索结果
   * @param keyword 关键词
   * @param start 开始时间
   * @param end 结束时间
   * @param page 页码
   * @return 搜索结果，包含count和列表
   */
  public ISearchResult searchByCond(
    String keyword,
    @Nullable String start,
    @Nullable String end,
    int page
  ) {
    ISearchResult iSearchResult = new ISearchResult();
    try {
      iSearchResult =
        publicationRepository.searchByCond(keyword, start, end, page);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return iSearchResult;
  }
}
