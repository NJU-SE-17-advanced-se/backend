package org.njuse17advancedse.entitypaper.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
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
  public List<String> getAllPapers() {
    String sql = "select id from paper";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getPapersByResearcher(String researcher) {
    String sql =
      "select pid from `paper_researcher` where rid='" + researcher + "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getPapersByPublication(String publication) {
    String sql =
      "select id from `paper` where publication_id='" + publication + "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getPapersByDate(String date) {
    String sql =
      "select id from `paper` where publication_date='" + date + "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getPapersByResearcherAndPublication(
    String researcher,
    String publication
  ) {
    String sql =
      "select paper.id from `paper`,`paper_researcher` where paper.publication_id='" +
      publication +
      "'and paper.id=paper_researcher.pid and paper_researcher.rid='" +
      researcher +
      "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getPapersByResearcherAndDate(
    String researcher,
    String date
  ) {
    String sql =
      "select paper.id from `paper`,`paper_researcher` where paper.publication_date='" +
      date +
      "'and paper.id=paper_researcher.pid and paper_researcher.rid='" +
      researcher +
      "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getPapersByPublicationAndDate(
    String publication,
    String date
  ) {
    String sql =
      "select id from `paper` where publication_id='" +
      publication +
      "' and publication_date='" +
      date +
      "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional
  public List<String> getPapersByResearcherAndPublicationAndDate(
    String researcher,
    String publication,
    String date
  ) {
    String sql =
      "select paper.id from `paper`,`paper_researcher` where paper.publication_date='" +
      date +
      "' and paper.publication_id='" +
      publication +
      "' and paper.id=paper_researcher.pid and paper_researcher.rid='" +
      researcher +
      "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public List<String> getDomains(String pid) {
    String sql = "select `did` from `paper_domain` where pid='" + pid + "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public IPaper getIPaper(String paperId) {
    String sql =
      "select paper.*,group_concat(distinct paper_reference.reference_doi) as `references`," +
      "group_concat(distinct paper_researcher.rid) as researchers, group_concat(distinct paper_domain.did) as domains" +
      " from `paper`,`paper_reference`,`paper_researcher`,`paper_domain` " +
      "where paper.id= '" +
      paperId +
      "' and paper.id=paper_reference.pid and paper.id=paper_domain.pid and paper.id =paper_researcher.pid " +
      "group by paper.id;";
    return jdbcTemplate.queryForObject(sql, new PaperRowMapper());
  }

  @Transactional(readOnly = true)
  public IPaperBasic getPaperBasic(String paperId) {
    String sql =
      "select paper.*, group_concat(distinct paper_researcher.rid) as researchers" +
      " from `paper`,`publication`,`paper_researcher` " +
      "where paper.id= '" +
      paperId +
      "' " +
      "and paper.id =paper_researcher.pid " +
      "group by paper.id;";
    return jdbcTemplate.queryForObject(sql, new PaperBasicRowMapper());
  }
}

class PaperRowMapper implements RowMapper<IPaper> {

  @Override
  public IPaper mapRow(ResultSet rs, int rowNum) throws SQLException {
    IPaper res = new IPaper();
    res.setId(rs.getString("id"));
    res.setAbs(rs.getString("abs"));
    res.setTitle(rs.getString("title"));
    res.setPublication(rs.getString("publication_id"));
    res.setLink(rs.getString("link"));
    res.setPublicationDate(rs.getString("publication_date"));
    res.setReferences(string2List(rs.getString("references")));
    res.setDomains(string2List(rs.getString("domains")));
    res.setResearchers(string2List(rs.getString("researchers")));
    return res;
  }

  private List<String> string2List(String str) {
    String[] strs = str.split(",");
    return Arrays.asList(strs);
  }
}

class PaperBasicRowMapper implements RowMapper<IPaperBasic> {

  @Override
  public IPaperBasic mapRow(ResultSet rs, int rowNum) throws SQLException {
    IPaperBasic res = new IPaperBasic();
    res.setId(rs.getString("id"));
    res.setAbs(rs.getString("abs"));
    res.setTitle(rs.getString("title"));
    res.setPublication(rs.getString("publication_id"));
    res.setPublicationDate(rs.getString("publication_date"));
    res.setResearchers(string2List(rs.getString("researchers")));
    return res;
  }

  private List<String> string2List(String str) {
    String[] strs = str.split(",");
    return Arrays.asList(strs);
  }
}
