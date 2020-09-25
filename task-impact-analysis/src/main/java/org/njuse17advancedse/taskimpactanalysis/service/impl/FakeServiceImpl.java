package org.njuse17advancedse.taskimpactanalysis.service.impl;

import org.njuse17advancedse.taskimpactanalysis.entity.Paper;
import org.njuse17advancedse.taskimpactanalysis.entity.Researcher;
import org.njuse17advancedse.taskimpactanalysis.service.FakeService;
import org.springframework.stereotype.Service;

@Service
public class FakeServiceImpl implements FakeService {

  @Override
  public Paper getPaperById(String id) {
    return null;
  }

  @Override
  public Researcher getResearcherById(String id) {
    return null;
  }
}
