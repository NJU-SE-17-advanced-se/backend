package org.njuse17advancedse.apigateway.domains.repo.task;

import org.njuse17advancedse.apigateway.domains.po.task.reviewer.IResearcher;
import org.njuse17advancedse.apigateway.infra.exception.TestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ReviewerRecommendationRepo {
  private static final String serverLocation = "http://106.15.248.145:8080";

  private RestTemplate restTemplate;

  // 查看某论文推荐的审稿人
  public IResearcher getPaperRecommendedReviewers(String id) throws Exception {
    String url = serverLocation + "/paper/" + id + "/recommend";
    ResponseEntity<IResearcher> res = restTemplate.getForEntity(
      url,
      IResearcher.class
    );
    HttpStatus status = res.getStatusCode();
    if (status.is2xxSuccessful() && res.getBody() != null) {
      return res.getBody();
    } else {
      throw new TestException();
    }
  }

  // 查看某论文不推荐的审稿人
  public IResearcher getPaperNotRecommendedReviewers(
    String id,
    String criteria
  )
    throws Exception {
    String url = serverLocation + "/paper/" + id + "/not-recommend";
    // 进行请求
    ResponseEntity<IResearcher> res = restTemplate.getForEntity(
      url,
      IResearcher.class
    );
    HttpStatus status = res.getStatusCode();
    if (status.is2xxSuccessful() && res.getBody() != null) {
      return res.getBody();
    } else {
      throw new TestException();
    }
  }

  public ReviewerRecommendationRepo(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
}
