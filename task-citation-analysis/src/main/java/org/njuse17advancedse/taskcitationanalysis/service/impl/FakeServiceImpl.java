package org.njuse17advancedse.taskcitationanalysis.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.njuse17advancedse.taskcitationanalysis.entity.Paper;
import org.njuse17advancedse.taskcitationanalysis.entity.Researcher;
import org.njuse17advancedse.taskcitationanalysis.service.FakeService;
import org.springframework.stereotype.Service;

@Service
public class FakeServiceImpl implements FakeService {

  @Override
  public List<Paper> getAllPapers() {
    return new ArrayList<>();
  }

  @Override
  public Researcher getResearcherById(String id) {
    Researcher r = new Researcher();
    r.setPapers(Collections.emptyList());
    return r;
  }

  @Override
  public Paper getPaperById(String id) {
    Paper p = new Paper();
    p.setResearchers(Collections.emptyList());
    return p;
  }
}
