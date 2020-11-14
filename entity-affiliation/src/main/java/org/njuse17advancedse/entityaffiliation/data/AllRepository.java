package org.njuse17advancedse.entityaffiliation.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.dto.IResult;
import org.springframework.beans.factory.annotation.Autowired;
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
  public IAffiliation getAffiliationById(String id) {
    IAffiliation res = new IAffiliation(getAffiliationBasicInfoById(id));
    res.setResearchers(getAffiliationResearchersById(id));
    res.setDomains(getAffiliationDomainsById(id));
    res.setPapers(getAffiliationPapersById(id));
    return res;
  }

  @Transactional(readOnly = true)
  public IAffiliationBasic getAffiliationBasicInfoById(String id) {
    String exist = "select id from affiliation where id='" + id + "'";
    if (jdbcTemplate.queryForList(exist, String.class).size() == 0) {
      return new IAffiliationBasic();
    }
    String sql = "select * from affiliation where id='" + id + "'";
    return jdbcTemplate.queryForObject(sql, new AffiliationBasicRowMapper());
  }

  @Transactional(readOnly = true)
  public List<String> getAffiliationResearchersById(String id) {
    String sql =
      "select distinct rid from researcher_affiliation where aid='" + id + "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getAffiliationPapersById(String id) {
    String sql =
      "select distinct paper_researcher.pid from researcher_affiliation,paper_researcher where " +
      "researcher_affiliation.aid='" +
      id +
      "' and paper_researcher.rid=researcher_affiliation.rid;";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getAffiliationDomainsById(String id) {
    String sql =
      "select distinct paper_domain.did from researcher_affiliation,paper_researcher,paper_domain" +
      " where researcher_affiliation.aid='" +
      id +
      "' and researcher_affiliation.rid=paper_researcher.rid" +
      " and paper_researcher.pid=paper_domain.pid;";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public IResult getAffiliationsByCond(String keyword, int page) {
    String countSQL =
      "select count(id) from affiliation where locate('" +
      keyword +
      "',`name`)!=0;";
    int count = jdbcTemplate.queryForObject(countSQL, Integer.class);
    String startIndex = Integer.toString(PAGE_SIZE * (page - 1));
    String sql =
      "select id from affiliation where locate('" +
      keyword +
      "',`name`)!=0 limit " +
      startIndex +
      "," +
      PAGE_SIZE;
    return new IResult(jdbcTemplate.queryForList(sql, String.class), count);
  }
}

//class AffiliationRowMapper implements RowMapper<IAffiliation> {
//
//  @Override
//  public IAffiliation mapRow(ResultSet rs, int rowNum) throws SQLException {
//    IAffiliation res = new IAffiliation();
//    String id = rs.getString("id");
//    res.setId(id);
//    res.setName(rs.getString("name"));
//    res.setDescription(rs.getString("description"));
//    try {
//      String papers = rs.getString("papers");
//      res.setPapers(string2List(papers));
//    } catch (Exception e) {
//      res.setPapers(new ArrayList<>());
//    }
//    try {
//      String domains = rs.getString("domains");
//      res.setDomains(string2List(domains));
//    } catch (Exception e) {
//      res.setDomains(new ArrayList<>());
//    }
//    try {
//      String researchers = rs.getString("researchers");
//      res.setResearchers(string2List(researchers));
//    } catch (Exception e) {
//      res.setResearchers(new ArrayList<>());
//    }
//    return res;
//  }
//
//  private List<String> string2List(String str) {
//    String[] strs = str.split(",");
//    return Arrays.asList(strs);
//  }
//}

class AffiliationBasicRowMapper implements RowMapper<IAffiliationBasic> {

  @Override
  public IAffiliationBasic mapRow(ResultSet rs, int rowNum)
    throws SQLException {
    IAffiliationBasic res = new IAffiliationBasic();
    String id = rs.getString("id");
    res.setId(id);
    res.setName(rs.getString("name"));
    res.setDescription(rs.getString("description"));
    return res;
  }
}
