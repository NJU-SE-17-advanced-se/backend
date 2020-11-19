package org.njuse17advancedse.entitypaper.exception;

import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NotFoundProblem extends AbstractThrowableProblem {
  private static final URI TYPE = URI.create("https://example.org/not-found");

  public NotFoundProblem(String id) {
    super(
      TYPE,
      "Paper Not found",
      Status.NOT_FOUND,
      String.format("Paper '%s' not found", id)
    );
  }
}
