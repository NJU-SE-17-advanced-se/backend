package org.njuse17advancedse.taskreviewerrecommendation.serviceImp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Paper;
import org.njuse17advancedse.taskreviewerrecommendation.entity.Researcher;
import org.njuse17advancedse.taskreviewerrecommendation.service.TaskReviewerRecommendationService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class TaskReviewerRecommendationServiceImpTest {
  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private TaskReviewerRecommendationService taskReviewerRecommendationService;

  @Test
  void getRecommendReviewer() {}

  @Test
  void getNotRecommendReviewer() {}
}
