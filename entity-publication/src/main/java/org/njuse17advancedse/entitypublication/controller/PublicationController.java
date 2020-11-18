package org.njuse17advancedse.entitypublication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;
import org.njuse17advancedse.entitypublication.problem.BadRequestProblem;
import org.njuse17advancedse.entitypublication.problem.PublicationNotFoundProblem;
import org.njuse17advancedse.entitypublication.service.PublicationEntityService;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "出版物" })
@RequestMapping("/publications")
@RestController
public class PublicationController {
  private final PublicationEntityService publicationEntityService;

  @ApiOperation("根据查询条件查询满足条件的出版物 id")
  @GetMapping("")
  public ISearchResult getPublicationsByCond(
    @ApiParam(value = "查询关键词") @RequestParam String keyword,
    @ApiParam("开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam("结束年份，形如'2020'") @RequestParam(
      required = false
    ) String end,
    @ApiParam(value = "页数") @RequestParam int page
  ) {
    int startDate = 0;
    int endDate = Integer.MAX_VALUE;
    if (start != null) {
      startDate = checkArgument(start);
    }
    if (end != null) {
      endDate = checkArgument(end);
    }
    if (startDate < 0 || startDate > endDate) {
      throw new BadRequestProblem(startDate + "," + endDate, "Date error");
    }
    if (page <= 0) {
      throw new BadRequestProblem(page + "", "page should >= 1");
    }
    return publicationEntityService.searchByCond(
      keyword,
      startDate,
      endDate,
      page
    );
  }

  @ApiOperation("根据 id 获取出版物详细信息")
  @GetMapping("/{id}")
  public IPublication getPublicationById(
    @ApiParam(value = "出版物 id") @PathVariable String id
  ) {
    IPublication iPublication = publicationEntityService.getPublicationById(id);
    if (iPublication.getName() == null) {
      throw new PublicationNotFoundProblem(id);
    }
    return iPublication;
  }

  @ApiOperation("根据 id 获取出版物简略信息")
  @GetMapping("/{id}/basic-info")
  public IPublicationBasic getPublicationBasicInfoById(
    @ApiParam(value = "出版物 id") @PathVariable String id
  ) {
    IPublicationBasic iPublicationBasic = publicationEntityService.getIPublicationBasic(
      id
    );
    if (iPublicationBasic.getName() == null) {
      throw new PublicationNotFoundProblem(id);
    }
    return iPublicationBasic;
  }

  @ApiOperation("根据 id （和时间范围）获取出版物包含的论文 id")
  @GetMapping("/{id}/papers")
  public List<String> getPapersByIdOrTimeRange(
    @ApiParam(value = "出版物 id") @PathVariable String id,
    @ApiParam("开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam("结束年份，形如'2020'") @RequestParam(required = false) String end
  ) {
    int startDate = 0;
    int endDate = Integer.MAX_VALUE;
    if (start != null) {
      startDate = checkArgument(start);
    }
    if (end != null) {
      endDate = checkArgument(end);
    }
    if (startDate < 0 || startDate > endDate) {
      throw new BadRequestProblem(startDate + "," + endDate, "Date error");
    }
    List<String> papers = publicationEntityService.getPapersByIdOrTimeRange(
      id,
      startDate,
      endDate
    );
    if (papers.size() == 1) {
      if (papers.get(0).equals("no such publication")) {
        throw new PublicationNotFoundProblem(id);
      }
    }
    return papers;
  }

  /**
   * 检查参数是否合法
   * @param arg 参数
   * @return 参数值
   */
  private int checkArgument(String arg) {
    int value;
    try {
      value = Integer.parseInt(arg);
      return value;
    } catch (NumberFormatException e) {
      throw new BadRequestProblem(arg, "can not parseInt");
    }
  }

  public PublicationController(
    PublicationEntityService publicationEntityService
  ) {
    this.publicationEntityService = publicationEntityService;
  }
}
