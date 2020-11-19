package org.njuse17advancedse.taskpartnershipanalysis.problem;

import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResearcherNotFoundProblem extends AbstractThrowableProblem {
  private static final URI TYPE = URI.create("https://example.org/not-found");

  public ResearcherNotFoundProblem(String id) {
    super(
      TYPE,
      "Not found",
      Status.NOT_FOUND,
      String.format("Researcher '%s' not found", id)
    );
  }
}
