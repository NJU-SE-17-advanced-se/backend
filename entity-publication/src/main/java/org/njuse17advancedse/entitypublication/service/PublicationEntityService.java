package org.njuse17advancedse.entitypublication.service;

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
    return publicationRepository.findPublication(id);
  }

  /**
   * 根据id和时间段获得出版的论文
   * @param id 出版物id
   * @param start 开始时间
   * @param end 结束时间
   * @return 论文id列表
   */
  public List<String> getPapersByIdOrTimeRange(String id, int start, int end) {
    return publicationRepository.getPapers(id, start, end);
  }

  /**
   * 获得出版物简要信息
   * @param id id
   * @return 出版物简要信息实体
   */
  public IPublicationBasic getIPublicationBasic(String id) {
    return publicationRepository.findPublicationBasic(id);
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
    int start,
    int end,
    int page
  ) {
    return publicationRepository.searchByCond(keyword, start, end, page);
  }
}
