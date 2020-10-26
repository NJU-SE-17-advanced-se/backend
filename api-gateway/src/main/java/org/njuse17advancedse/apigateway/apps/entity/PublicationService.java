package org.njuse17advancedse.apigateway.apps.entity;

import java.util.List;
import org.njuse17advancedse.apigateway.domains.dto.DPublication;
import org.njuse17advancedse.apigateway.domains.dto.DPublicationBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("entity-publication")
public interface PublicationService {
  @GetMapping("/publications/{id}")
  // 根据ID获取出版物
  DPublication getPublicationById(@PathVariable String id);

  @GetMapping("/publications")
  // 根据其他指标获取出版物
  // NOTE: 如果没有任何指标，返回的就是全部的出版物
  List<String> getPublicationsByTimeRange(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  @GetMapping("/publications/{id}/papers")
  // 根据ID（和时间范围）获取出版物包含的论文
  List<String> getPublicationPapersById(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  @GetMapping("/publications/{id}/papers")
  // 根据ID（和时间范围）获取出版物包含的论文
  List<String> getPapersByIdOrTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  @GetMapping("/publications/{id}/basic-info")
  // 根据ID获取出版物基本信息
  DPublicationBasic getPublicationBasicInfoById(@PathVariable String id);
}
