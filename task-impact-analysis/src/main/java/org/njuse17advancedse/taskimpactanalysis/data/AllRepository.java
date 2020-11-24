package org.njuse17advancedse.taskimpactanalysis.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.njuse17advancedse.taskimpactanalysis.dto.ICitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AllRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(readOnly = true)
  public boolean existsResearcherById(String id) {
    String exist = "select id from `researcher` where id='" + id + "'";
    return jdbcTemplate.queryForList(exist, String.class).size() != 0;
  }

  @Transactional(readOnly = true)
  public List<ICitation> getPaperQuotingTimes(String researcherId) {
    String sql =
      " select rel1.pid as paperId,count(rel.pid) as citation from paper_researcher rel1 left join paper_reference rel on rel1.pid=rel.rid  where rel1.rid='" +
      researcherId +
      "' group by rel1.pid;";
    return jdbcTemplate.query(sql, new BeanCitationRowMapper());
  }
}

class BeanCitationRowMapper extends BeanPropertyRowMapper<ICitation> {

  @Override
  public ICitation mapRow(ResultSet rs, int rowNum) throws SQLException {
    ICitation res = new ICitation();
    res.setPaperId(rs.getString("paperId"));
    res.setCitation(rs.getInt("citation"));
    return res;
  }
}
