package org.njuse17advancedse.entityaffiliation.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AllRepository {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(readOnly = true)
  public IAffiliation getAffiliationById(String id) {
    String sql =
      "select group_concat(distinct paper_domain.did) as domains, group_concat(distinct researcher_affiliation.rid) as researchers," +
      " group_concat(distinct paper_researcher.pid) as papers, affiliation.* " +
      "from affiliation,researcher_affiliation,paper_researcher,paper_domain" +
      " where affiliation.id=researcher_affiliation.aid and affiliation.id='" +
      id +
      "' and researcher_affiliation.rid=paper_researcher.rid" +
      " and paper_researcher.pid=paper_domain.pid;";
    System.out.println(sql);
    return jdbcTemplate.queryForObject(sql, new AffiliationRowMapper());
  }

  @Transactional(readOnly = true)
  public IAffiliationBasic getAffiliationBasicInfoById(String id) {
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
}

class AffiliationRowMapper implements RowMapper<IAffiliation> {

  @Override
  public IAffiliation mapRow(ResultSet rs, int rowNum) throws SQLException {
    IAffiliation res = new IAffiliation();
    String id = rs.getString("id");
    res.setId(id);
    res.setName(rs.getString("name"));
    res.setDescription(rs.getString("description"));
    res.setPapers(string2List(rs.getString("papers")));
    res.setDomains(string2List(rs.getString("domains")));
    res.setResearchers(string2List(rs.getString("researchers")));
    return res;
  }

  private List<String> string2List(String str) {
    String[] strs = str.split(",");
    return Arrays.asList(strs);
  }
}

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
