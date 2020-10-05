package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IImpact;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IResearcher;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文扩展" })
@RequestMapping("/papers")
@RestController
public class PapersController {

  @ApiOperation(
    value = "接口 2.1.2：查看某论文引用情况",
    notes = "接口 2.1 的附属版本"
  )
  @GetMapping("/{ids}/references")
  public Map<String, IPaper> getReferences(
    @ApiParam(value = "论文id") @PathVariable List<String> ids
  ) {
    Map<String, IPaper> res = new HashMap<>();
    res.put(
      "5",
      new IPaper(
        "5",
        "测试论文5",
        "测试论文5的摘要",
        "google.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    res.put(
      "6",
      new IPaper(
        "6",
        "测试论文6",
        "测试论文6的摘要",
        "google.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    return res;
  }

  @ApiOperation(
    value = "接口 2.2.2：查看某论文被引情况",
    notes = "接口 2.2 的附属版本"
  )
  @GetMapping("/{ids}/citations")
  public Map<String, IPaper> getCitations(
    @ApiParam(value = "学者id") @PathVariable List<String> ids
  ) {
    Map<String, IPaper> res = new HashMap<>();
    res.put(
      "7",
      new IPaper(
        "7",
        "测试论文7",
        "测试论文7的摘要",
        "google.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    res.put(
      "8",
      new IPaper(
        "8",
        "测试论文8",
        "测试论文8的摘要",
        "google.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>()
      )
    );
    return res;
  }

  @ApiOperation(
    value = "接口 2.3.2：查看某论文推荐的审稿人",
    notes = "接口 2.3 的附属版本"
  )
  @GetMapping("/{ids}/recommendation/reviewers")
  public Map<String, IResearcher> getRecommendedReviewers(
    @ApiParam(value = "论文id") @PathVariable List<String> ids
  ) {
    Map<String, IResearcher> res = new HashMap<>();
    res.put(
      "5",
      new IResearcher("5", "测试学者5", new ArrayList<>(), new ArrayList<>())
    );
    res.put(
      "6",
      new IResearcher("6", "测试学者6", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 2.4.2：查看某论文不推荐的审稿人",
    notes = "接口 2.4 的附属版本"
  )
  @GetMapping("/{ids}/non-recommendation/reviewers")
  public Map<String, IResearcher> getNotRecommendedReviewers(
    @ApiParam(value = "论文id") @PathVariable List<String> ids
  ) {
    Map<String, IResearcher> res = new HashMap<>();
    res.put(
      "7",
      new IResearcher("7", "测试学者8", new ArrayList<>(), new ArrayList<>())
    );
    res.put(
      "8",
      new IResearcher("8", "测试学者8", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 2.5.2：查看某论文的影响力",
    notes = "接口 2.5 的附属版本"
  )
  @GetMapping("/{ids}/impact")
  public Map<String, IImpact> getImpact(
    @ApiParam(value = "论文id") @PathVariable List<String> ids
  ) {
    Map<String, IImpact> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, new IImpact(7.777, "H-index"));
    }
    return res;
  }
}
