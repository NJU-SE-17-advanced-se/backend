package org.njuse17advancedse.taskimpactanalysis.exception;

import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NotFoundProblem extends AbstractThrowableProblem {
  private static final URI TYPE = URI.create("https://example.org/not-found");

  public NotFoundProblem(String type, String id) {
    super(
      TYPE,
      "Paper Not found",
      Status.NOT_FOUND,
      String.format(type + " '%s' not found", id)
    );
  }
}
