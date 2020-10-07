package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.apigateway.apps.service.PaperService;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IImpact;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IResearcher;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.req.IPaperUpload;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文" })
@RequestMapping("/paper")
@RestController
public class PaperController {
  private final PaperService paperService;

  @ApiOperation(
    value = "接口 2.1：查看某论文引用情况",
    notes = "需求 7.1：论文引用其它论文"
  )
  @GetMapping("/{id}/references")
  public List<IPaper> getReferences(
    @ApiParam(value = "论文id") @PathVariable String id
  )
    throws Exception {
    return paperService.getReferences(id);
  }

  @ApiOperation(
    value = "接口 2.2：查看某论文被引情况",
    notes = "需求 7.1：论文被其它论文引用情况"
  )
  @GetMapping("/{id}/citations")
  public List<IPaper> getCitations(
    @ApiParam(value = "学者id") @PathVariable String id
  )
    throws Exception {
    return paperService.getCitations(id);
  }

  @ApiOperation(
    value = "接口 2.3：查看某论文推荐的审稿人",
    notes = "需求 6.1：提交审稿时，能够自动推荐相关审稿人"
  )
  @PostMapping("/recommend-reviewers")
  public List<IResearcher> getRecommendedReviewers(
    @ApiParam(value = "论文内容") @RequestBody IPaperUpload paper
  ) {
    List<IResearcher> res = new ArrayList<>();
    res.add(
      new IResearcher("5", "测试学者5", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IResearcher("6", "测试学者6", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 2.4：查看某论文不推荐的审稿人",
    notes = "需求 6.2：提交审稿时，能够自动屏蔽相关审稿人"
  )
  @PostMapping("/not-recommend-reviewers")
  public List<IResearcher> getNotRecommendedReviewers(
    @ApiParam(value = "论文内容") @RequestBody IPaperUpload paper
  ) {
    List<IResearcher> res = new ArrayList<>();
    res.add(
      new IResearcher("7", "测试学者8", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IResearcher("8", "测试学者8", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  @ApiOperation(
    value = "接口 2.5：查看某论文的影响力",
    notes = "需求 7.3：评价研究影响力"
  )
  @GetMapping("/{id}/impact")
  public IImpact getImpact(@ApiParam(value = "论文id") @PathVariable String id)
    throws Exception {
    String criteria = "custom";
    double impact = this.paperService.getImpact(id, criteria);
    return new IImpact(impact, criteria);
  }

  public PaperController(PaperService paperService) {
    this.paperService = paperService;
  }
}
