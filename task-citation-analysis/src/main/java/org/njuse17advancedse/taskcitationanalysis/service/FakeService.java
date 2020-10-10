package org.njuse17advancedse.taskcitationanalysis.service;

import java.util.List;
import org.njuse17advancedse.taskcitationanalysis.entity.Paper;
import org.njuse17advancedse.taskcitationanalysis.entity.Researcher;

public interface FakeService {
  List<Paper> getAllPapers();

  Researcher getResearcherById(Long id);
}
