package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.apigateway.apps.service.ResearcherService;
import org.njuse17advancedse.apigateway.interfaces.dto.researcher.*;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "学者" })
@RequestMapping("/researcher")
@RestController
public class ResearcherController {
  private ResearcherService researcherService;

  @ApiOperation(
    value = "接口 1.1：查看某学者某一时间段所在机构",
    notes = "需求 3.1：能够识别同一研究者在不同时间的单位情况"
  )
  @GetMapping("/{id}/affiliations")
  public List<IAffiliation> getAffiliationsByTimeRange(
    @ApiParam(value = "学者id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    List<IAffiliation> res = new ArrayList<>();
    res.add(
      new IAffiliation("1", "测试机构1", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IAffiliation("2", "测试机构2", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.2：查看某学者某一时间段的研究方向",
    notes = "需求 4.1：能够识别研究者的兴趣\n\n" +
    "需求 4.2：能够识别研究者在不同阶段的研究兴趣"
  )
  @GetMapping("/{id}/domains")
  public List<IDomain> getDomainsByTimeRange(
    @ApiParam(value = "学者id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    List<IDomain> res = new ArrayList<>();
    res.add(
      new IDomain("1", "测试领域1", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IDomain("2", "测试领域2", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.3：预测某学者未来的研究方向",
    notes = "需求 4.3：能够初步预测研究者的研究兴趣"
  )
  @GetMapping("/{id}/future/domains")
  public List<IDomain> getFutureDomains(
    @ApiParam(value = "学者id") @PathVariable String id
  ) {
    List<IDomain> res = new ArrayList<>();
    res.add(
      new IDomain("3", "测试领域3", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IDomain("4", "测试领域4", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.4：查看某学者某一时间段的合作关系",
    notes = "需求 5.1：能够识别研究者存在的合作关系，形成社会网络"
  )
  @GetMapping("/{id}/partnership")
  public List<IResearcher> getPartnershipByTimeRange(
    @ApiParam(value = "学者id") @PathVariable String id
  ) {
    List<IResearcher> res = new ArrayList<>();
    res.add(
      new IResearcher("1", "测试学者1", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IResearcher("2", "测试学者2", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.5：预测某学者未来的合作关系",
    notes = "需求 5.2：能够初步预测研究者之间的合作走向"
  )
  @GetMapping("/{id}/future/partnership")
  public List<IResearcher> getFuturePartnership(
    @ApiParam(value = "学者id") @PathVariable String id,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    List<IResearcher> res = new ArrayList<>();
    res.add(
      new IResearcher("3", "测试学者3", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IResearcher("4", "测试学者4", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.6：查看某学者的论文引用情况",
    notes = "需求 7.2：研究者引用其他研究者（的论文）"
  )
  @GetMapping("/{id}/references")
  public List<IPaper> getReferences(
    @ApiParam(value = "学者id") @PathVariable String id
  ) {
    List<IPaper> res = new ArrayList<>();
    res.add(
      new IPaper(
        "1",
        "测试论文1",
        "测试论文1的摘要",
        "google.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    res.add(
      new IPaper(
        "2",
        "测试论文2",
        "测试论文2的摘要",
        "google.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.7：查看某学者的论文被引情况",
    notes = "需求 7.2：研究者被其他研究者引用情况"
  )
  @GetMapping("/{id}/citations")
  public List<IPaper> getCitations(
    @ApiParam(value = "学者id") @PathVariable String id
  ) {
    List<IPaper> res = new ArrayList<>();
    res.add(
      new IPaper(
        "3",
        "测试论文3",
        "测试论文3的摘要",
        "google.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    res.add(
      new IPaper(
        "4",
        "测试论文4",
        "测试论文4的摘要",
        "google.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.8：查看某学者的影响力",
    notes = "需求 7.3：评价研究者影响力"
  )
  @GetMapping("/{id}/impact")
  public IImpact getImpact(@ApiParam(value = "学者id") @PathVariable String id)
    throws Exception {
    String criteria = "H-index";
    double impact = this.researcherService.getImpact(id);
    return new IImpact(impact, criteria);
  }

  public ResearcherController(ResearcherService researcherService) {
    this.researcherService = researcherService;
  }
}
