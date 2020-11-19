package org.njuse17advancedse.entityaffiliation.exception;

import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NotFoundProblem extends AbstractThrowableProblem {
  private static final URI TYPE = URI.create("https://example.org/not-found");

  public NotFoundProblem(String id) {
    super(
      TYPE,
      "Affiliation Not found",
      Status.NOT_FOUND,
      String.format("Affiliation '%s' not found", id)
    );
  }
}
