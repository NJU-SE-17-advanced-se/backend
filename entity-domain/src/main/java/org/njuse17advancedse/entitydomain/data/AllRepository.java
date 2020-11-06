package org.njuse17advancedse.entitydomain.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.Event;

@Repository
public class AllRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(readOnly = true)
  public List<String> getPapers(String id) {
    String sql = "select pid from paper_domain where did='" + id + "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getResearchers(String id) {
    String sql =
      "select paper_researcher.rid from paper_domain,paper_researcher where paper_domain.did='" +
      id +
      "' and paper_domain.pid=paper_researcher.pid;";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public IDomain getDomain(String id) {
    String exist = "select id from `domain` where id='" + id + "'";
    if (jdbcTemplate.queryForList(exist, String.class).size() == 0) {
      return new IDomain();
    }
    String sql =
      "select group_concat(distinct paper_domain.pid) as papers, group_concat(distinct paper_researcher.rid) as researchers, `domain`.*" +
      " from paper_domain,paper_researcher,`domain` where" +
      " paper_domain.pid=paper_researcher.pid and paper_domain.did=`domain`.id and `domain`.id='" +
      id +
      "'";
    return jdbcTemplate.queryForObject(sql, new DomainRowMapper());
  }

  @Transactional(readOnly = true)
  public IDomainBasic getDomainBasic(String id) {
    String exist = "select id from `domain` where id='" + id + "'";
    if (jdbcTemplate.queryForList(exist, String.class).size() == 0) {
      return new IDomainBasic();
    }
    String sql = "select * from `domain` where id='" + id + "'";
    return jdbcTemplate.queryForObject(sql, new DomainBasicRowMapper());
  }
}

class DomainRowMapper implements RowMapper<IDomain> {

  @Override
  public IDomain mapRow(ResultSet rs, int rowNum) throws SQLException {
    IDomain res = new IDomain();
    res.setPapers(string2List(rs.getString("papers")));
    res.setResearchers(string2List(rs.getString("researchers")));
    res.setId(rs.getString("id"));
    res.setName(rs.getString("name"));
    return res;
  }

  private List<String> string2List(String str) {
    String[] strs = str.split(",");
    return Arrays.asList(strs);
  }
}

class DomainBasicRowMapper implements RowMapper<IDomainBasic> {

  @Override
  public IDomainBasic mapRow(ResultSet rs, int rowNum) throws SQLException {
    IDomainBasic res = new IDomainBasic();
    res.setId(rs.getString("id"));
    res.setName(rs.getString("name"));
    return res;
  }
}
