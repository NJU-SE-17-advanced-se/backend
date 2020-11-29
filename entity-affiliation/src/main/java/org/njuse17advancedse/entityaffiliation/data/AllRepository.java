package org.njuse17advancedse.entityaffiliation.data;

import java.util.List;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.dto.IResult;

public interface AllRepository {
  IAffiliation getAffiliationById(String id);
  IAffiliationBasic getAffiliationBasicInfoById(String id);
  boolean existsById(String id);
  List<String> getAffiliationResearchersById(String id);
  List<String> getAffiliationPapersById(String id);
  List<String> getAffiliationDomainsById(String id);
  IResult getAffiliationsByCond(String keyword, int page);
}
