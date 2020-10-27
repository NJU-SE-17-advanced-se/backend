package org.njuse17advancedse.entitypaper.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLQuery {
  private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";

  private static final String DBURL =
    "jdbc:mysql://cdb-1u0nhiy0.cd.tencentcdb.com:10120/data_processing";

  private static final String DBUSER = "root";

  private static final String DBPASS = "zznb123456";

  private Connection connection;

  private Statement statement;

  public SQLQuery() throws Exception {
    Class.forName(DBDRIVER);
  }

  private void connect() throws Exception {
    if (connection.isClosed()) {
      connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
      statement = connection.createStatement();
    }
  }

  public ResultSet query(String sql) throws Exception {
    connect();
    ResultSet rs = statement.executeQuery(sql);
    return rs;
  }
}
