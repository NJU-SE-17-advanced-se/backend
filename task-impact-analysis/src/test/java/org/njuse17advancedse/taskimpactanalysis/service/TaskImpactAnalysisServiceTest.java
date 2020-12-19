package org.njuse17advancedse.taskimpactanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.taskimpactanalysis.data.AllRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class TaskImpactAnalysisServiceTest {
  @Autowired
  TaskImpactAnalysisService service;

  @MockBean
  AllRepository repository;

  @BeforeEach
  void init() {
    Mockito.when(repository.existsPaperById("SKT")).thenReturn(true);
    Mockito.when(repository.existsPaperById("not exist")).thenReturn(false);
    Mockito.when(repository.existsPaperById("not exist")).thenReturn(false);
  }

  @Test
  void testGetHIndex1() {
    Mockito.when(repository.existsResearcherById("skt")).thenReturn(true);
    List<Integer> citations = Arrays.asList(4, 3, 1, 7, 0, 4, 5, 4);
    Mockito.when(repository.getPaperQuotingTimes("skt")).thenReturn(citations);
    assertEquals(4, service.getHIndex("skt"));
    assertEquals(-1, service.getHIndex("not exist"));
  }

  @Test
  void testGetHIndex2() {
    Mockito.when(repository.existsResearcherById("skt")).thenReturn(true);
    List<Integer> citations = Arrays.asList(4, 3, 1, 7, 0);
    Mockito.when(repository.getPaperQuotingTimes("skt")).thenReturn(citations);
    assertEquals(3, service.getHIndex("skt"));
  }

  @Test
  void testGetPaperImpact() {
    Mockito.when(repository.getSinglePaperQuotingTimes("SKT")).thenReturn(12);
    Mockito.when(repository.getImpactFactor("SKT")).thenReturn(0.125);
    assertEquals(1.5d, service.getPaperImpact("SKT"));
    assertEquals(-1, service.getPaperImpact("not exist"));
  }
}
