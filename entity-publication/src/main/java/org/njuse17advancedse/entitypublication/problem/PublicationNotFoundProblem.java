package org.njuse17advancedse.entitypublication.problem;

import java.net.URI;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class PublicationNotFoundProblem extends AbstractThrowableProblem {
  private static final URI TYPE = URI.create("https://example.org/not-found");

  public PublicationNotFoundProblem(String id) {
    super(
      TYPE,
      "Not found",
      Status.NOT_FOUND,
      String.format("Publication '%s' not found", id)
    );
  }
}
