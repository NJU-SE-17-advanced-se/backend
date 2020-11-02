package org.njuse17advancedse.apigateway.apps.entity;

import java.util.List;
import org.njuse17advancedse.apigateway.domains.dto.DPaper;
import org.njuse17advancedse.apigateway.domains.dto.DPaperBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "entity-paper")
public interface PaperService {
  @GetMapping("/papers/{id}")
  // 根据 id 获取论文信息
  DPaper getPaper(@PathVariable String id);

  @GetMapping("/papers")
  // 根据其他查询条件获取论文 id
  // 如果没有任何查询条件，返回全部论文 id
  // TODO: 分页
  List<String> getPapers(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  );

  @GetMapping("/papers/{id}/basic-info")
  // 根据 id 获取论文简略信息
  DPaperBasic getPaperBasicInfo(@PathVariable String id);

  @GetMapping("/papers/{id}/domains")
  // 获取论文所属领域 id
  List<String> getDomains(@PathVariable String id);
}
