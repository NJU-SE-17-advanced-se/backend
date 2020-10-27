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
  // 根据 id 获取出版物
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
  // 根据其他查询条件获取出版物 id
  // NOTE: 如果没有任何查询条件，返回的就是全部出版物 id
  public List<String> getPublicationsByTimeRange(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/papers")
  // 根据 id （和时间范围）获取出版物包含的论文 id
  public List<String> getPapersByIdOrTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/basic-info")
  // 根据 id 获取出版物基本信息
  public IPublicationBasic getPublicationBasicInfoById(
    @PathVariable String id
  ) {
    return new IPublicationBasic(id, "测试出版物", "2020-10-18");
  }
}
