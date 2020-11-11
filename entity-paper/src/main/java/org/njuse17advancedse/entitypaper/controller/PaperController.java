package org.njuse17advancedse.entitypaper.controller;

import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/papers")
@RestController
public class PaperController {
  @Autowired
  PaperService service;

  @GetMapping("/{id}")
  // 根据 id 获取论文信息
  public IPaper getPaper(@PathVariable String id) {
    return service.getIPaper(id);
  }

  @GetMapping("")
  // 根据其他查询条件获取论文 id
  // 如果没有任何查询条件，返回全部论文 id
  // TODO: 分页
  public List<String> getPapers(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  ) {
    return service.getPapers(researcher, publication, date);
  }

  @GetMapping("/{id}/basic-info")
  // 根据 id 获取论文简略信息
  public IPaperBasic getPaperBasicInfo(@PathVariable String id) {
    return service.getPaperBasicInfo(id);
  }

  @GetMapping("/{id}/domains")
  // 获取论文所属领域 id
  public List<String> getDomains(@PathVariable String id) {
    return service.getDomains(id);
  }

  @GetMapping("/{id}/citations")
  public List<String> getCitations(@PathVariable String id) {
    return service.getCitations(id);
  }
}
