package org.njuse17advancedse.apigateway.apps.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.domains.repo.entity.PaperRepo;
import org.njuse17advancedse.apigateway.domains.repo.entity.ResearcherRepo;
import org.njuse17advancedse.apigateway.domains.repo.task.CitationAnalysisRepo;
import org.njuse17advancedse.apigateway.domains.repo.task.ImpactAnalysisRepo;
import org.njuse17advancedse.apigateway.infra.entity.Affiliation;
import org.njuse17advancedse.apigateway.infra.entity.Domain;
import org.njuse17advancedse.apigateway.infra.entity.Paper;
import org.njuse17advancedse.apigateway.interfaces.dto.researcher.IAffiliation;
import org.njuse17advancedse.apigateway.interfaces.dto.researcher.IDomain;
import org.njuse17advancedse.apigateway.interfaces.dto.researcher.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.researcher.IResearcher;
import org.springframework.stereotype.Service;

@Service
public class ResearcherService {
  private final CitationAnalysisRepo citationAnalysisRepo;

  private final ImpactAnalysisRepo impactAnalysisRepo;

  private final ModelMapper modelMapper;

  private final PaperRepo paperRepo;

  private final ResearcherRepo researcherRepo;

  // 查看某学者某一时间段所在机构
  public List<IAffiliation> getAffiliationsByTimeRange(
    String id,
    String start,
    String end
  ) {
    List<IAffiliation> res = new ArrayList<>();
    List<Affiliation> affiliations = researcherRepo.getAffiliationsByTimeRange(
      id,
      start,
      end
    );
    for (Affiliation affiliation : affiliations) {
      res.add(modelMapper.map(affiliation, IAffiliation.class));
    }
    return res;
  }

  // 查看某学者某一时间段的研究方向
  public List<IDomain> getDomainsByTimeRange(
    String id,
    String start,
    String end
  ) {
    List<IDomain> res = new ArrayList<>();
    List<Domain> domains = researcherRepo.getDomainsByTimeRange(id, start, end);
    for (Domain domain : domains) {
      res.add(modelMapper.map(domain, IDomain.class));
    }
    return res;
  }

  // TODO: 预测某学者未来的研究方向
  public List<IDomain> getFutureDomains(String id) {
    List<IDomain> res = new ArrayList<>();
    res.add(
      new IDomain("3", "测试领域3", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IDomain("4", "测试领域4", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  // TODO: 查看某学者某一时间段的合作关系
  public List<IResearcher> getPartnershipByTimeRange(
    String id,
    String start,
    String end
  ) {
    List<IResearcher> res = new ArrayList<>();
    res.add(
      new IResearcher("1", "测试学者1", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IResearcher("2", "测试学者2", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  // TODO: 预测某学者未来的合作关系
  public List<IResearcher> getFuturePartnership(String id) {
    List<IResearcher> res = new ArrayList<>();
    res.add(
      new IResearcher("3", "测试学者3", new ArrayList<>(), new ArrayList<>())
    );
    res.add(
      new IResearcher("4", "测试学者4", new ArrayList<>(), new ArrayList<>())
    );
    return res;
  }

  // 查看某学者的论文引用情况
  public Map<String, List<IPaper>> getReferences(String id) throws Exception {
    Map<String, List<IPaper>> res = new HashMap<>();
    Map<Long, List<Long>> references = citationAnalysisRepo.getResearcherReferences(
      id
    );
    for (Map.Entry<Long, List<Long>> entry : references.entrySet()) {
      String researcherPaperId = String.valueOf(entry.getKey());
      List<IPaper> referencePapers = new ArrayList<>();
      for (long paperId : entry.getValue()) {
        Paper referenceOriginPaper = paperRepo.getPaperById(
          String.valueOf(paperId)
        );
        IPaper referencePaper = modelMapper.map(
          referenceOriginPaper,
          IPaper.class
        );
        referencePapers.add(referencePaper);
      }
      res.put(researcherPaperId, referencePapers);
    }
    return res;
  }

  // 查看某学者的论文被引情况
  public Map<String, List<IPaper>> getCitations(String id) throws Exception {
    Map<String, List<IPaper>> res = new HashMap<>();
    Map<Long, List<Long>> citations = citationAnalysisRepo.getResearcherCitations(
      id
    );
    for (Map.Entry<Long, List<Long>> entry : citations.entrySet()) {
      String researcherPaperId = String.valueOf(entry.getKey());
      List<IPaper> citationPapers = new ArrayList<>();
      for (long paperId : entry.getValue()) {
        Paper citationOriginPaper = paperRepo.getPaperById(
          String.valueOf(paperId)
        );
        IPaper citationPaper = modelMapper.map(
          citationOriginPaper,
          IPaper.class
        );
        citationPapers.add(citationPaper);
      }
      res.put(researcherPaperId, citationPapers);
    }
    return res;
  }

  // 查看某学者的影响力
  public double getImpact(String id) throws Exception {
    return impactAnalysisRepo.getResearcherImpact(id);
  }

  public ResearcherService(
    CitationAnalysisRepo citationAnalysisRepo,
    ImpactAnalysisRepo impactAnalysisRepo,
    ModelMapper modelMapper,
    PaperRepo paperRepo,
    ResearcherRepo researcherRepo
  ) {
    this.citationAnalysisRepo = citationAnalysisRepo;
    this.impactAnalysisRepo = impactAnalysisRepo;
    this.modelMapper = modelMapper;
    this.paperRepo = paperRepo;
    this.researcherRepo = researcherRepo;
  }
}
