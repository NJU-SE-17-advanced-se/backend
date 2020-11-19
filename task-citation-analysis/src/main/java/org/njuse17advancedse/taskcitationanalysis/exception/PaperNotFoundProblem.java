package org.njuse17advancedse.taskcitationanalysis.exception;

import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class PaperNotFoundProblem extends AbstractThrowableProblem {
  private static final URI TYPE = URI.create("https://example.org/not-found");

  public PaperNotFoundProblem(String id) {
    super(
      TYPE,
      "Paper Not found",
      Status.NOT_FOUND,
      String.format("Paper '%s' not found", id)
    );
  }
}
