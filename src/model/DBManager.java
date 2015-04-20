package model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * The Database Manager class.
 * 
 * This class contains database helper methods for a SQLite database.
 * This class uses the singleton pattern to ensure there can only be
 * one database connection active.
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */

final public class DBManager {

  private Connection con = null;
  private File filename;

  // Using default (private, package) scope
  DBManager() {}
  
  /**
   * Resets the database connection
   * 
   */
  private void resetConnection() {
    con = null;
  }

  /**
   * Connect to the SQLite database
   * 
   * @return the connection object
   */
  private Connection connect() {

    try {
      Class.forName("org.sqlite.JDBC");
      con = DriverManager.getConnection("jdbc:sqlite:"+filename);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return con;
  }
  
  /**
   * Creates a new connection if none exists otherwise the
   * existing connection is returned
   * 
   * @return the connection object
   */
  private Connection getConnection() {
    if (con == null) {
      return connect();
    }
    return con;
  }

  /**
   * Creates a new table in the local database. Fails silently
   * if the table already exists.
   * 
   */
  private void create() {

    Connection con = getConnection();
    Statement stmt = null;

    try {
      stmt = con.createStatement();
      String sql = "create table musiccollection"
          + "(id int primary key," // A null value will auto-increment
          + " artist text not null, " + " album text not null, "
          + " year integer, " + " genre text)";
      stmt.executeUpdate(sql);
      stmt.close();
    } catch (Exception e) {
      // Most of the time the user will be working with a an existing database,
      // so fail silently.
    }
  }

  /**
   * Drops a table in the local database.
   * 
   */
  public void drop() {

    Connection con = getConnection();
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      String sql = "drop table musiccollection;";
      stmt.executeUpdate(sql);
      stmt.close();
    } catch (Exception e) {
      // System.err.println(e.getClass().getName() + ": " + e.getMessage());
      // System.exit(0);
    }
  }

  
  /**
   * Returns a list of all the music items in the local database.
   * 
   * @return an array list of music items
   * @throws SQLException
   */
  public ArrayList<MusicItem> load(File filename) throws SQLException {
  
    // In case of multiple db files
    resetConnection();

    // Store the filename
    this.filename = filename;
    
    ArrayList<MusicItem> list = new ArrayList<>();
    Connection con = getConnection();
    Statement stmt = null;
    ResultSet rs = null;
  
    stmt = con.createStatement();
    rs = stmt.executeQuery("select * from musiccollection;");
  
    while (rs.next()) {
      MusicItem item = new MusicItem();
      item.setArtist(rs.getString("artist"));
      item.setAlbum(rs.getString("album"));
      item.setYear(rs.getInt("year"));
      item.setGenre(rs.getString("genre"));
      list.add(item);
    }
  
    rs.close();
    stmt.close();
    return list;
  }

  /**
   * Saves the music list into a SQLite database file.
   * 
   * @param list music list
   * @param filename database file
   */
  public void save(ArrayList<MusicItem> list, File filename) {
    
    // Store the filename
    this.filename = filename;

    // This is awful, but it done this way to work in the same
    // manner as the other storage type examples.
    drop();
    create();

    for (MusicItem item : list) {
      saveItem(item);
    }
    
  }
  
  /**
   * Stores a new music item in the local database.
   * 
   * @param item
   */
  private void saveItem(MusicItem item) {

    Connection con = getConnection();
    Statement stat = null;

    try {
      stat = con.createStatement();

      PreparedStatement ps = con
          .prepareStatement("insert into musiccollection values (?, ?, ?, ?, ?);");

      ps.setString(2, item.getArtist());
      ps.setString(3, item.getAlbum());
      ps.setInt(4, item.getYear());
      ps.setString(5, item.getGenre());
      ps.execute();

      stat.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Closes the database connection.
   * 
   */
  public void close(File filename) {
    try {
      Connection con = getConnection();

      if (con != null) {
        con.close();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
