package org.njuse17advancedse.apigateway.domains.repo.task;

import org.njuse17advancedse.apigateway.infra.exception.TestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ImpactAnalysisRepo {
  private static final String serverLocation = "http://101.37.152.235:8080";

  private RestTemplate restTemplate;

  // 查看某论文的影响力
  public double getPaperImpact(String id) throws Exception {
    String url = serverLocation + "/impact/paper/" + id;
    return getImpact(url);
  }

  // 查看某学者的影响力
  public double getResearcherImpact(String id) throws Exception {
    String url = serverLocation + "/impact/researcher" + id;
    return getImpact(url);
  }

  // 请求
  private double getImpact(String url) throws Exception {
    ResponseEntity<Double> res = restTemplate.getForEntity(url, Double.class);
    HttpStatus status = res.getStatusCode();
    if (status.is2xxSuccessful() && res.getBody() != null) {
      return res.getBody();
    } else {
      throw new TestException();
    }
  }

  public ImpactAnalysisRepo(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
}
