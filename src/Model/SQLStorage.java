package Model;

import java.io.File;
import java.sql.SQLException;

public class SQLStorage extends Storable {

  private DBManager db = new DBManager();

  @Override
  public void load(File filename) throws PersistenceException {

    try {
      musicList = db.selectAll();
      musicCollectionLength = musicList.size();
    } catch (SQLException e) {
      throw new PersistenceException();
    }
  }

  @Override
  public void add(MusicItem item) {
    musicList.add(item);
    db.save(item);
    
    // Recalculate new List size
    musicCollectionLength = musicList.size();    
  }

  /**
   * The Liskov Substitution Principle is being bent here.
   * 
   * This is a stub function
   */
  @Override
  public void save(File filename) { }
}
