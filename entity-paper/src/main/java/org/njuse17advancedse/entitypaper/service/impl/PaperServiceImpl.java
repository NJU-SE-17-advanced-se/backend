package org.njuse17advancedse.entitypaper.service.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.service.PaperService;
import org.springframework.stereotype.Service;

@Service
public class PaperServiceImpl implements PaperService {
  private SQLQuery querier;

  public PaperServiceImpl() throws Exception {
    querier = new SQLQuery();
  }

  public IPaper getIPaper(String paperId) {
    try {
      //            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
      //            Statement stmt=connection.createStatement();
      String sql =
        "select paper.*,publication.*,group_concat(distinct paper_reference.reference_doi) as `references`," +
        "group_concat(distinct paper_researcher.rid) as researchers, group_concat(distinct paper_domain.dname) as domains" +
        " from `paper`,`publication`,`paper_reference`,`paper_researcher`,`paper_domain` " +
        "where paper.id= '" +
        paperId +
        "' and paper.publication_id=publication.id " +
        "and paper.id=paper_reference.pid and paper.id=paper_domain.pid and paper.id =paper_researcher.pid " +
        "group by paper.id;";
      //            String sql = "select paper.*,publication.*,group_concat(paper_reference.reference_doi) as `references`,"  +
      //                    " from `paper`,`publication`,`paper_reference` " +
      //                    "where paper.id= '" + paperId + "' and paper.publication_id=publication.id " +
      //                    "and paper.id=paper_reference.pid " +
      //                    "group by paper.id;";
      ResultSet rs = querier.query(sql);
      System.out.println(sql);

      if (!rs.next()) {
        return null;
      }
      IPaper res = new IPaper();
      res.setId(paperId);
      res.setAbs(rs.getString("abs"));
      res.setTitle(rs.getString("title"));
      res.setPublication(rs.getString("publication_id"));
      res.setLink(rs.getString("link"));
      res.setPublicationDate(rs.getString("paper.publication_date"));
      res.setReferences(string2List(rs.getString("references")));
      res.setDomains(string2List(rs.getString("domains")));
      res.setResearchers(string2List(rs.getString("researchers")));

      release(rs);
      return res;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<String> getPapers(
    String researcher,
    String publication,
    String date
  ) {
    try {
      String sql;
      List<String> res = new ArrayList<>();
      //啥都不限制
      if (researcher == null && publication == null && date == null) {
        sql = "select id from `paper`";
        ResultSet rs = querier.query(sql);

        while (rs.next()) {
          res.add(rs.getString("id"));
        }

        release(rs);
        return res;
      }
      //只限制researcher
      if (researcher != null && publication == null && date == null) {
        sql =
          "select paper.id from `paper`,`paper_researcher` where paper.id=paper_researcher.pid and paper_researcher.rid='" +
          researcher +
          "';";
        ResultSet rs = querier.query(sql);

        while (rs.next()) {
          res.add(rs.getString("id"));
        }

        release(rs);
        return res;
      }
      //只限制publication
      if (researcher == null && publication != null && date == null) {
        sql =
          "select paid from `paper` where publication_id='" +
          publication +
          "';";
        ResultSet rs = querier.query(sql);

        while (rs.next()) {
          res.add(rs.getString("id"));
        }

        release(rs);
        return res;
      }
      //只限制date
      if (researcher == null && publication == null) {
        sql = "select paid from `paper` where publication_date='" + date + "';";
        ResultSet rs = querier.query(sql);
        while (rs.next()) {
          res.add(rs.getString("id"));
        }
        release(rs);
        return res;
      }
      //限制researcher和publication
      if (researcher != null && publication != null && date == null) {
        sql =
          "select paper.id from `paper`,`paper_researcher` where paper.publication_id='" +
          publication +
          "'and paper.id=paper_researcher.pid and paper_researcher.rid='" +
          researcher +
          "';";
        ResultSet rs = querier.query(sql);

        while (rs.next()) {
          res.add(rs.getString("id"));
        }

        release(rs);
        return res;
      }
      //限制researcher和date
      if (researcher != null && publication == null) {
        sql =
          "select paper.id from `paper`,`paper_researcher` where paper.publication_date='" +
          date +
          "' and paper.id=paper_researcher.pid and paper_researcher.rid='" +
          researcher +
          "';";
        ResultSet rs = querier.query(sql);

        while (rs.next()) {
          res.add(rs.getString("id"));
        }

        release(rs);
        return res;
      }
      //限制publication和date
      if (researcher == null) {
        sql =
          "select paid from `paper` where publication_id='" +
          publication +
          "' and publication_date='" +
          date +
          "';";
        ResultSet rs = querier.query(sql);

        while (rs.next()) {
          res.add(rs.getString("id"));
        }

        release(rs);
        return res;
      }
      //全都限制
      sql =
        "select paper.id from `paper`,`paper_researcher` where paper.publication_date='" +
        date +
        "' and paper.publication_id='" +
        publication +
        "' and paper.id=paper_researcher.pid and paper_researcher.rid='" +
        researcher +
        "';";
      ResultSet rs = querier.query(sql);

      while (rs.next()) {
        res.add(rs.getString("id"));
      }

      release(rs);
      return res;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  //获取
  public IPaperBasic getPaperBasicInfo(String id) {
    try {
      //            Connection connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
      //            Statement stmt=connection.createStatement();
      String sql =
        "select paper.*, group_concat(distinct paper_researcher.rid) as researchers" +
        " from `paper`,`publication`,`paper_researcher` " +
        "where paper.id= '" +
        id +
        "' " +
        "and paper.id =paper_researcher.pid " +
        "group by paper.id;";
      //            String sql = "select paper.*,publication.*,group_concat(paper_reference.reference_doi) as `references`,"  +
      //                    " from `paper`,`publication`,`paper_reference` " +
      //                    "where paper.id= '" + paperId + "' and paper.publication_id=publication.id " +
      //                    "and paper.id=paper_reference.pid " +
      //                    "group by paper.id;";
      ResultSet rs = querier.query(sql);
      System.out.println(sql);

      if (!rs.next()) {
        return null;
      }
      IPaperBasic res = new IPaperBasic();
      res.setId(id);
      res.setAbs(rs.getString("abs"));
      res.setTitle(rs.getString("title"));
      res.setPublication(rs.getString("publication_id"));
      res.setPublicationDate(rs.getString("publication_date"));
      res.setResearchers(string2List(rs.getString("researchers")));

      release(rs);
      return res;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // 根据指标获取论文简略信息
  // 如果都没填，返回全部论文的简略信息
  public List<String> getPapersBasicInfo(
    String researcher,
    String publication,
    String date
  ) {
    return null;
  }

  //获取论文所属领域
  public List<String> getDomains(String id) {
    try {
      String sql = "select * from `paper_domain` where pid='" + id + "';";
      ResultSet rs = querier.query(sql);
      List<String> res = new ArrayList<>();

      while (rs.next()) {
        res.add(rs.getString("dname"));
      }

      release(rs);
      return res;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  private void release(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      rs = null;
    }
  }

  private List<String> string2List(String str) {
    String[] strs = str.split(",");
    return Arrays.asList(strs);
  }
}
