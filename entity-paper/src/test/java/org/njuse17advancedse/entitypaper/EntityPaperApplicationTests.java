package org.njuse17advancedse.entitypaper;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.njuse17advancedse.entitypaper.entity.JpaPaper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ContextConfiguration(classes = { JpaRepository.class })
class EntityPaperApplicationTests {

  @Test
  void contextLoads() {
    assertNull(new JpaPaper().getId());
  }
}
