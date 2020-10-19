package org.njuse17advancedse.apigateway.apps.entity;

import java.util.List;
import org.njuse17advancedse.apigateway.domains.dto.DDomain;
import org.njuse17advancedse.apigateway.domains.dto.DDomainBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-domain")
public interface DomainService {
  @GetMapping("/domains/{id}")
  // 根据ID获取某领域信息
  DDomain getDomainById(@PathVariable String id);

  @GetMapping("/domains/{id}/basic-info")
  // 根据ID获取某领域简略信息
  DDomainBasic getDomainBasicInfoById(@PathVariable String id);

  @GetMapping("/domains/{id}/papers")
  // 某领域下的论文
  List<String> getPapers(@PathVariable String id);

  @GetMapping("/domains/{id}/researchers")
  // 某领域下的学者
  List<String> getResearchers(@PathVariable String id);
}
