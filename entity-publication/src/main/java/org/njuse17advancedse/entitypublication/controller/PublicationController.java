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

  @GetMapping("/{id}/papers")
  // 根据ID获取出版物包含的论文
  public List<String> getPublicationPapersById(@PathVariable String id) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/papers")
  // 根据ID和时间范围获取出版物包含的论文
  public List<String> getPublicationPapersByIdAndTimeRange(
    @RequestParam String start,
    @RequestParam String end
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

  @GetMapping("/papers")
  // 根据时间范围获取出版物
  public List<String> getPublicationByTimeRange(
    @RequestParam String start,
    @RequestParam String end
  ) {
    return new ArrayList<>();
  }
}
