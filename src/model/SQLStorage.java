package model;

import java.io.File;
import java.sql.SQLException;

/**
 * 
 * The SQLStorage class.
 * 
 * This class loads from the local database. The user may also add new music
 * items to the music list.
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */
public class SQLStorage extends Storable {

  private DBManager db = new DBManager();

  /**
   * Loads the database entries into the music list
   * 
   * @param filename
   *          the storage file
   */
  @Override
  public void load(File filename) throws PersistenceException {

    // Clear the list before populating new data
    musicList.clear();

    try {
      musicList = db.load(filename);
      musicCollectionLength = musicList.size();
    } catch (SQLException e) {
      throw new PersistenceException();
    }
  }

  /**
   * Adds a music item into the music list
   * 
   */
  @Override
  public void add(MusicItem item) {
    musicList.add(item);

    // Recalculate new List size
    musicCollectionLength = musicList.size();
  }

  /**
   * Saves the music list into a database file
   */
  @Override
  public void save(File filename) {
    db.save(musicList, filename);
  }
}
