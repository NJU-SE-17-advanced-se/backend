package org.njuse17advancedse.entitydomain.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AllRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private static final int PAGE_SIZE = 10;

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
    IDomain res = new IDomain();
    IDomainBasic idb = getDomainBasic(id);
    if (idb.getId() == null) return new IDomain();
    res.setName(idb.getName());
    res.setId(id);
    res.setPapers(getPapers(id));
    res.setResearchers(getResearchers(id));
    return res;
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

  @Transactional(readOnly = true)
  public IResult getDomainsByCond(String keyword, int page) {
    String countSQL =
      "select count(id) from `domain` where locate('" +
      keyword +
      "',lower(`name`))!=0;";
    int count = jdbcTemplate.queryForObject(countSQL, Integer.class);
    String startIndex = Integer.toString(PAGE_SIZE * (page - 1));
    String sql =
      "select id from `domain` where locate('" +
      keyword +
      "',lower(`name`))!=0 limit " +
      startIndex +
      "," +
      PAGE_SIZE;
    return new IResult(jdbcTemplate.queryForList(sql, String.class), count);
  }
}

//
//class DomainRowMapper implements RowMapper<IDomain> {
//
//  @Override
//  public IDomain mapRow(ResultSet rs, int rowNum) throws SQLException {
//    IDomain res = new IDomain();
//    try {
//      String papers = rs.getString("papers");
//      res.setPapers(string2List(papers));
//    } catch (Exception e) {
//      res.setPapers(new ArrayList<>());
//    }
//    try {
//      String researchers = rs.getString("researchers");
//      res.setResearchers(string2List(researchers));
//    } catch (Exception e) {
//      res.setResearchers(new ArrayList<>());
//    }
//    res.setId(rs.getString("id"));
//    res.setName(rs.getString("name"));
//    return res;
//  }
//
//  private List<String> string2List(String str) {
//    String[] strs = str.split(",");
//    return Arrays.asList(strs);
//  }
//}

class DomainBasicRowMapper implements RowMapper<IDomainBasic> {

  @Override
  public IDomainBasic mapRow(ResultSet rs, int rowNum) throws SQLException {
    IDomainBasic res = new IDomainBasic();
    res.setId(rs.getString("id"));
    res.setName(rs.getString("name"));
    return res;
  }
}
