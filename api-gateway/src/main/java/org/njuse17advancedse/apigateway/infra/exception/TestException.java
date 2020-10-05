package org.njuse17advancedse.apigateway.infra.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "测试")
@NoArgsConstructor
public class TestException extends Exception {}
