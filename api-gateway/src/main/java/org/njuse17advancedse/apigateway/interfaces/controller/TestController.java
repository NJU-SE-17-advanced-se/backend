package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.njuse17advancedse.apigateway.infra.exception.TestException;
import org.njuse17advancedse.apigateway.interfaces.dto.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "测试" })
@RestController
@RequestMapping("/test")
public class TestController {

  @ApiOperation("增加")
  @PostMapping("/")
  public ResponseEntity<?> add(@RequestBody Test test) throws Exception {
    System.out.println(test);
    if (test == null || test.getId() == null) {
      System.out.println(test);
      throw new TestException();
    }
    return new ResponseEntity<>("succ", HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public boolean delete(@PathVariable("id") String id) {
    return true;
  }

  @PutMapping("/{id}")
  public boolean update(@PathVariable("id") String id, @RequestBody Test test) {
    return true;
  }

  @GetMapping("/{id}")
  public String get(@PathVariable("id") String id) {
    return id;
  }
}
