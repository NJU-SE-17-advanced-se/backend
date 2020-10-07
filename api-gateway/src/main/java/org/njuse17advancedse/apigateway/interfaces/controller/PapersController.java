package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.apigateway.apps.service.PaperService;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IImpact;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IPaper;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文扩展" })
@RequestMapping("/papers")
@RestController
public class PapersController {
  private final PaperService paperService;

  @ApiOperation(
    value = "接口 2.1.2：查看某些论文引用情况",
    notes = "接口 2.1 的附属版本"
  )
  @GetMapping("/{ids}/references")
  public Map<String, List<IPaper>> getReferences(
    @ApiParam(value = "论文id的列表") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, List<IPaper>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, paperService.getReferences(id));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 2.2.2：查看某些论文被引情况",
    notes = "接口 2.2 的附属版本"
  )
  @GetMapping("/{ids}/citations")
  public Map<String, List<IPaper>> getCitations(
    @ApiParam(value = "论文id的列表") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, List<IPaper>> res = new HashMap<>();
    for (String id : ids) {
      res.put(id, paperService.getCitations(id));
    }
    return res;
  }

  @ApiOperation(
    value = "接口 2.5.2：查看某些论文的影响力",
    notes = "接口 2.5 的附属版本"
  )
  @GetMapping("/{ids}/impact")
  public Map<String, IImpact> getImpact(
    @ApiParam(value = "论文id的列表") @PathVariable List<String> ids
  )
    throws Exception {
    Map<String, IImpact> res = new HashMap<>();
    for (String id : ids) {
      String criteria = "custom";
      double impact = this.paperService.getImpact(id);
      res.put(id, new IImpact(impact, criteria));
    }
    return res;
  }

  public PapersController(PaperService paperService) {
    this.paperService = paperService;
  }
}
