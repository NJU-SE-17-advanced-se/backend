package org.njuse17advancedse.entitypaper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.*;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class EntityPaperServiceTest {
  @Autowired
  PaperService service;

  @Test
  public void test1() {
    String testDoi = "10.1109/ASE.2019.00011";
    IPaper p = service.getIPaper(testDoi);
    System.out.println(p.getAbs());
  }
}
