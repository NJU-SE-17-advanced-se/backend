package org.njuse17advancedse.entitydomain;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.njuse17advancedse.entitydomain.entity.JpaDomain;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ContextConfiguration(classes = { JpaRepository.class })
class EntityDomainApplicationTests {

  @Test
  void contextLoads() {
    assertNull(new JpaDomain().getId());
  }
}
