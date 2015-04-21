package model;

import java.io.File;
import java.sql.SQLException;

/**
 * 
 * The SQLStorage class.
 * 
 * This class loads from the local SQLite database. The user may also add new
 * music items, update existing items as well as delete items in the music list
 * (see Storable.java).
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */
public class SQLStorage extends Storable {

  private DBManager db = new DBManager();

  /**
   * Loads the database entries into the music list.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  @Override
  public void load(File filename) throws PersistenceException {

    // Clear the list before populating new data
    musicList.clear();

    try {
      musicList = db.load(filename);
      musicCollectionLength = musicList.size();
    } catch (SQLException | ClassNotFoundException e) {
      throw new PersistenceException();
    }
  }

  /**
   * Saves the music list into a database file.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  @Override
  public void save(File filename) throws PersistenceException {

    try {
      db.save(musicList, filename);
    } catch (SQLException | ClassNotFoundException e) {
      throw new PersistenceException();
    }
  }
}
