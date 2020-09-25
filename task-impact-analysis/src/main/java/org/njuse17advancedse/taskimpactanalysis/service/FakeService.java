package org.njuse17advancedse.taskimpactanalysis.service;

import org.njuse17advancedse.taskimpactanalysis.entity.Paper;
import org.njuse17advancedse.taskimpactanalysis.entity.Researcher;

public interface FakeService {
  Paper getPaperById(String id);

  Researcher getResearcherById(String id);
}
