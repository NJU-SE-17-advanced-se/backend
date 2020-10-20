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
  // 根据ID获取论文
  DPaper getPaper(@PathVariable String id);

  @GetMapping("/papers")
  // 根据其他指标获取论文
  // 如果都没填，返回全部论文
  List<String> getPapers(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  );

  @GetMapping("/papers/basic-info/{id}")
  // 根据指标获取论文简略信息
  DPaperBasic getPaperBasicInfo(@PathVariable String id);

  @GetMapping("/papers/basic-info")
  // 根据指标获取论文简略信息
  // 如果都没填，返回全部论文的简略信息
  List<String> getPapersBasicInfo(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  );

  @GetMapping("/papers/{id}/domains")
  // 获取论文所属领域
  List<String> getDomains(@PathVariable String id);
}
