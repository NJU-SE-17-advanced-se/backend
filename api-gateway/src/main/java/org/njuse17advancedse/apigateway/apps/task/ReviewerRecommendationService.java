package org.njuse17advancedse.apigateway.apps.task;

import java.util.List;
import org.njuse17advancedse.apigateway.infra.exception.TestException;
import org.njuse17advancedse.apigateway.interfaces.dto.IPaperUpload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReviewerRecommendationService {
  private static final String serverLocation = "http://106.15.248.145:8080";

  private final RestTemplate restTemplate;

  // 查看某论文推荐的审稿人
  public List<String> getPaperRecommendedReviewers(IPaperUpload paper)
    throws Exception {
    String url = serverLocation + "/paper/recommend";
    return getPaperReviewers(url, paper);
  }

  // 查看某论文不推荐的审稿人
  public List<String> getPaperNotRecommendedReviewers(IPaperUpload paper)
    throws Exception {
    String url = serverLocation + "/paper/not-recommend";
    return getPaperReviewers(url, paper);
  }

  // 请求
  private List<String> getPaperReviewers(String url, IPaperUpload paper)
    throws TestException {
    ResponseEntity<List> res = restTemplate.postForEntity(
      url,
      paper,
      List.class
    );
    HttpStatus status = res.getStatusCode();
    if (status.is2xxSuccessful() && res.getBody() != null) {
      return (List<String>) res.getBody();
    } else {
      throw new TestException();
    }
  }

  public ReviewerRecommendationService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
}
