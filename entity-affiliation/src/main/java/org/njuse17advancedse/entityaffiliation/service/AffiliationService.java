package org.njuse17advancedse.entityaffiliation.service;

import java.util.List;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;

public interface AffiliationService {
  IAffiliation getAffiliationById(String id);
  IAffiliationBasic getAffiliationBasicInfoById(String id);
  List<String> getAffiliationResearchersById(String id);
  List<String> getAffiliationPapersById(String id);
  List<String> getAffiliationDomainsById(String id);
}
