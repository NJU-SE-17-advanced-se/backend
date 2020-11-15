package org.njuse17advancedse.entitydomain.service;

import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;

public interface DomainService {
  IDomain getDomainById(String id);

  IDomainBasic getDomainBasicInfoById(String id);

  List<String> getPapers(String id);

  List<String> getResearchers(String id);

  IResult getDomainsByCond(String keyword, int page);
}
