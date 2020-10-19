package org.njuse17advancedse.entitypublication.controller;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/publications")
@RestController
public class PublicationController {

  @GetMapping("/{id}")
  // 根据ID获取出版物
  public IPublication getPublicationById(@PathVariable String id) {
    return new IPublication(
      id,
      "测试出版物",
      "2020-10-18",
      1.32,
      new ArrayList<>()
    );
  }

  @GetMapping("")
  // 根据其他指标获取出版物
  // NOTE: 如果没有任何指标，返回的就是全部的出版物
  public List<String> getPublicationsByTimeRange(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/papers")
  // 根据ID（和时间范围）获取出版物包含的论文
  public List<String> getPapersByIdOrTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/basic-info")
  // 根据ID获取出版物基本信息
  public IPublicationBasic getPublicationBasicInfoById(
    @PathVariable String id
  ) {
    return new IPublicationBasic(id, "测试出版物", "2020-10-18");
  }
}
