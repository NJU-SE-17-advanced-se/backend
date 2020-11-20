package org.njuse17advancedse.entitypaper.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
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

  @Transactional(readOnly = true)
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
    IPaperBasic ipb = getPaperBasic(paperId);
    IPaper res = new IPaper(ipb);
    String referenceSQL =
      "select distinct rid from paper_reference where pid='" + paperId + "';";
    res.setReferences(jdbcTemplate.queryForList(referenceSQL, String.class));
    String linkSQL = "select link from paper where id='" + paperId + "';";
    res.setLink(jdbcTemplate.queryForObject(linkSQL, String.class));
    String domainSQL =
      "select distinct did from paper_domain where pid='" + paperId + "';";
    res.setDomains(jdbcTemplate.queryForList(domainSQL, String.class));
    return res;
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

  @Transactional(readOnly = true)
  public boolean existsById(String paperId) {
    String exist = "select id from paper where id='" + paperId + "'";
    return jdbcTemplate.queryForList(exist, String.class).size() != 0;
  }

  @Transactional(readOnly = true)
  public List<String> getCitations(String paperId) {
    String sql =
      "select distinct pid from paper_reference where rid='" + paperId + "';";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  @Transactional(readOnly = true)
  public IResult getPaperByCond(String keyword, int page) {
    String countSQL =
      "select count(id) from paper where (locate('" +
      keyword +
      "',lower(title))!=0 or locate('" +
      keyword +
      "',lower(abs))!=0);";
    int count = jdbcTemplate.queryForObject(countSQL, Integer.class);
    String startIndex = Integer.toString(PAGE_SIZE * (page - 1));
    String sql =
      "select id from paper where (locate('" +
      keyword +
      "',lower(title))!=0 or locate('" +
      keyword +
      "',lower(abs))!=0)" +
      " limit " +
      startIndex +
      "," +
      PAGE_SIZE;
    return new IResult(jdbcTemplate.queryForList(sql, String.class), count);
  }

  @Transactional(readOnly = true)
  public IResult getPaperByCond(
    String keyword,
    String year,
    boolean type,
    int page
  ) {
    if (type) {
      String countSQL =
        "select count(id) from paper where publication_date>='" +
        year +
        "' and (locate('" +
        keyword +
        "',lower(title))!=0 or locate('" +
        keyword +
        "',lower(abs))!=0)";
      int count = jdbcTemplate.queryForObject(countSQL, Integer.class);
      String startIndex = Integer.toString(PAGE_SIZE * (page - 1));
      String sql =
        "select id from paper where publication_date>='" +
        year +
        "' and (locate('" +
        keyword +
        "',lower(title))!=0 or locate('" +
        keyword +
        "',lower(abs))!=0)" +
        " limit " +
        startIndex +
        "," +
        PAGE_SIZE;
      return new IResult(jdbcTemplate.queryForList(sql, String.class), count);
    } else {
      String countSQL =
        "select count(id) from paper where publication_date<='" +
        year +
        "' and (locate('" +
        keyword +
        "',lower(title))!=0 or locate('" +
        keyword +
        "',lower(abs))!=0)";
      int count = jdbcTemplate.queryForObject(countSQL, Integer.class);
      String startIndex = Integer.toString(PAGE_SIZE * (page - 1));
      String sql =
        "select id from paper where publication_date<='" +
        year +
        "' and (locate('" +
        keyword +
        "',lower(title))!=0 or locate('" +
        keyword +
        "',lower(abs))!=0)" +
        " limit " +
        startIndex +
        "," +
        PAGE_SIZE;
      return new IResult(jdbcTemplate.queryForList(sql, String.class), count);
    }
  }

  @Transactional(readOnly = true)
  public IResult getPaperByCond(
    String keyword,
    String startYear,
    String endYear,
    int page
  ) {
    String countSQL =
      "select count(id) from paper where publication_date>='" +
      startYear +
      "' and publication_date<='" +
      endYear +
      "'" +
      "and (locate('" +
      keyword +
      "',lower(title))!=0 or locate('" +
      keyword +
      "',lower(abs))!=0)";
    int count = jdbcTemplate.queryForObject(countSQL, Integer.class);
    String startIndex = Integer.toString(PAGE_SIZE * (page - 1));
    String sql =
      "select id from paper where publication_date>='" +
      startYear +
      "' and publication_date<='" +
      endYear +
      "' and (locate('" +
      keyword +
      "',lower(title))!=0 or locate('" +
      keyword +
      "',lower(abs))!=0)" +
      " limit " +
      startIndex +
      "," +
      PAGE_SIZE;
    //    System.out.println(countSQL);
    //    System.out.println(sql);
    return new IResult(jdbcTemplate.queryForList(sql, String.class), count);
  }
}

//
//class PaperRowMapper implements RowMapper<IPaper> {
//
//  @Override
//  public IPaper mapRow(ResultSet rs, int rowNum) throws SQLException {
//    IPaper res = new IPaper();
//    res.setId(rs.getString("id"));
//    res.setAbs(rs.getString("abs"));
//    res.setTitle(rs.getString("title"));
//    res.setPublication(rs.getString("publication_id"));
//    res.setLink(rs.getString("link"));
//    res.setPublicationDate(rs.getString("publication_date"));
//    try {
//      String references = rs.getString("references");
//      res.setReferences(string2List(references));
//    } catch (Exception e) {
//      res.setReferences(new ArrayList<>());
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
//    res.setCitations(rs.getInt("citation"));
//    return res;
//  }
//
//  private List<String> string2List(String str) {
//    String[] strs = str.split(",");
//    return Arrays.asList(strs);
//  }
//}

class PaperBasicRowMapper implements RowMapper<IPaperBasic> {

  @Override
  public IPaperBasic mapRow(ResultSet rs, int rowNum) throws SQLException {
    IPaperBasic res = new IPaperBasic();
    res.setId(rs.getString("id"));
    res.setAbs(rs.getString("abs"));
    res.setTitle(rs.getString("title"));
    res.setPublication(rs.getString("publication_id"));
    res.setPublicationDate(rs.getString("publication_date"));
    res.setCitations(rs.getInt("citation"));
    try {
      String researchers = rs.getString("researchers");
      res.setResearchers(string2List(researchers));
    } catch (Exception e) {
      res.setResearchers(new ArrayList<>());
    }
    return res;
  }

  private List<String> string2List(String str) {
    String[] strs = str.split(",");
    return Arrays.asList(strs);
  }
}
