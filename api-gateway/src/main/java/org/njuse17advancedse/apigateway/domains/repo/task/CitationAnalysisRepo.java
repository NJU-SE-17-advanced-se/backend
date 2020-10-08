package org.njuse17advancedse.apigateway.domains.repo.task;

import java.util.List;
import java.util.Map;
import org.njuse17advancedse.apigateway.infra.exception.TestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class CitationAnalysisRepo {
  private static final String serverLocation = "http://101.37.152.235:6324";

  private final RestTemplate restTemplate;

  // 查看某学者的论文引用情况
  public Map<Long, List<Long>> getResearcherReferences(String id)
    throws Exception {
    String url = serverLocation + "/citation/researcher/quoting/" + id;
    return getReferences(url);
  }

  // 查看某学者的论文引用情况
  public Map<Long, List<Long>> getResearcherCitations(String id)
    throws Exception {
    String url = serverLocation + "/citation/researcher/quoted/" + id;
    return getReferences(url);
  }

  // 查看某论文引用情况
  public List<Long> getPaperReferences(String id) throws Exception {
    String url = serverLocation + "/citation/paper/quoting/" + id;
    return getCitations(url);
  }

  // 查看某论文被引情况
  public List<Long> getPaperCitations(String id) throws Exception {
    String url = serverLocation + "/citation/paper/quoted/" + id;
    return getCitations(url);
  }

  // 请求
  private Map<Long, List<Long>> getReferences(String url) throws Exception {
    ResponseEntity<Map> res = restTemplate.getForEntity(url, Map.class);
    HttpStatus status = res.getStatusCode();
    if (status.is2xxSuccessful() && res.getBody() != null) {
      return (Map<Long, List<Long>>) res.getBody();
    } else {
      throw new TestException();
    }
  }

  // 请求
  private List<Long> getCitations(String url) throws Exception {
    ResponseEntity<List> res = restTemplate.getForEntity(url, List.class);
    HttpStatus status = res.getStatusCode();
    if (status.is2xxSuccessful() && res.getBody() != null) {
      return (List<Long>) res.getBody();
    } else {
      throw new TestException();
    }
  }

  public CitationAnalysisRepo(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
}
