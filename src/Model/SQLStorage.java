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
  public void save(File filename, MusicItem item) {

    db.save(item);
  }
}
