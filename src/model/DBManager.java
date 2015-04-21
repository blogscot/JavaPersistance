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
 * This class contains database helper methods for a SQLite database. This class
 * uses the singleton pattern to ensure there can only be one active database
 * connection.
 * 
 * In general, exceptions are re-thrown and handled by the SQLStorage class.
 * 
 * @author Iain Diamond
 * @version 20/04/2015
 * 
 */

final public class DBManager {

  private Connection con = null;
  private File filename;

  // Using default (private, package) scope
  DBManager() {
  }

  /**
   * Returns a list of all the music items in the local database.
   * 
   * @return an array list of music items
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  public ArrayList<MusicItem> load(File filename) throws SQLException,
      ClassNotFoundException {

    // In case of multiple database files are being used it is necessary to
    // reset the DB connection on each new load.
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
      item.setTrack(rs.getString("track"));
      item.setDuration(rs.getString("duration"));
      item.setYear(rs.getInt("year"));
      item.setGenre(rs.getString("genre"));
      list.add(item);
    }

    rs.close();
    stmt.close();
    return list;
  }

  /**
   * Saves the music list into the connected SQLite database file.
   * 
   * @param list
   *          the music list
   * @param filename
   *          the database file
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  public void save(ArrayList<MusicItem> list, File filename)
      throws SQLException, ClassNotFoundException {

    // Store the filename
    this.filename = filename;

    /*
     * In order to have this application behave the same for each of the four
     * persistence types means that saving the DB file effectively overwrites
     * the existing DB file. This is simulated by dropping the DB table(s) and
     * creating it anew.
     */
    drop();
    create();

    for (MusicItem item : list) {
      saveItem(item);
    }
  }

  /**
   * Drops the table(s) in the local database.
   * 
   * @throws SQLException
   * @throws ClassNotFoundException
   * 
   */
  public void drop() throws ClassNotFoundException, SQLException {

    Connection con = getConnection();
    Statement stmt = null;

    try {
      stmt = con.createStatement();
      String sql = "drop table musiccollection;";
      stmt.executeUpdate(sql);
      stmt.close();
    } catch (Exception e) {
      // Fail silently
    }
  }

  /**
   * Closes the database connection.
   * 
   * @throws ClassNotFoundException
   * 
   */
  public void close(File filename) throws ClassNotFoundException {
    try {
      Connection con = getConnection();

      if (con != null) {
        con.close();
      }
    } catch (SQLException e) {
      // Log the error and continue
      System.err.println("DB close error.");
    }
  }

  /**
   * Creates a new table in the local database. Fails silently if the table
   * already exists.
   * 
   * @throws SQLException
   * @throws ClassNotFoundException
   * 
   */
  private void create() throws ClassNotFoundException, SQLException {

    Connection con = getConnection();
    Statement stmt = null;

    stmt = con.createStatement();
    String sql = "create table musiccollection"
        + "(id int primary key," // A null value will auto-increment
        + " artist text not null, " + " track text not null, "
        + " duration text not null, " + " album text not null, "
        + " year integer, " + " genre text)";
    stmt.executeUpdate(sql);
    stmt.close();
  }

  /**
   * Connects to the SQLite database, and returns the connection object.
   * 
   * @return the connection object
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  private Connection connect() throws ClassNotFoundException, SQLException {

    Class.forName("org.sqlite.JDBC");
    con = DriverManager.getConnection("jdbc:sqlite:" + filename);
    return con;
  }

  /**
   * Resets the database connection.
   * 
   */
  private void resetConnection() {
    con = null;
  }

  /**
   * Creates a new connection if none exists otherwise the existing connection
   * is returned.
   * 
   * @return the connection object
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  private Connection getConnection() throws ClassNotFoundException,
      SQLException {
    if (con == null) {
      return connect();
    }
    return con;
  }

  /**
   * Stores a new music item in the local database.
   * 
   * @param item
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  private void saveItem(MusicItem item) throws SQLException,
      ClassNotFoundException {

    Connection con = getConnection();
    Statement stat = null;

    stat = con.createStatement();

    PreparedStatement ps = con
        .prepareStatement("insert into musiccollection values (?, ?, ?, ?, ?, ?, ?);");

    ps.setString(2, item.getArtist());
    ps.setString(3, item.getAlbum());
    ps.setString(4, item.getTrack());
    ps.setString(5, item.getDuration());
    ps.setInt(6, item.getYear());
    ps.setString(7, item.getGenre());
    ps.execute();

    stat.close();
  }
}
