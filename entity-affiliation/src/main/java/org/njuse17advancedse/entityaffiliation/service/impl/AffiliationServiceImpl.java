package org.njuse17advancedse.entityaffiliation.service.impl;

import java.util.List;
import org.njuse17advancedse.entityaffiliation.data.AllRepository;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffiliationServiceImpl implements AffiliationService {
  @Autowired
  AllRepository repository;

  @Override
  public IAffiliation getAffiliationById(String id) {
    return repository.getAffiliationById(id);
  }

  @Override
  public IAffiliationBasic getAffiliationBasicInfoById(String id) {
    return repository.getAffiliationBasicInfoById(id);
  }

  @Override
  public List<String> getAffiliationResearchersById(String id) {
    return repository.getAffiliationResearchersById(id);
  }

  @Override
  public List<String> getAffiliationPapersById(String id) {
    return repository.getAffiliationPapersById(id);
  }

  @Override
  public List<String> getAffiliationDomainsById(String id) {
    return repository.getAffiliationDomainsById(id);
  }
}
