package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.apigateway.interfaces.dto.researcher.*;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "学者扩展" })
@RequestMapping("/researchers")
@RestController
public class ResearchersController {

  @ApiOperation(
    value = "接口 1.1.2：查看某些学者某一时间段所在机构",
    notes = "接口 1.1 的附属版本"
  )
  @GetMapping("/{ids}/affiliations")
  public Map<String, IAffiliation> getAffiliationsByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    Map<String, IAffiliation> res = new HashMap<>();
    res.put(
      "1",
      new IAffiliation("1", "测试机构1", new ArrayList<>(), new ArrayList<>())
    );
    res.put(
      "2",
      new IAffiliation("2", "测试机构2", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.2.2：查看某些学者某一时间段的研究方向",
    notes = "接口 1.2 的附属版本"
  )
  @GetMapping("/{ids}/domains")
  public Map<String, IDomain> getDomainsByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    Map<String, IDomain> res = new HashMap<>();
    res.put(
      "1",
      new IDomain("1", "测试领域1", new ArrayList<>(), new ArrayList<>())
    );
    res.put(
      "2",
      new IDomain("2", "测试领域2", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.3.2：预测某些学者未来的研究方向",
    notes = "接口 1.3 的附属版本"
  )
  @GetMapping("/{ids}/future/domains")
  public Map<String, IDomain> getFutureDomains(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    Map<String, IDomain> res = new HashMap<>();
    res.put(
      "3",
      new IDomain("3", "测试领域3", new ArrayList<>(), new ArrayList<>())
    );
    res.put(
      "4",
      new IDomain("4", "测试领域4", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.4.2：查看某些学者某一时间段的合作关系",
    notes = "接口 1.4 的附属版本"
  )
  @GetMapping("/{ids}/partnership")
  public Map<String, IResearcher> getPartnershipByTimeRange(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    Map<String, IResearcher> res = new HashMap<>();
    res.put(
      "1",
      new IResearcher("1", "测试学者1", new ArrayList<>(), new ArrayList<>())
    );
    res.put(
      "2",
      new IResearcher("2", "测试学者2", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.5.2：预测某些学者未来的合作关系",
    notes = "接口 1.5 的附属版本"
  )
  @GetMapping("/{ids}/future/partnership")
  public Map<String, IResearcher> getFuturePartnership(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids,
    @ApiParam(value = "开始日期，形如 '2020-09-21'") @RequestParam String start,
    @ApiParam(value = "结束日期，形如 '2020-09-21'") @RequestParam String end
  ) {
    Map<String, IResearcher> res = new HashMap<>();
    res.put(
      "3",
      new IResearcher("3", "测试学者3", new ArrayList<>(), new ArrayList<>())
    );
    res.put(
      "4",
      new IResearcher("4", "测试学者4", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 1.6.2：查看某些学者的论文引用情况",
    notes = "接口 1.6 的附属版本"
  )
  @GetMapping("/{ids}/references")
  public Map<String, IPaper> getReferences(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    Map<String, IPaper> res = new HashMap<>();
    res.put(
      "1",
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
    res.put(
      "2",
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
    value = "接口 1.7.2：查看某些学者的论文被引情况",
    notes = "接口 1.7 的附属版本"
  )
  @GetMapping("/{ids}/citations")
  public Map<String, IPaper> getCitations(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    Map<String, IPaper> res = new HashMap<>();
    res.put(
      "3",
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
    res.put(
      "4",
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
    value = "接口 1.8.2：查看某些学者的影响力",
    notes = "接口 1.8 的附属版本"
  )
  @GetMapping("/{ids}/impact")
  public Map<String, IImpact> getImpact(
    @ApiParam(value = "学者**们**的id") @PathVariable List<String> ids
  ) {
    Map<String, IImpact> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, new IImpact(6.666, "我不道啊"));
    }
    return res;
  }
}
