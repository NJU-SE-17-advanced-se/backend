package org.njuse17advancedse.taskimpactanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.njuse17advancedse.taskimpactanalysis.vo.PaperVo;
import org.njuse17advancedse.taskimpactanalysis.vo.ScholarVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskImpactAnalysisServiceTest {
  @Autowired
  TaskImpactAnalysisService service;

  @Test
  public void testGetHIndex1() {
    PaperVo vo1 = new PaperVo(
      0L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L),
      Arrays.asList(2L, 3L, 4L, 5L)
    );
    PaperVo vo2 = new PaperVo(
      1L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L, 5L),
      Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L)
    );
    PaperVo vo3 = new PaperVo(
      2L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L, 5L),
      Arrays.asList(0L, 2L, 3L, 4L, 5L)
    );
    PaperVo vo4 = new PaperVo(
      3L,
      "",
      Arrays.asList(1L, 2L),
      Arrays.asList(0L, 1L, 2L, 5L)
    );
    PaperVo vo5 = new PaperVo(
      4L,
      "",
      Arrays.asList(1L),
      Arrays.asList(0L, 1L, 2L)
    );
    PaperVo vo6 = new PaperVo(
      5L,
      "",
      Arrays.asList(2L, 3L, 4L),
      Arrays.asList(1L, 2L)
    );
    ScholarVo svo = new ScholarVo(
      null,
      Arrays.asList(vo1, vo2, vo3, vo4, vo5, vo6)
    );
    int hIndex = service.getHIndex(svo);
    assertEquals(hIndex, 3);
  }

  @Test
  public void testGetHIndex2() {
    PaperVo vo1 = new PaperVo(
      0L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L, 7L, 8L),
      Arrays.asList(2L, 3L, 4L, 5L, 8L)
    );
    PaperVo vo2 = new PaperVo(
      1L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L, 5L),
      Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 8L)
    );
    PaperVo vo3 = new PaperVo(
      2L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L, 5L),
      Arrays.asList(0L, 2L, 3L, 4L, 5L, 8L)
    );
    PaperVo vo4 = new PaperVo(
      3L,
      "",
      Arrays.asList(1L, 2L, 6L, 8L),
      Arrays.asList(0L, 1L, 2L, 5L, 8L)
    );
    PaperVo vo5 = new PaperVo(
      4L,
      "",
      Arrays.asList(1L),
      Arrays.asList(0L, 1L, 2L, 8L)
    );
    PaperVo vo6 = new PaperVo(
      5L,
      "",
      Arrays.asList(2L, 3L, 4L),
      Arrays.asList(1L, 2L, 8L)
    );
    PaperVo vo7 = new PaperVo(6L, "", Arrays.asList(), Arrays.asList(3L));
    PaperVo vo8 = new PaperVo(7L, "", Arrays.asList(8L), Arrays.asList(0L));
    PaperVo vo9 = new PaperVo(
      8L,
      "",
      Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L),
      Arrays.asList(0L, 3L, 7L)
    );
    ScholarVo svo = new ScholarVo(
      null,
      Arrays.asList(vo1, vo2, vo3, vo4, vo5, vo6, vo7, vo8, vo9)
    );
    int hIndex = service.getHIndex(svo);
    assertEquals(hIndex, 4);
  }

  @Test
  public void testGetPaperImpact() {
    PaperVo vo1 = new PaperVo(
      0L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L, 7L, 8L),
      Arrays.asList(2L, 3L, 4L, 5L, 8L)
    );
    PaperVo vo2 = new PaperVo(
      1L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L, 5L),
      Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 8L)
    );
    PaperVo vo3 = new PaperVo(
      2L,
      "",
      Arrays.asList(1L, 2L, 3L, 4L, 5L),
      Arrays.asList(0L, 2L, 3L, 4L, 5L, 8L)
    );
    PaperVo vo4 = new PaperVo(
      3L,
      "",
      Arrays.asList(1L, 2L, 6L, 8L),
      Arrays.asList(0L, 1L, 2L, 5L, 8L)
    );
    PaperVo vo5 = new PaperVo(
      4L,
      "",
      Arrays.asList(1L),
      Arrays.asList(0L, 1L, 2L, 8L)
    );
    PaperVo vo6 = new PaperVo(
      5L,
      "",
      Arrays.asList(2L, 3L, 4L),
      Arrays.asList(1L, 2L, 8L)
    );
    PaperVo vo7 = new PaperVo(6L, "", Arrays.asList(), Arrays.asList(3L));
    PaperVo vo8 = new PaperVo(7L, "", Arrays.asList(8L), Arrays.asList(0L));
    PaperVo vo9 = new PaperVo(
      8L,
      "",
      Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L),
      Arrays.asList(0L, 3L, 7L)
    );
    assertEquals(service.getPaperImpact(vo1), vo1.getQuotingIds().size());
    assertEquals(service.getPaperImpact(vo2), vo2.getQuotingIds().size());
    assertEquals(service.getPaperImpact(vo3), vo3.getQuotingIds().size());
    assertEquals(service.getPaperImpact(vo4), vo4.getQuotingIds().size());
    assertEquals(service.getPaperImpact(vo5), vo5.getQuotingIds().size());
    assertEquals(service.getPaperImpact(vo6), vo6.getQuotingIds().size());
    assertEquals(service.getPaperImpact(vo7), vo7.getQuotingIds().size());
    assertEquals(service.getPaperImpact(vo8), vo8.getQuotingIds().size());
    assertEquals(service.getPaperImpact(vo9), vo9.getQuotingIds().size());
  }
}
