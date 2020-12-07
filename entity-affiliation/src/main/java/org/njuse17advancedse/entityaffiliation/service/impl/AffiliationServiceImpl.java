package org.njuse17advancedse.entityaffiliation.service.impl;

import java.util.Collections;
import java.util.List;
import org.njuse17advancedse.entityaffiliation.data.AllRepository;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.dto.IResult;
import org.njuse17advancedse.entityaffiliation.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffiliationServiceImpl implements AffiliationService {
  @Autowired
  AllRepository repository;

  private static final List<String> notFound = Collections.singletonList(
    "Not Found"
  );

  @Override
  public IAffiliation getAffiliationById(String id) {
    if (!repository.existsById(id)) return new IAffiliation();
    return repository.getAffiliationById(id);
  }

  @Override
  public IAffiliationBasic getAffiliationBasicInfoById(String id) {
    if (!repository.existsById(id)) return new IAffiliationBasic();
    return repository.getAffiliationBasicInfoById(id);
  }

  @Override
  public List<String> getAffiliationResearchersById(String id) {
    if (!repository.existsById(id)) return notFound;
    return repository.getAffiliationResearchersById(id);
  }

  @Override
  public List<String> getAffiliationPapersById(String id) {
    if (!repository.existsById(id)) return notFound;
    return repository.getAffiliationPapersById(id);
  }

  @Override
  public List<String> getAffiliationDomainsById(String id) {
    if (!repository.existsById(id)) return notFound;
    return repository.getAffiliationDomainsById(id);
  }

  @Override
  public IResult getAffiliationsByCond(String keyword, int page) {
    if (page <= 0) return new IResult();
    keyword = keyword.toLowerCase();
    return repository.getAffiliationsByCond(keyword, page);
  }
}
