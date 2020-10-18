package org.njuse17advancedse.entitypaper.controller;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/papers")
@RestController
public class PaperController {

  @GetMapping("/{id}")
  // 根据ID获取论文
  public IPaper getPaper(@PathVariable String id) {
    return new IPaper(
      id,
      "测试论文" + id,
      "测试摘要" + id,
      "测试期刊" + id,
      "2020",
      "google.com",
      new ArrayList<>(),
      new ArrayList<>(),
      new ArrayList<>()
    );
  }

  @GetMapping("")
  // 根据其他指标获取论文
  // 如果都没填，返回全部论文
  // TODO: 分页
  public List<String> getPapers(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/basic-info/{id}")
  // 根据指标获取论文简略信息
  public IPaperBasic getPaperBasicInfo(@PathVariable String id) {
    return new IPaperBasic(
      id,
      "测试论文" + id,
      "测试摘要" + id,
      "测试期刊" + id,
      "2020",
      new ArrayList<>()
    );
  }

  @GetMapping("/basic-info")
  // 根据指标获取论文简略信息
  // 如果都没填，返回全部论文的简略信息
  // TODO: 分页
  public List<String> getPapersBasicInfo(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  ) {
    return new ArrayList<>();
  }

  @GetMapping("/{id}/domains")
  // 获取论文所属领域
  public List<String> getDomains(@PathVariable String id) {
    return new ArrayList<>();
  }
}
