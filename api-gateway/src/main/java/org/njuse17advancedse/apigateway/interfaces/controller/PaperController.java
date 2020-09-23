package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IImpact;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IResearcher;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.req.IPaperUpload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文" })
@RestController
@RequestMapping("/paper")
public class PaperController {

  @ApiOperation(
    value = "接口 2.1：查看某论文引用情况",
    notes = "需求 7.1：论文引用其它论文"
  )
  @GetMapping("/{id}/references")
  public @ResponseBody List<IPaper> getReferences(
    @ApiParam(value = "论文id") @PathVariable String id
  ) {
    List<IPaper> res = new ArrayList<>();
    res.add(
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
    res.add(
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
    value = "接口 2.2：查看某论文被引情况",
    notes = "需求 7.1：论文被其它论文引用情况"
  )
  @GetMapping("/{id}/citations")
  public @ResponseBody List<IPaper> getCitations(
    @ApiParam(value = "学者id") @PathVariable String id
  ) {
    List<IPaper> res = new ArrayList<>();
    res.add(
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
    res.add(
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
    value = "接口 2.3：查看某论文推荐的审稿人",
    notes = "需求 6.1：提交审稿时，能够自动推荐相关审稿人"
  )
  @GetMapping("/{id}/recommendation/reviewers")
  public @ResponseBody List<IResearcher> getRecommendedReviewers(
    @ApiParam(value = "论文id") @PathVariable String id
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
  @GetMapping("/{id}/non-recommendation/reviewers")
  public @ResponseBody List<IResearcher> getNotRecommendedReviewers(
    @ApiParam(value = "论文id") @PathVariable String id
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
  public @ResponseBody IImpact getImpact(
    @ApiParam(value = "论文id") @PathVariable String id
  ) {
    return new IImpact(7.777, "H-index");
  }

  @ApiOperation(value = "上传新论文")
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public void savePaper(@RequestBody IPaperUpload paper) {
    System.out.println(paper);
  }
}
