package org.njuse17advancedse.entitypaper.exception;

import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestProblem extends AbstractThrowableProblem {
  private static final URI TYPE = URI.create(
    "https://example.org/illegal-argument"
  );

  public BadRequestProblem(String arg, String reason) {
    super(
      TYPE,
      "Bad Request",
      Status.BAD_REQUEST,
      String.format("Argument '%s' illegal," + reason, arg)
    );
  }
}
