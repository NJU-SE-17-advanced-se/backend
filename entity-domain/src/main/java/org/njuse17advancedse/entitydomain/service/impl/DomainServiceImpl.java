package org.njuse17advancedse.entitydomain.service.impl;

import java.util.Collections;
import java.util.List;
import org.njuse17advancedse.entitydomain.data.AllRepository;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;
import org.njuse17advancedse.entitydomain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {
  @Autowired
  private AllRepository repository;

  @Override
  public IDomain getDomainById(String id) {
    if (!repository.existsById(id)) return new IDomain();
    return repository.getDomain(id);
  }

  @Override
  public IDomainBasic getDomainBasicInfoById(String id) {
    if (!repository.existsById(id)) return new IDomainBasic();
    return repository.getDomainBasic(id);
  }

  @Override
  public List<String> getPapers(String id) {
    if (!repository.existsById(id)) return Collections.singletonList(
      "Not Found"
    );
    return repository.getPapers(id);
  }

  @Override
  public List<String> getResearchers(String id) {
    if (!repository.existsById(id)) return Collections.singletonList(
      "Not Found"
    );
    return repository.getResearchers(id);
  }

  @Override
  public IResult getDomainsByCond(String keyword, int page) {
    if (page <= 0) return new IResult();
    keyword = keyword.toLowerCase();
    return repository.getDomainsByCond(keyword, page);
  }
}
