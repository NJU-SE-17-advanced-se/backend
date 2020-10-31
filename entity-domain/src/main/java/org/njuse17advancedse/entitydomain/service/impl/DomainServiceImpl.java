package org.njuse17advancedse.entitydomain.service.impl;

import java.util.List;
import org.njuse17advancedse.entitydomain.data.AllRepository;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {
  @Autowired
  private AllRepository repository;

  @Override
  public IDomain getDomainById(String id) {
    return repository.getDomain(id);
  }

  @Override
  public IDomainBasic getDomainBasicInfoById(String id) {
    return repository.getDomainBasic(id);
  }

  @Override
  public List<String> getPapers(String id) {
    return repository.getPapers(id);
  }

  @Override
  public List<String> getResearchers(String id) {
    return repository.getResearchers(id);
  }
}
