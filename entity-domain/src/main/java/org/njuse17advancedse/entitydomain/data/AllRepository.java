package org.njuse17advancedse.entitydomain.data;

import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;

public interface AllRepository {
  IDomain getDomain(String domainId);

  IDomainBasic getDomainBasic(String domainId);

  List<String> getPapers(String domainId);

  List<String> getResearchers(String domainId);

  IResult getDomainsByCond(String keyword, int page);

  boolean existsById(String domainId);
}
